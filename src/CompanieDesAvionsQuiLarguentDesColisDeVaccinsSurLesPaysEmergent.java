import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import behaviors.data.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Compagnie des avions qui larguent des colis de vaccins sur les pays émergents.
 * Voir la documentation des messages sur les javadoc des classes FlyListServer, NegotiationServer et FlyOrderServer
 */
public class CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent extends Agent {
    public static final String SERVICE_TYPE = "companie";
    public static final String SERVICE_NAME = "COMPANIE";

    public static final int MSG_DEM_VOL = 1000;
    public static final int MSG_REP_OFFRES = 1001;
    public static final int MSG_DEM_NEGOCIE = 1002;
    public static final int MSG_REP_NEGOCIE = 1003;
    public static final int MSG_DEM_ACHAT = 1005;
    public static final int MSG_REP_ACHAT = 1006;


    private static ArrayList<Vol> vols = new ArrayList<Vol>();
    private static ArrayList<Pays> pays = new ArrayList<Pays>();
    private static ArrayList<Ville> villes = new ArrayList<Ville>();

    private static ArrayList<Offre> offresGenerees = new ArrayList<Offre>();


    private static String nickname = "CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent";
    private static AID id = new AID(nickname, AID.ISLOCALNAME);


    protected void setup() {
        // Printout a welcome message
        log("Starting Agent: " + getClass().getName());

        //Recupère les arguments passés aux agents.
        Object[] args = getArguments();


        /**
         * Intègre des données Génerées
         *
         * @TODO : Contacter l'agent BDD pour peupler nos variables
         * @PROBLEM : Agent indisponible pour le moment
         */
        /**--------------------------------------------------*/
        log("Initializing Dummy Database");
        DummyDatabase.initListePays(pays);
        DummyDatabase.initListeVilles(villes);
        DummyDatabase.initListeVols(vols);
        log("Database ready");
        /**--------------------------------------------------*/

        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SERVICE_TYPE);
        sd.setName(SERVICE_NAME);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Ajoute le comportement produisant la liste des vols
        addBehaviour(new FlyListServer());

        // Ajoute le comportement gérant la négociation du prix de vols
        addBehaviour(new NegotiationServer());

        // Ajoute le comportement gerant les commandes de vols
        addBehaviour(new FlyOrdersServer());

    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Printout a dismissal message
        log("Agent terminating.");
    }

    private static void log(String msg) {
        System.out.println("Companie: " + msg);
    }

    /**
     * Demande de la liste des offres de vol pour un pays et un nombre de lots donnés:
     * - L'agent demande la liste des offres de vols pour un pays, et pour un certain nombre de lots
     * - On renvoie la liste des vols compatibles
     * - L'agent peut alors soit négocier le prix en envoyant un performative MSG_DEM_NEGOCIE (voir NegotiationServer),
     * soit accepter directement une offre en envoyant MSG_DEM_ACHAT
     * <p/>
     * Format de la demande:
     * Performative: MSG_DEM_VOL
     * Contenu: PAYS;NOMBRE DE LOTS
     * Exemple: Nigeria;5
     * <p/>
     * Format de la réponse:
     * Performative: MSG_REP_OFFRES
     * Contenu: ArrayList<Offre> (objet)
     */
    private static class FlyListServer extends CyclicBehaviour {

        public FlyListServer() {
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(MSG_DEM_VOL));

            if (msg != null) {
                /**
                 * @TODO: contacter bdd
                 */

                log("Demande de vols reçue");

                String content = msg.getContent();
                log("Requête: " + content);

                /**
                 * @TODO: gérer cas d'erreur
                 */
                String[] args = content.split(";");
                String nomPays = args[0];
                int capac = Integer.parseInt(args[1]);

                ArrayList<Offre> offresGenereesLocal = new ArrayList<Offre>();

                if (capac > 0 && nomPays != null) {
                    for (Vol vol : vols) {
                        // On vérifie si le vol est compatible, c'est à dire s'il passe par le pays demandé
                        boolean volCompatible = false;

                        ArrayList<Ville> escales = vol.getEscales();
                        for (Ville escale : escales) {
                            if (escale.getPays().getName().equals(nomPays)) {
                                volCompatible = true;
                            }
                        }

                        if (volCompatible) {
                            // Le vol est compatible, on génère une offre
                            Offre offre = new Offre(offresGenerees.size() + 1,
                                    vol,
                                    vol.calculPrixInitial(capac),
                                    vol.calculPrixInitial(capac));

                            offresGenerees.add(offre);
                            offresGenereesLocal.add(offre);
                        }
                    }
                } else {
                    log("KO");
                }

                ACLMessage reply = msg.createReply();
                reply.setPerformative(MSG_REP_OFFRES);

                try {
                    log("Offres envoyées:");
                   /* for (Offre offre : offresGenereesLocal) {
                        log(" - " + offre.toString());
                    }*/

                    reply.setContentObject(offresGenereesLocal);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    /**
     * Négociation du prix du vol:
     * - L'agent association envoie l'identifiant de l'offre, accompagné d'un prix suggéré
     * - La réponse renvoie "OK" avec le prix si l'offre est acceptée, "KO" sinon avec un nouveau prix proposé
     * <p/>
     * Format de la demande:
     * Performative: MSG_DEM_NEGOCIE
     * Contenu: NUMERO_OFFRE;PRIX_SUGGERE
     * <p/>
     * Format de la réponse:
     * Performative: MSG_REP_NEGOCIE
     * Contenu: OK;1650 -ou- KO;1688
     */
    private static class NegotiationServer extends CyclicBehaviour {

        public NegotiationServer() {
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(MSG_DEM_NEGOCIE));
            if (msg != null) {
                String content = msg.getContent();
                String[] args = content.split(";");
                int idOffre = Integer.parseInt(args[0]);
                double prixSuggere = Double.parseDouble(args[1]);

                log("Le client veut avoir l'offre " + idOffre + " à " + prixSuggere + " euros");

                Offre offre = offresGenerees.get(idOffre - 1);
                double prixInitial = offre.getPrixInitial();
                double prixMinimal = prixInitial * 0.7;
                log("Le prix initial du vol est de " + prixInitial + " euros");

                ACLMessage reply = msg.createReply();
                reply.setPerformative(MSG_REP_NEGOCIE);

                if (prixSuggere < prixMinimal) {
                    // TODO: isAbonne
                    Calendar oneMonthBeforeFlight = Calendar.getInstance();
                    oneMonthBeforeFlight.setTime(offre.getVol().getDepart());
                    oneMonthBeforeFlight.add(Calendar.MONTH, -1);

                    Calendar now = Calendar.getInstance();

                    boolean isLastMinute = now.after(oneMonthBeforeFlight);
                    double prixSuggereParLeServeurDeGestionDeCompanieDesAvions = offre.getVol().calculPrix(prixInitial, false /*isAbo*/, isLastMinute);

                    reply.setContent("KO;" + prixSuggereParLeServeurDeGestionDeCompanieDesAvions);
                    offre.setNouveauPrix(prixSuggereParLeServeurDeGestionDeCompanieDesAvions);

                    log("Prix suggéré par le client inférieur au prix minimal, prix suggéré : " + prixSuggereParLeServeurDeGestionDeCompanieDesAvions);
                } else {
                    log("Offre acceptée");
                    reply.setContent("OK;" + prixSuggere);
                    offre.setNouveauPrix(prixSuggere);
                }

                myAgent.send(reply);
            } else {
                block();
            }
        }

    }

    /**
     * Demande et confirmation de la réservation d'un vol:
     * - L'agent envoie le numéro de l'offre
     * - On répond "OK" si tout s'est bien passé, ou KO en cas d'erreur (numéro de vol incorrect, etc).
     * <p/>
     * Format de la demande:
     * Performative: MSG_DEM_ACHAT
     * Contenu: NUMERO_OFFRE
     * Exemple: 1
     * <p/>
     * Format de la réponse:
     * Performative: MSG_REP_ACHAT
     * Contenu: OK ou KO
     */
    private static class FlyOrdersServer extends CyclicBehaviour {

        public FlyOrdersServer() {
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(MSG_DEM_ACHAT));

            if (msg != null) {
                int orderId = Integer.parseInt(msg.getContent());

                ACLMessage reply = msg.createReply();

                Offre offre = offresGenerees.get(orderId - 1);

                log("Le client confirme l'achat de l'offre " + orderId + " à " + offre.getNouveauPrix() + "euros.");
                log("Solde précédent: " + DummyDatabase.getSolde());

                DummyDatabase.incrSolde(offre.getNouveauPrix());

                log("Nouveau solde: " + DummyDatabase.getSolde());

                reply.setPerformative(MSG_REP_ACHAT);
                reply.setContent("OK");

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }


}

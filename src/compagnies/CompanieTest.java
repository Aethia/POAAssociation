package compagnies;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import behaviors.data.*;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;

public class CompanieTest extends Agent {

    private static AID[] sellerAgents;

    private static ArrayList<Ville> villes = new ArrayList<Ville>();

    private static String nickname = "CompanieTest";
    private static AID id = new AID(nickname, AID.ISLOCALNAME);

    private static int idOffreNegocie = -1;

    private void init() {
        Pays p1 = new Pays("Nigeria");
        Pays p2 = new Pays("Ouganda");
        Pays p3 = new Pays("Nicaragua");

        villes.add(new Ville("Bougabouga", p1));
        villes.add(new Ville("Gneuh", p1));
        villes.add(new Ville("Projetdem", p2));
        villes.add(new Ville("BLablabla", p3));
        villes.add(new Ville("Talabanahau", p3));
    }

    private static void log(String msg) {
        System.out.println("TEST: " + msg);
    }

    protected void setup() {
        // Printout a welcome message
        log("Starting agent");

        // Recupère les arguments passés aux agents.
        Object[] args = getArguments();

        // initialisation du jeu de données
        init();

        // Register the book-selling service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.SERVICE_TYPE);
        sd.setName("TEST");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }


        // Ajoute le comportement produisant la liste des vols
        addBehaviour(new InitialPaysSender("Nigeria"));
        addBehaviour(new FlyListServer());
        addBehaviour(new NegotiationServer());
        addBehaviour(new ConfirmationAchatServer());
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        log("Agent " + getAID().getName() + " terminating.");
    }


    private static class InitialPaysSender extends OneShotBehaviour {
        private String pays;

        public InitialPaysSender(String pays) {
            this.pays = pays;
        }

        @Override
        public void action() {
            ACLMessage msg;

            // Update the list of seller agents
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.SERVICE_TYPE);
            template.addServices(sd);

            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                log("Found the following seller agents:");
                sellerAgents = new AID[result.length];
                for (int i = 0; i < result.length; ++i) {
                    sellerAgents[i] = result[i].getName();
                    log(" - " + sellerAgents[i].getName());
                }
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }

            boolean foundSeller = false;
            for (AID aid : sellerAgents) {
                // On filtre l'agent de test
                if (!aid.getName().contains("test")) {
                    msg = new ACLMessage(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_DEM_VOL);
                    msg.setContent(pays + ";5");
                    msg.addReceiver(aid);
                    myAgent.send(msg);
                    foundSeller = true;
                }
            }

            if (foundSeller) {
                log("Demande de vol envoyée");
                block();
            } else{
                log("Aucun agent de vente trouvé, on réessaye dans 1 seconde");
                myAgent.addBehaviour(new InitialPaysSender(pays));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {}
            }
        }
    }

    private static class FlyListServer extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_REP_OFFRES));

            if (msg != null) {
                log("Réception des offres de vols");

                ArrayList<Offre> content = null;
                try {
                    content = (ArrayList<Offre>) msg.getContentObject();
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }

                for (Offre offre : content) {
                    log(" - " + offre);
                }

                if (content.size() == 0) {
                    log("Aucune offre pour le pays demandé !");
                } else {
                    Offre negocie = content.get(0);
                    log("Demande de négocation de l'offre " + negocie.getIdOffre() + " à " + (negocie.getPrixInitial() / 2.0));

                    idOffreNegocie = negocie.getIdOffre();

                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_DEM_NEGOCIE);
                    reply.setContent("" + idOffreNegocie + ";" + (negocie.getPrixInitial() / 2.0));
                    myAgent.send(reply);
                }
            } else {
                block();
            }
        }
    }

    private static class NegotiationServer extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_REP_NEGOCIE));

            if (msg != null) {
                String content = msg.getContent();
                log("Réponse de négociation: " + content);

                String[] args = content.split(";");

                if ("OK".equals(args[0])) {
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_DEM_ACHAT);
                    reply.setContent("" + idOffreNegocie);
                    myAgent.send(reply);
                } else if ("KO".equals(args[0])) {
                    double prixSuggere = Double.parseDouble(args[1]);
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_DEM_NEGOCIE);
                    reply.setContent("" + idOffreNegocie + ";" + prixSuggere);
                    myAgent.send(reply);
                }
            } else {
                block();
            }
        }
    }

    private static class ConfirmationAchatServer extends CyclicBehaviour {
        private static boolean testRussie = false;
        private static boolean testFrance = false;
        private static boolean testUnknown = false;

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(CompanieDesAvionsQuiLarguentDesColisDeVaccinsSurLesPaysEmergent.MSG_REP_ACHAT));

            if (msg != null) {
                String content = msg.getContent();
                log("Réponse d'achat: " + content);

                if (!testRussie) {
                    myAgent.addBehaviour(new InitialPaysSender("Russie"));
                    testRussie = true;
                } else if (!testFrance) {
                    myAgent.addBehaviour(new InitialPaysSender("France"));
                    testFrance = true;
                } else if (!testUnknown) {
                    myAgent.addBehaviour(new InitialPaysSender("Flugbékistan"));
                    testUnknown = true;
                } else {
                    log("TEST TERMINE");
                    System.exit(0);
                }
            } else {
                block();
            }
        }
    }
}

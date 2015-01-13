/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import business.SickPeople;
import business.Sickness;
import business.Vaccine;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;

/**
 *
 * @author Aethia
 */
public class AgentAssociation extends Agent {

    public static DFAgentDescription dfd;

    @Override
    protected void setup() {
        System.out.println("Mon nom est " + this.getLocalName());

        //AJOUTER UN SERVICE
        dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("association");
        sd.setName("ASSOCIATION");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        AID a = this.searchService("MALADIES");
        this.sendMessage(a, "Liste maladies");
        this.sendMessage(a, "Details maladie : Ebola");
        AID a2 = this.searchService("malades");
        this.sendMessage(a2, "Liste malades");
        this.sendMessage(a2, "Pays atteint : Mali");
        AID a3 = this.searchService("LABORATOIRES");
        //this.sendMessage(a3, "Liste vaccins");
        this.sendMessage(a3, "Vaccin maladie : Tuberculose");
        this.sendMessage(a3, "Details vaccin : Vaccin_1");
        this.receiveMessage();
    }

    public void receiveMessage() {
        while (true) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage aclMessage = this.receive(mt);

            if (aclMessage != null) {
                try {
                    // si c'est l'agent maladie qui répond
                    if (aclMessage.getSender().equals(this.searchService("MALADIES"))) {
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<Sickness> sicknesses = (ArrayList<Sickness>) aclMessage.getContentObject();
                            for (Sickness i : sicknesses) {
                                System.out.println(i.getNom() + " est recu" + i.getDelaiIncubation());
                            }
                        } else if (aclMessage.getContentObject() instanceof Sickness) {
                            System.out.println("maladie");
                            Sickness message = (Sickness) aclMessage.getContentObject();
                            System.out.println(message.getNom() + " est recu, delai d'incubation : " + message.getDelaiIncubation());
                        }
                    }
                    else if (aclMessage.getSender().equals(this.searchService("MALADES"))){
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<SickPeople> sickPeople = (ArrayList<SickPeople>) aclMessage.getContentObject();
                            for (SickPeople i : sickPeople) {
                                System.out.println("Il y a "+i.getNbSick()+" malade de la maladie "+i.getSick().getNom()+" au pays "+i.getCountry().getCountry());
                            }
                        }
                    }
                    else if (aclMessage.getSender().equals(this.searchService("LABORATOIRES"))) {
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<Vaccine> vaccines = (ArrayList<Vaccine>) aclMessage.getContentObject();
                            for (Vaccine i : vaccines) {
                                System.out.println(i.getNom() + " guérit " + i.getMaladie().getNom());
                            }
                        } else if (aclMessage.getContentObject() instanceof Vaccine) {
                            
                            Vaccine message = (Vaccine) aclMessage.getContentObject();
                            System.out.println(message.getNom() + " guérit la maladie " + message.getMaladie().getNom());
                        }
                    }
                    
                   

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    /**
     *
     * @param service
     * @return Recherche dans l'annuaire
     */
    private AID searchService(String service) {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(service);
        dfd.addServices(sd);
        AID aid = null;
        try {
            DFAgentDescription[] result = DFService.search(this, dfd);
            for (int i = 0; i < result.length; i++) {
                DFAgentDescription desc = (DFAgentDescription) result[i];
                aid = desc.getName();
                if (aid != null) {
                    System.out.println(aid.getName());
                    return aid;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aid;
    }

    @Override
    protected void takeDown() {
        // Deregister from the yellow pages 
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Agent : " + getAID().getName() + " terminé");
    }

    private void sendMessage(AID id, String msg) {
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(id);
        aclMessage.setContent(msg);
        this.send(aclMessage);
    }

}

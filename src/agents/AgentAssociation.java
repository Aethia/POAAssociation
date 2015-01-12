/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import business.Sickness;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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

        //ASK DATA
        System.out.println("Mon nom est " + this.getLocalName());
        AID a = this.searchService("MALADIES");
        this.sendMessage(a, "Send me your diseases");

        this.receiveMessage();
    }

    public void receiveMessage() {
        while (true) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage aclMessage = this.receive(mt);
            if (aclMessage != null) {
                try {
                    Sickness message =(Sickness) aclMessage.getContentObject();
                    System.out.println(message.getNom() +"est recu" + message.getDelaiIncubation());
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
        sd.setType("maladies");
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
        // Printout a dismissal message 
        System.out.println("Agent : " + getAID().getName() + " terminé");
    }

    private void sendMessage(AID id, String msg) {
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(id);
        aclMessage.setContent(msg);
        System.out.println("message : " + msg + "id : " + id.getName() + "envoyé");
        this.send(aclMessage);
    }

}

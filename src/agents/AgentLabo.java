/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import static agents.AgentAssociation.dfd;
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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Aethia
 */
public class AgentLabo extends Agent {

    @Override
    protected void setup() {
        System.out.println("Mon nom est " + this.getLocalName());

        //AJOUTER UN SERVICE
        dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("laboratoires");
        sd.setName("LABORATOIRES");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        this.receiveMessage();
    }

    public void receiveMessage() {
        while (true) {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage aclMessage = this.receive(mt);
            if (aclMessage != null) {
                try {
                    String message = aclMessage.getContent();
                    //message = demande
                    //on va renvoyer la liste des maladies
                    //bdd chercher maladies
                    Vaccine v1 = new Vaccine("Vaccin_1", "10/10/2015", 100, new Sickness("Ebola", 5, 14));
                    Vaccine v2 = new Vaccine("Vaccin_2", "10/12/2016", 130, new Sickness("Cholera", 4, 5));
                    Vaccine v3 = new Vaccine("Vaccin_3", "12/04/2016", 120, new Sickness("Paludisme", 3, 4));
                    Vaccine v4 = new Vaccine("Vaccin_4", "30/03/2017", 130, new Sickness("Tuberculose", 5, 7));
                   
                    ArrayList<Vaccine> lVaccines = new ArrayList();
                    lVaccines.add(v1);
                    lVaccines.add(v2);
                    lVaccines.add(v3);
                    lVaccines.add(v4);

                    switch (message) {
                        case "Liste vaccins":
                            
                            this.sendMessage(aclMessage.getSender(), lVaccines);
                            break;

                    }
                    if (message.contains("Vaccin")) {
                        System.out.println("j'envoie le message " + message);
                        String maladie = message.split(" : ")[1];
                        for (Vaccine i : lVaccines) {
                            if (i.getMaladie().getNom().equals(maladie)) {
                                this.sendMessage(aclMessage.getSender(), i);
                            }
                        }

                    }
                    
                     if (message.contains("Details")) {
                        String vaccin = message.split(" : ")[1];
                        for (Vaccine i : lVaccines) {
                            
                            if (i.getNom().equals(vaccin)) {
                                this.sendMessage(aclMessage.getSender(), i);
                            }
                        }

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
    }

    @Override
    protected void takeDown() {

    }

    private void sendMessage(AID id, Serializable msg) throws IOException {
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(id);
        aclMessage.setContentObject(msg);
        System.out.println("message : " + msg + "id : " + id.getName() + "envoyé");
        this.send(aclMessage);
    }

}

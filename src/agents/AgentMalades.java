/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import static agents.AgentAssociation.dfd;
import business.Country;
import business.SickPeople;
import business.Sickness;
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
public class AgentMalades extends Agent {

    @Override
    protected void setup() {
        System.out.println("Mon nom est " + this.getLocalName());

        //AJOUTER UN SERVICE
        dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("malades");
        sd.setName("MALADES");
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
                    switch (message) {
                        case "liste malades":
                            
                            SickPeople s = new SickPeople(new Sickness("Ebola", 5, 14), new Country("France"), 5);
                            SickPeople s2 = new SickPeople(new Sickness("Ebola", 5, 14), new Country("Mali"), 5);
                            SickPeople s3 = new SickPeople(new Sickness("Cholera", 4, 5), new Country("Senegal"), 5);
                            SickPeople s4 = new SickPeople(new Sickness("Cholera", 4, 5), new Country("Turquie"), 5);
                            SickPeople s5 = new SickPeople(new Sickness("Paludisme", 3, 4), new Country("Syrie"), 5);
                            SickPeople s6 = new SickPeople(new Sickness("Ebola", 5, 14), new Country("Sri-Lanka"), 5);
                            SickPeople s7 = new SickPeople(new Sickness("Tuberculose", 5, 7), new Country("Japon"), 5);
                            SickPeople s8 = new SickPeople(new Sickness("Paludisme", 3, 4), new Country("Inde"), 5);
                            SickPeople s9 = new SickPeople(new Sickness("Tuberculose", 5, 7), new Country("Maroc"), 5);

                            ArrayList<SickPeople> lMalades = new ArrayList();
                            lMalades.add(s);
                            lMalades.add(s2);
                            lMalades.add(s3);
                            lMalades.add(s4);
                            lMalades.add(s5);
                            lMalades.add(s6);
                            lMalades.add(s7);
                            lMalades.add(s8);
                            lMalades.add(s9);
                            

                            System.out.println("j'envoie la liste des malades");
                            this.sendMessage(aclMessage.getSender(), lMalades);

                            break;    
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

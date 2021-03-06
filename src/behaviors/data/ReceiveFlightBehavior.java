/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import agents.AgentAssociation;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import business.Constants;

/**
 *
 * @author Aethia
 */
public class ReceiveFlightBehavior extends CyclicBehaviour {

    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(Constants.MSG_REP_ACHAT);

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
        if (aclMessage != null) {
            try {

                AgentAssociation asso = (AgentAssociation) myAgent;
                String rep = aclMessage.getContent();
                System.out.println("Avion réservé, réponse : "+rep);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            block();
        }

    }

}

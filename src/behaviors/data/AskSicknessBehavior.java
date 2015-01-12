/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Aethia Demande les informations sur les maladies
 */
public class AskSicknessBehavior extends OneShotBehaviour {

    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

    public AskSicknessBehavior(Agent ag) {
        super(ag);
    }

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
        if (aclMessage != null) {
            try {
                String message = aclMessage.getContent();
            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            this.block();
        }

    }

}

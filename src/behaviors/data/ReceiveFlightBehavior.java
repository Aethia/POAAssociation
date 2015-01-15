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
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Aethia
 */
public class ReceiveFlightBehavior extends CyclicBehaviour {

    public static final int MSG_DEM_VOL = 1000;
    public static final int MSG_REP_OFFRES = 1001;
    public static final int MSG_DEM_NEGOCIE = 1002;
    public static final int MSG_REP_NEGOCIE = 1003;
    public static final int MSG_DEM_ACHAT = 1005;
    public static final int MSG_REP_ACHAT = 1006;
    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(ReceiveFlightBehavior.MSG_REP_ACHAT);

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

    }

}

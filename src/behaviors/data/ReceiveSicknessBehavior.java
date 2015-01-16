/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import agents.AgentAssociation;
import business.Sickness;
import business.Constants;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveSicknessBehavior extends CyclicBehaviour {

    public static final int MSG_DEM_MALADE_PAYS_PAR_MALADIE = 1030;
    public static final int MSG_DEM_MALADIE = 1031;
    public static final int MSG_REP_MALADE_PAYS_PAR_MALADIE = 1032;
    public static final int MSG_REP_MALADIE = 1033;
    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(Constants.MSG_REP_MALADIE);

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
        AgentAssociation assoc = (AgentAssociation) myAgent;
        if (aclMessage != null) {
            try {
                ArrayList<Sickness> sicknesses = (ArrayList<Sickness>) aclMessage.getContentObject();
                assoc.setSicknesses(sicknesses);
                System.out.println("Il y a actuellement " + sicknesses.size() + " maladies actives.");
                for (Sickness i : sicknesses) {
                    System.out.println("- " + i.getNom());
                }
            } catch (UnreadableException ex) {
                Logger.getLogger(ReceiveSicknessBehavior.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            block();
        }

    }

}

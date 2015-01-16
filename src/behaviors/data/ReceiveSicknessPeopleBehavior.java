/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import agents.AgentAssociation;
import business.Constants;
import business.SickPeople;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ReceiveSicknessPeopleBehavior extends CyclicBehaviour {


    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(Constants.MSG_REP_MALADE_PAYS_PAR_MALADIE);

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
        AgentAssociation assoc = (AgentAssociation) myAgent;
        if (aclMessage != null) {
            try {
                ArrayList<SickPeople> sicknesses = (ArrayList<SickPeople>) aclMessage.getContentObject();
                ArrayList<SickPeople> sickPeople = (ArrayList<SickPeople>) aclMessage.getContentObject();
                assoc.setSickPeople(sickPeople);
                for (SickPeople i : sickPeople) {
                    System.out.println("Il y a " + i.getNbSick() + " malades de la maladie " + i.getSick().getNom() + " au pays " + i.getCountry().getCountry());
                }
            } catch (UnreadableException ex) {
                Logger.getLogger(ReceiveSicknessBehavior.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            block();
        }
    }
}

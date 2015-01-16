/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.SearchService;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Aethia Demande les informations sur les maladies
 */
public class AskSicknessBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        AID a = SearchService.searchService("MALADIES", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        aclMessage.setContent("Liste maladies");
        myAgent.send(aclMessage);
    }
}

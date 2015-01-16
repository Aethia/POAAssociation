/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.Constants;
import business.SearchService;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Aethia Demande les informations sur les maladies
 */
public class AskSickPeopleBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        /*AID a = SearchService.searchService("malades", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        aclMessage.setContent("Liste malades");
        myAgent.send(aclMessage);*/
        
        AID a = SearchService.searchService("organization", myAgent);
        ACLMessage aclMessage = new ACLMessage(Constants.MSG_DEM_MALADE_PAYS_PAR_MALADIE);
        aclMessage.addReceiver(a);
        myAgent.send(aclMessage);
    }
}

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
 * @author Aethia
 */
public class AskVaccinesBehavior extends OneShotBehaviour{

    @Override
    public void action() {
       AID a = SearchService.searchService("LABORATOIRES", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        aclMessage.setContent("Liste vaccins");
        myAgent.send(aclMessage);
    }
    
}

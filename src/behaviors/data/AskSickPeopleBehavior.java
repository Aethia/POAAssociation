/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.SearchService;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Aethia Demande les informations sur les maladies
 */
public class AskSickPeopleBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        AID a = SearchService.searchService("malades", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        aclMessage.setContent("Liste malades");
        myAgent.send(aclMessage);
    }
}

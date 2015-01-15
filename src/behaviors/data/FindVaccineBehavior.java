/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.SearchService;
import business.Sickness;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aethia
 */
public class FindVaccineBehavior extends OneShotBehaviour{
    private Map<Sickness, Integer> sick;

    public FindVaccineBehavior(Map<Sickness, Integer> sick){
        this.sick=sick;
    }
    
    @Override
    public void action() {
       AID a = SearchService.searchService("LABORATOIRES", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        for(Sickness sickness : sick.keySet()){
            aclMessage.setContent("Vaccin : "+sickness.getNom()+ " : "+sick.get(sickness));
            myAgent.send(aclMessage);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.Country;
import business.SearchService;
import business.Vaccine;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aethia
 */
public class BuyVaccinesBehavior extends OneShotBehaviour{

    private String vaccine;
    private int quantity;
    
    
    public BuyVaccinesBehavior(String nomVaccin, int taille){
        this.vaccine = nomVaccin;
        this.quantity=taille;
    }
    
    @Override
    public void action() {
        AID a = SearchService.searchService("LABORATOIRE", myAgent);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(a);
        
        aclMessage.setContent("Achat : "+vaccine+" : "+quantity);
        myAgent.send(aclMessage);
        System.out.println("on demande a acheter le vaccin "+vaccine);
    }
    
}

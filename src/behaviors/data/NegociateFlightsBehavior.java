/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import business.Country;
import business.SearchService;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Aethia
 */
public class NegociateFlightsBehavior extends OneShotBehaviour{

    public static final int MSG_DEM_VOL = 1000;
    public static final int MSG_REP_OFFRES = 1001;
    public static final int MSG_DEM_NEGOCIE = 1002;
    public static final int MSG_REP_NEGOCIE = 1003;
    public static final int MSG_DEM_ACHAT = 1005;
    public static final int MSG_REP_ACHAT = 1006;
    
    private String idVol;
    private int price;
    
    public NegociateFlightsBehavior(String idVol, int price){
        this.idVol = idVol;
        this.price = price;
    }
    
    @Override
    public void action() {
        AID a = SearchService.searchService("companie", myAgent);
        ACLMessage aclMessage = new ACLMessage(NegociateFlightsBehavior.MSG_DEM_NEGOCIE);
        aclMessage.addReceiver(a);
        aclMessage.setContent(idVol+";"+price*0.8);
        myAgent.send(aclMessage);
    }
    
}

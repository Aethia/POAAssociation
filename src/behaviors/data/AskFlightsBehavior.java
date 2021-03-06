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
public class AskFlightsBehavior extends OneShotBehaviour{

    public static final int MSG_DEM_VOL = 1000;
    public static final int MSG_REP_OFFRES = 1001;
    public static final int MSG_DEM_NEGOCIE = 1002;
    public static final int MSG_REP_NEGOCIE = 1003;
    public static final int MSG_DEM_ACHAT = 1005;
    public static final int MSG_REP_ACHAT = 1006;
    
    private String countryname;
    private Integer nbSick;
    
    public AskFlightsBehavior(String country, Integer nbSick){
        this.countryname = country;
        this.nbSick = nbSick;
    }
    
    @Override
    public void action() {
        AID a = SearchService.searchService("companie", myAgent);
        ACLMessage aclMessage = new ACLMessage(AskFlightsBehavior.MSG_DEM_VOL);
        aclMessage.addReceiver(a);
        aclMessage.setContent(countryname+";"+nbSick);
        myAgent.send(aclMessage);
        System.out.println("on demande les vols pour "+countryname);
    }
    
}

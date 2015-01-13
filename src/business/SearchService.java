/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * @author Main
 */
public class SearchService {
    public static AID searchService(String name,Agent agent){
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(name);
        dfd.addServices(sd);
        AID aid = null;
        try {
            DFAgentDescription[] result = DFService.search(agent, dfd);
            for (int i = 0; i < result.length; i++) {
                DFAgentDescription desc = (DFAgentDescription) result[i];
                aid = desc.getName();
                if (aid != null) {
                    return aid;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aid;
    }
}

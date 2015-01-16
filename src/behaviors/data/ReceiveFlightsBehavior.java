/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;
import compagnies.Offre;
import agents.AgentAssociation;
import business.Constants;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Aethia
 */
public class ReceiveFlightsBehavior extends CyclicBehaviour {

    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(Constants.MSG_REP_OFFRES);

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
        if (aclMessage != null) {
            try {
                    if (aclMessage.getContentObject() instanceof ArrayList) {
                        AgentAssociation asso = (AgentAssociation)myAgent;
                        ArrayList<Offre> lesoffres = (ArrayList<Offre>) aclMessage.getContentObject();
                        asso.setLesOffres(lesoffres);
                        System.out.println(lesoffres.size() + " offres recues");
                        Collections.sort(lesoffres);
                     /*   for (int i=0;i<lesoffres.size();i++){
                            System.out.println("- id:" + lesoffres.get(i).getIdOffre()+" , prix:"+lesoffres.get(i).getPrixInitial()+" ");
                        }*/
                        
                        // demander les infos sur le vol le moins cher
                        myAgent.addBehaviour(new ReceiveFlightBehavior());
                        myAgent.addBehaviour(new BuyFlightBehavior(lesoffres.get(0).getIdOffre().toString()));
                        
                    } 
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            block();
        }

    }

}

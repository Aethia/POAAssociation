/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import static agents.AgentAssociation.dfd;
import business.Sickness;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Aethia
 */
public class AgentMaladies extends Agent{

        @Override
    protected void setup() {
        System.out.println("Mon nom est " + this.getLocalName());

        //AJOUTER UN SERVICE
        dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("maladies");
        sd.setName("MALADIES");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        
        this.receiveMessage();
    }
    
    public void receiveMessage(){
        while(true){
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage aclMessage = this.receive(mt);
            if(aclMessage != null){
                try{
                    String message = aclMessage.getContent();
                    //message = demande
                    //on va renvoyer la liste des maladies
                    //bdd chercher maladies
                    System.out.println("JAI RECU LE MESSAGE maladiES");
                    Sickness sn = new Sickness("Ebola", 5, 14);
                    Sickness sn2 = new Sickness("Cholera", 4, 5);
                    Sickness sn3 = new Sickness("Paludisme", 3, 4);
                    Sickness sn4 = new Sickness("Tuberculose", 5, 7);
                    
                    ArrayList<Sickness> lMaladies = new ArrayList();
                    lMaladies.add(sn);
                    lMaladies.add(sn2);
                    lMaladies.add(sn3);
                    lMaladies.add(sn4);
                     
                    if(message.equals("Liste maladies")){
                       System.out.println(message);
                        this.sendMessage(aclMessage.getSender(), lMaladies);
                    }
                    
                }catch(Exception e){
                    System.out.println(e);
                }
                
            }
        }
    }
    
    
    @Override
    protected void takeDown() {
  
    }

private void sendMessage(AID id, Serializable msg) throws IOException{
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.addReceiver(id);
        aclMessage.setContentObject(msg);
        System.out.println("message : " + msg + "id : "+ id.getName()+"envoyé");
        this.send(aclMessage);
    }
    
    
    
}

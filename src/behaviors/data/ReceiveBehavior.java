/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import agents.AgentAssociation;
import business.SearchService;
import business.SickPeople;
import business.Sickness;
import business.Vaccine;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aethia
 */
public class ReceiveBehavior extends CyclicBehaviour {

    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    
    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive(mt);
            if (aclMessage != null) {
                try {
                    AgentAssociation assoc = (AgentAssociation)myAgent;
                    /*
                    Agent Maladies
                    */
                    if (aclMessage.getSender().equals(SearchService.searchService("MALADIES",myAgent))) {
                        if (aclMessage.getContentObject() instanceof ArrayList) { 
                            ArrayList<Sickness> sicknesses = (ArrayList<Sickness>) aclMessage.getContentObject();
                            assoc.setSicknesses(sicknesses);
                            System.out.println("Il y a actuellement "+sicknesses.size()+" maladies actives.");
                            for (Sickness i : sicknesses) {
                                System.out.println("- "+i.getNom());
                            }
                        } else if (aclMessage.getContentObject() instanceof Sickness) {
                            Sickness message = (Sickness) aclMessage.getContentObject();
                            System.out.println("détails de la maladie\""+message.getNom() + "\"");
                            System.out.println("- Temps d'incubation : "+message.getDelaiIncubation());
                            System.out.println("- Virulence (sur 5) : "+message.getVirulence());
                        }
                    }
                    /*
                    Agent Malades
                    */
                    else if (aclMessage.getSender().equals(SearchService.searchService("MALADES",myAgent))){
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<SickPeople> sickPeople = (ArrayList<SickPeople>) aclMessage.getContentObject();
                            assoc.setSickPeople(sickPeople);
                            for (SickPeople i : sickPeople) {
                                System.out.println("Il y a "+i.getNbSick()+" malades de la maladie "+i.getSick().getNom()+" au pays "+i.getCountry().getCountry());
                            }
                        }
                    }
                    /*
                    Agent Laboratoires
                    */
                    else if (aclMessage.getSender().equals(SearchService.searchService("LABORATOIRES",myAgent))) {
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<Vaccine> vaccines = (ArrayList<Vaccine>) aclMessage.getContentObject();
                            assoc.setVaccines(vaccines);
                            System.out.println("Il y a actuellement "+vaccines.size()+" vaccins fabriqués.");
                            for (Vaccine i : vaccines) {
                                System.out.println("- "+i.getNom() + " guérit " + i.getMaladie().getNom()+" et coûte "+i.getPrice());
                            }
                        } else if (aclMessage.getContentObject() instanceof Vaccine) {
                            Vaccine message = (Vaccine) aclMessage.getContentObject();
                            System.out.println(message.getNom() + " guérit " + message.getMaladie().getNom()+" et coûte "+message.getPrice());
                        
                                
                            
                        }
                        else if (aclMessage.getContentObject() instanceof HashMap){
                            Map<Vaccine, Integer> map = (HashMap<Vaccine, Integer>)aclMessage.getContentObject();
                            for(Vaccine v : map.keySet()){
                                System.out.println("Achat de "+map.get(v)+" "+v.getNom());
                                assoc.addBehaviour(new PurchaseVaccineBehavior(v, map.get(v)));
                            }
                            
                        }
                    }
                    else if (aclMessage.getSender().equals(SearchService.searchService("compagnie",myAgent))) {
                        if (aclMessage.getContentObject() instanceof ArrayList) {
                            ArrayList<Vaccine> vaccines = (ArrayList<Vaccine>) aclMessage.getContentObject();
                            assoc.setVaccines(vaccines);
                            System.out.println("Il y a actuellement "+vaccines.size()+" vaccins fabriqués.");
                            for (Vaccine i : vaccines) {
                                System.out.println("- "+i.getNom() + " guérit " + i.getMaladie().getNom()+" et coûte "+i.getPrice());
                            }
                        } else if (aclMessage.getContentObject() instanceof Vaccine) {
                            Vaccine message = (Vaccine) aclMessage.getContentObject();
                            System.out.println(message.getNom() + " guérit " + message.getMaladie().getNom()+" et coûte "+message.getPrice());
                        }
                    }
                    
                    
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
            else {
                this.block();
            }
    }

}

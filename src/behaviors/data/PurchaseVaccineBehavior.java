/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.data;

import agents.AgentAssociation;
import business.Vaccine;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Aethia
 */
public class PurchaseVaccineBehavior extends OneShotBehaviour{
    private Vaccine vaccine;
    
    private int quantity;
    
    
    public PurchaseVaccineBehavior(Vaccine vaccin, int taille){
        this.vaccine = vaccin;
        
        this.quantity=taille;
    }
    
    @Override
    public void action() {
        AgentAssociation assoc = (AgentAssociation) myAgent;
        
        int price = vaccine.getPrice()*quantity;
        assoc.setAvailableMoney(assoc.getAvailableMoney() - price);
        
       
        System.out.println("Argent à la fin de la transaction : "+assoc.getAvailableMoney());
        System.out.println("Liste des vaccins de l'association : ");
        if(assoc.getStockVaccines().isEmpty()){
            vaccine.setTaille(quantity);
            assoc.getStockVaccines().add(vaccine);
        }
        for(Vaccine v : assoc.getStockVaccines()){
            if(v.getNom().equals(vaccine)){
                v.setTaille(v.getTaille()+quantity);
            }
            System.out.println("- "+v.getNom()+" : "+v.getTaille()+" unités");
        }
       
    }
    
}

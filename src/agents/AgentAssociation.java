/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviors.data.AskSickPeopleBehavior;
import behaviors.data.AskSicknessBehavior;
import behaviors.data.AskVaccinesBehavior;
import behaviors.data.ReceiveBehavior;
import business.SickPeople;
import business.Sickness;
import business.Vaccine;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;

/**
 *
 * @author Aethia
 */
public class AgentAssociation extends Agent {

    public static DFAgentDescription dfd;
    private ArrayList<Sickness> sicknesses;
    private ArrayList<SickPeople> sickPeople;
    private ArrayList<Vaccine> vaccines;
    private int availableMoney = 5000;

    public void setSicknesses(ArrayList<Sickness> sicknesses) {
        this.sicknesses = sicknesses;
    }

    public void setSickPeople(ArrayList<SickPeople> sickPeople) {
        this.sickPeople = sickPeople;
    }

    public void setVaccines(ArrayList<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

    public void setAvailableMoney(int availableMoney) {
        this.availableMoney = availableMoney;
    }
    
    public void init(){
        //AJOUTER UN SERVICE
        dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("association");
        sd.setName("ASSOCIATION");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
    @Override
    protected void setup() {
        init();    
        addBehaviour(new ReceiveBehavior());
        addBehaviour(new AskSicknessBehavior());
        addBehaviour(new AskVaccinesBehavior());
        addBehaviour(new AskSickPeopleBehavior());
    }

    /**
     *
     * @param service
     * @return Recherche dans l'annuaire
     */

    @Override
    protected void takeDown() {
        // Deregister from the yellow pages 
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Agent : " + getAID().getName() + " terminé");
    }
}

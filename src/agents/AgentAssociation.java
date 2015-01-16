/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import behaviors.agents.CountryFirst;
import behaviors.data.AskFlightsBehavior;
import behaviors.data.AskSickPeopleBehavior;
import behaviors.data.AskSicknessBehavior;
import behaviors.data.AskVaccinesBehavior;
import compagnies.Offre;
import behaviors.data.ReceiveBehavior;
import behaviors.data.ReceiveFlightBehavior;
import behaviors.data.ReceiveFlightsBehavior;
import behaviors.data.ReceiveSicknessBehavior;
import behaviors.data.ReceiveSicknessPeopleBehavior;
import business.SickPeople;
import business.Sickness;
import business.Vaccine;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.AssociationOntology;

/**
 *
 * @author Aethia
 */
public class AgentAssociation extends Agent {
    private Codec codec = new SLCodec();
    private Ontology ontology = AssociationOntology.getInstance();
    public static DFAgentDescription dfd;

    public ArrayList<Offre> getLesOffres() {
        return lesOffres;
    }

    public void setLesOffres(ArrayList<Offre> lesOffres) {
        this.lesOffres = lesOffres;
    }
    private ArrayList<Sickness> sicknesses;
    private ArrayList<SickPeople> sickPeople;
    private ArrayList<Vaccine> vaccines;
    private ArrayList<Vaccine> stockVaccines = new ArrayList<Vaccine>();
    private ArrayList<Offre> lesOffres;
    private int availableMoney = 5000;

    public ArrayList<Vaccine> getStockVaccines() {
        return stockVaccines;
    }

    public void setStockVaccines(ArrayList<Vaccine> stockVaccines) {
        this.stockVaccines = stockVaccines;
    }

    public ArrayList<Sickness> getSicknesses() {
        return sicknesses;
    }

    public void setSicknesses(ArrayList<Sickness> sicknesses) {
        this.sicknesses = sicknesses;
    }

    public ArrayList<SickPeople> getSickPeople() {
        return sickPeople;
    }

    public void setSickPeople(ArrayList<SickPeople> sickPeople) {
        this.sickPeople = sickPeople;
    }

    public ArrayList<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(ArrayList<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

    public int getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(int availableMoney) {
        this.availableMoney = availableMoney;
    }



    public void init() {
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
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        addBehaviour(new ReceiveBehavior());
        addBehaviour(new ReceiveFlightsBehavior());
        addBehaviour(new ReceiveSicknessBehavior());
        addBehaviour(new ReceiveSicknessPeopleBehavior());
        
       
        
        
        /*
        demander les maladies
        */
        Thread thread = new Thread() {
            public void run() {
                try {
                    this.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AgentAssociation.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                    addBehaviour(new AskSicknessBehavior());
            }
        };
        thread.start();
        
        /*
        demander les vaccins
        */
        Thread thread2 = new Thread() {
            public void run() {
                try {
                    this.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AgentAssociation.class.getName()).log(Level.SEVERE, null, ex);
                }
                addBehaviour(new AskVaccinesBehavior());
            }
        };
        thread2.start();
        
        /*
        demander les malades par pays
        */
        Thread thread3 = new Thread() {
            public void run() {
                try {
                    this.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AgentAssociation.class.getName()).log(Level.SEVERE, null, ex);
                }
                addBehaviour(new AskSickPeopleBehavior());
            }
        };
        thread3.start();
        
        /*
        on lance le comportement
        */
        Thread thread4 = new Thread() {
            public void run() {
                try {
                    this.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AgentAssociation.class.getName()).log(Level.SEVERE, null, ex);
                }
                addBehaviour(new CountryFirst());
            }
        };
        thread4.start();
        
    }

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

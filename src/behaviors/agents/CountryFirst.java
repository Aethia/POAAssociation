/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behaviors.agents;

import agents.AgentAssociation;
import behaviors.data.AskFlightsBehavior;
import behaviors.data.Offre;
import business.Country;
import business.SickPeople;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Aethia
 */
public class CountryFirst extends OneShotBehaviour {

    private String countryName;

    @Override
    public void action() {
        AgentAssociation assoc = (AgentAssociation) myAgent;
        Map<String, Integer> countries = new HashMap<String, Integer>();
        // obtenir les pays des malades
        for (SickPeople sick : assoc.getSickPeople()) {
            if (countries.containsKey(sick.getCountry())) {
                Integer nbSick = countries.get(sick.getCountry());
                countries.put(sick.getCountry().getCountry(), sick.getNbSick() + nbSick);
            } else {
                countries.put(sick.getCountry().getCountry(), sick.getNbSick());
            }
        }

        System.out.println("les pays trouves " + countries.toString());
        countryName = "France";
        System.out.println("on s'occupe d'abord de la France, il y a " + countries.get("France") + " personnes à soigner");

        // obtenir les vols pour la france
        assoc.addBehaviour(new AskFlightsBehavior(countryName,countries.get("France")));
        System.out.println("j'espere avoir recu les vols");
        ArrayList<Offre> lesOffres = assoc.getLesOffres();
        
    }

}

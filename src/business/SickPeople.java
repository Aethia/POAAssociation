/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import jade.content.Concept;
import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class SickPeople implements Serializable, Concept{
    private Sickness sick;
    private Country country;
    private int nbSick;

    public SickPeople(Sickness sick, Country country, int nb) {
        this.sick = sick;
        this.country = country;
        this.nbSick = nb;
    }

    public int getNbSick() {
        return nbSick;
    }

    public void setNbSick(int nbSick) {
        this.nbSick = nbSick;
    }

    
    
    public Sickness getSick() {
        return sick;
    }

    public void setSick(Sickness sick) {
        this.sick = sick;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    
    
}

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
 * @author Aethia
 */
public class Sickness implements Serializable, Concept{
    private String nom;
    private int virulence;
    private int delaiIncubation;
    
    public Sickness(){
        
    }

    public Sickness(String nom, int virulence, int delaiIncubation) {
        this.nom = nom;
        this.virulence = virulence;
        this.delaiIncubation = delaiIncubation;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getVirulence() {
        return virulence;
    }

    public void setVirulence(int virulence) {
        this.virulence = virulence;
    }

    public int getDelaiIncubation() {
        return delaiIncubation;
    }

    public void setDelaiIncubation(int delaiIncubation) {
        this.delaiIncubation = delaiIncubation;
    }
    
}

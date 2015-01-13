/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author myriam
 */
public class Vaccine implements Serializable {
    private String nom;
    private String datePeremption;
    private int taille;
    private Sickness maladie;
    private int price;

    public Vaccine(String nom, String dp, int taille, Sickness mal, int price){
        this.nom=nom;
        this.datePeremption=dp;
        this.taille=taille;
        this.maladie=mal;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(String datePeremption) {
        this.datePeremption = datePeremption;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public Sickness getMaladie() {
        return maladie;
    }

    public void setMaladie(Sickness maladie) {
        this.maladie = maladie;
    }
    
    
}

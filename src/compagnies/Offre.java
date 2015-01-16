package compagnies;



import jade.content.Concept;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author romain
 */
public class Offre implements Comparable, Serializable, Concept  {
    
    private Integer idOffre;
    private Vol vol;
    private double prixInitial;
    private double nouveauPrix;

    public Offre() {
    }

    public Offre(Integer idOffre, Vol vol, double prixInitial, double nouveauPrix) {
        this.idOffre = idOffre;
        this.vol = vol;
        this.prixInitial = prixInitial;
        this.nouveauPrix = nouveauPrix;
    }

    public Integer getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public double getNouveauPrix() {
        return nouveauPrix;
    }

    public void setNouveauPrix(double nouveauPrix) {
        this.nouveauPrix = nouveauPrix;
    }

    public double getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(double prixInitial) {
        this.prixInitial = prixInitial;
    }

    @Override
    public String toString() {
        return "id=" + idOffre + ", vol=" + vol.getId() + ", prixInitial=" + prixInitial + ", nouveauPrix=" + nouveauPrix;
    }

    @Override
    public int compareTo(Object o) {
        Offre o1 = (Offre)o;
       if (o1.getNouveauPrix() < this.getNouveauPrix())
            return 1;
        else if (o1.getNouveauPrix() == this.getNouveauPrix())
            return 0;
        else 
            return -1;
    }
      
}

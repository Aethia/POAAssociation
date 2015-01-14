package behaviors.data;


import java.io.Serializable;

/**
 * Created by RadiuM on 13/01/2015.
 */
public class Ville implements Serializable {

    private String nom;
    private Pays pays;

    public Ville(){
    }

    public Ville(String nom, Pays pays) {
        this.nom = nom;
        this.pays = pays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
    
    
}

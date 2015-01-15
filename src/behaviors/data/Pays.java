package behaviors.data;


import jade.content.Concept;
import java.io.Serializable;

/**
 * Created by RadiuM on 13/01/2015.
 */
public class Pays implements Serializable, Concept {
    private String name;


    public Pays(){

    }

    public Pays(String n){
        this.name=n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import behaviors.data.*;
/**
 * Created by RadiuM on 14/01/2015.
 */
public class DummyDatabase {



    private static double soldeBancaire = 0.0;




    public static void initListeVols(ArrayList<Vol> vols){

        int i=1;

        while(i<1000){
            Random randomizer=new Random();
            int annee=2015;
            int mois=randomizer.nextInt(10)+1;
            int jour=randomizer.nextInt(26)+1;

            Calendar tmpCal=Calendar.getInstance();
            tmpCal.set(annee,mois,jour);
            Date depart=tmpCal.getTime();
            tmpCal.set(annee,mois,jour+1);
            Date arrive=tmpCal.getTime();

            boolean charter=randomizer.nextBoolean();


            double prixUnitaire=randomizer.nextDouble()+randomizer.nextInt(2)+1;

            int capaciteOccupee=randomizer.nextInt(100);
            int capaciteMax=capaciteOccupee+100;

            double taxeAeroport=randomizer.nextDouble()+randomizer.nextInt(200)+1;

            int qteCarburant=randomizer.nextInt(1000);
            double prixCarburant=randomizer.nextDouble()+randomizer.nextInt(2)+1;

            int nbVillesParcourues=randomizer.nextInt(3)+2;

            ArrayList<Ville> villes=new ArrayList<Ville>();

            ArrayList<Ville> toutelesvilles=new ArrayList<Ville>();
            initListeVilles(toutelesvilles);

            for(int x=0;x<nbVillesParcourues;x++){
                villes.add(toutelesvilles.get(randomizer.nextInt(toutelesvilles.size() - 1)));
            }

            Vol generated=new Vol(i,depart,arrive,villes,charter,capaciteMax,prixUnitaire,capaciteOccupee,taxeAeroport,qteCarburant,prixCarburant,1);
            vols.add(generated);
            i++;
        }

    }

    public static void initListeVilles(ArrayList<Ville> villes){
        villes.add(new Ville("Niamey",new Pays("Nigeria")));
        villes.add(new Ville("Paris",new Pays("France")));
        villes.add(new Ville("Marseille",new Pays("France")));
        villes.add(new Ville("Dimapur",new Pays("Inde")));
        villes.add(new Ville("Calicut",new Pays("Inde")));
        villes.add(new Ville("Goa",new Pays("Inde")));
        villes.add(new Ville("Jakarta",new Pays("Indonesie")));
        villes.add(new Ville("Moscou",new Pays("Russie")));
        villes.add(new Ville("Pekin",new Pays("Chine")));
        villes.add(new Ville("Hong-Kong",new Pays("Chine")));
        villes.add(new Ville("New-York",new Pays("Etats-Unis")));
        villes.add(new Ville("Brasilia",new Pays("Bresil")));
        villes.add(new Ville("Rio de Janeiro",new Pays("Bresil")));
        villes.add(new Ville("Abidjan",new Pays("Cote d'Ivoire")));
        villes.add(new Ville("NDjamena",new Pays("Tchad")));
    }


    public static void initListePays(ArrayList<Pays> pays){
        pays.add(new Pays("Nigeria"));
        pays.add(new Pays("France"));
        pays.add(new Pays("Inde"));
        pays.add(new Pays("Indonesie"));
        pays.add(new Pays("Russie"));
        pays.add(new Pays("Chine"));
        pays.add(new Pays("Etats-Unis"));
        pays.add(new Pays("Bresil"));
        pays.add(new Pays("Vietnam"));
        pays.add(new Pays("Cote d'Ivoire"));
        pays.add(new Pays("Tchad"));
    }


    public static void incrSolde(double value){
        if(value>0)
        soldeBancaire+=value;
    }

    public static double getSolde() {
        return soldeBancaire;
    }
}

package compagnies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
 
/**
 * Created by RadiuM on 13/01/2015.
 */
public class Vol implements Serializable{
 
    private int id;
    private Date depart;
    private Date arrive;
    private ArrayList<Ville> escales;
 
 
 
    private int idCompany;
    private boolean charter;
    private int capaciteSouteMax;
    private double prixUnitaire;
    private int capaciteOccupee;
    private double taxeAeroport;
    private int qteCarburant;
    private double prixCarburant;
 
 
    public Vol(){
 
    }
 
    public Vol(int i,Date d, Date a, ArrayList<Ville> v, boolean chart, int cmax, double pu, int co, double ta, int qt, double pr,int idComp){
        this.id=i;
        this.depart=d;
        this.arrive=a;
        this.escales=v;
        this.charter=chart;
        this.capaciteSouteMax=cmax;
        this.capaciteOccupee=co;
        this.prixUnitaire=pu;
        this.taxeAeroport=ta;
        this.prixCarburant=pr;
        this.qteCarburant=qt;
        this.idCompany=idComp;
    }
 
    public double calculPrix(boolean isAbo,int capaciteVoulue,boolean lastMinute){
        double remiseAbo=0.10;
        double remiseCharter=0.5;
        double remiseCharterDerniereMinute=0.10;
 
        //double remiseMax=0.30;
 
        double prix;
 
        if((this.capaciteOccupee+capaciteVoulue)<this.capaciteSouteMax){
            prix=(capaciteVoulue*prixUnitaire)+(prixCarburant*qteCarburant)+taxeAeroport;
            double remise=0.0;
 
            if(charter && lastMinute){
                remise=remise+remiseCharterDerniereMinute;
            }
 
            if(charter && !lastMinute){
                remise=remise+remiseCharter;
            }
 
            if(isAbo){
                remise=remise+remiseAbo;
            }
 
            prix=prix*(1-remise);
        }else{
            prix=-1;
        }
 
        return prix;
    }
   
    public double calculPrix(double prixInitial, boolean isAbo, boolean lastMinute){
        double remiseAbo=0.10;
        double remiseCharter=0.05;
        double remiseCharterDerniereMinute=0.10;
 
        //double remiseMax=0.30;
 
        double remise=0.0;
 
        if(charter && lastMinute){
            remise=remise+remiseCharterDerniereMinute;
        }
 
        if(charter && !lastMinute){
            remise=remise+remiseCharter;
        }
 
        if(isAbo){
            remise=remise+remiseAbo;
        }
 
        prixInitial=prixInitial*(1-remise);
 
 
        return prixInitial;
    }
 
    public double calculPrixInitial(int capaciteVoulue){
 
        double prix;
 
        if((this.capaciteOccupee+capaciteVoulue)<this.capaciteSouteMax){
            prix=(capaciteVoulue*prixUnitaire)+(prixCarburant*qteCarburant)+taxeAeroport;
        }else{
            prix=-1;
        }
 
        return prix;
    }
 
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public double getTaxeAeroport() {
        return taxeAeroport;
    }
 
    public void setTaxeAeroport(double taxeAeroport) {
        this.taxeAeroport = taxeAeroport;
    }
 
    public boolean isCharter() {
        return charter;
    }
 
    public void setCharter(boolean charter) {
        this.charter = charter;
    }
 
    public int getQteCarburant() {
        return qteCarburant;
    }
 
    public void setQteCarburant(int qteCarburant) {
        this.qteCarburant = qteCarburant;
    }
 
    public double getPrixCarburant() {
        return prixCarburant;
    }
 
    public void setPrixCarburant(double prixCarburant) {
        this.prixCarburant = prixCarburant;
    }
 
    public Date getDepart() {
        return depart;
    }
 
    public void setDepart(Date depart) {
        this.depart = depart;
    }
    public int getIdCompany() {
        return idCompany;
    }
 
    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
 
 
    public Date getArrive() {
        return arrive;
    }
 
    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }
 
    public ArrayList<Ville> getEscales() {
        return escales;
    }
 
    public void setEscales(ArrayList<Ville> escales) {
        this.escales = escales;
    }
 
 
 
    public int getCapaciteSouteMax() {
        return capaciteSouteMax;
    }
 
    public void setCapaciteSouteMax(int capaciteSouteMax) {
        this.capaciteSouteMax = capaciteSouteMax;
    }
 
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
 
    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
 
    public int getCapaciteOccupee() {
        return capaciteOccupee;
    }
 
    public void setCapaciteOccupee(int capaciteOccupee) {
        this.capaciteOccupee = capaciteOccupee;
    }
 
 
}
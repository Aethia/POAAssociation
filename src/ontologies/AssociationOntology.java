/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ontologies;

import behaviors.data.Offre;
import behaviors.data.Pays;
import behaviors.data.Vol;
import business.SickPeople;
import business.Sickness;
import business.Vaccine;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PrimitiveSchema;

/**
 *
 * @author Aethia
 */
public class AssociationOntology extends Ontology{

    public static final String ONTOLOGY_NAME = "Association-ontology";
    
    //VOCABULAIRE
    public static final String SICKNESS ="Sickness";
    public static final String SICKNESS_NOM ="nom";
    public static final String SICKNESS_VIRULENCE ="virulence";
    public static final String SICKNESS_DELAI_INCUBATION ="delaiIncubation";
    
    public static final String VACCINE = "Vaccine";
    public static final String VACCINE_NOM = "nom";
    public static final String VACCINE_DATE_PEREMPTION = "datePeremption";
    public static final String VACCINE_MALADIE = "maladie";
    public static final String VACCINE_TAILLE = "taille";
    public static final String VACCINE_PRICE = "price";
    
    public static final String PAYS = "Pays";
    public static final String PAYS_NAME = "name";
    
    public static final String VOL = "Vol";
    public static final String VOL_ID ="id";
    public static final String VOL_DEPART ="depart";
    public static final String VOL_ARRIVE = "arrive";
    
    public static final String OFFRE = "Offre";
    public static final String OFFRE_IDOFFRE = "idOffre";
    public static final String OFFRE_VOL ="vol";
    public static final String OFFRE_PRIX_INITIAL = "prixInitial";
    public static final String OFFRE_NOUVEAU_PRIX = "nouveauPrix";
    
    public static final String SICKPEOPLE = "SickPeople";
    public static final String SICKPEOPLE_SICK ="sick";
    public static final String SICKPEOPLE_PAYS ="pays";
    public static final String SICKPEOPLE_NBSICK = "nbSick";
    
    private static Ontology theInstance = new AssociationOntology();
    
    public static Ontology getInstance(){
        return theInstance;
    }
    
    private AssociationOntology() {
        super(ONTOLOGY_NAME, BasicOntology.getInstance());
        
        try {
            add(new ConceptSchema(SICKNESS), Sickness.class);
            add(new ConceptSchema(VACCINE), Vaccine.class);
            add(new ConceptSchema(PAYS), Pays.class);
            add(new ConceptSchema(VOL), Vol.class);
            add(new ConceptSchema(OFFRE), Offre.class);
            add(new ConceptSchema(SICKPEOPLE), SickPeople.class);
            
            //structure du schema pour sickness concept
            ConceptSchema cs = (ConceptSchema) getSchema(SICKNESS);
            cs.add(SICKNESS_NOM, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(SICKNESS_DELAI_INCUBATION, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(SICKNESS_VIRULENCE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            
            //structure vaccine
            cs = (ConceptSchema) getSchema(VACCINE);
            cs.add(VACCINE_NOM, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(VACCINE_DATE_PEREMPTION, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(VACCINE_MALADIE, (ConceptSchema) getSchema(SICKNESS));
            cs.add(VACCINE_PRICE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(VACCINE_TAILLE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            
            //structure pays
            cs = (ConceptSchema) getSchema(PAYS);
            cs.add(PAYS_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            
            //structure vol
            cs = (ConceptSchema) getSchema(VOL);
            cs.add(VOL_ID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(VOL_DEPART, (PrimitiveSchema) getSchema(BasicOntology.DATE));
            cs.add(VOL_ARRIVE, (PrimitiveSchema) getSchema(BasicOntology.DATE));
            
            //Structure offre
            cs = (ConceptSchema) getSchema(OFFRE);
            cs.add(OFFRE_IDOFFRE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(OFFRE_VOL, (ConceptSchema) getSchema(VOL));
            cs.add(OFFRE_PRIX_INITIAL, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            cs.add(OFFRE_NOUVEAU_PRIX, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));
            
            //Structure SickPeople
            cs = (ConceptSchema) getSchema(SICKPEOPLE);
            cs.add(SICKPEOPLE_SICK, (ConceptSchema) getSchema(SICKNESS));
            cs.add(SICKPEOPLE_PAYS, (ConceptSchema) getSchema(PAYS));
            cs.add(SICKPEOPLE_NBSICK, (PrimitiveSchema) getSchema(BasicOntology.INTEGER));

            
            //on a pas pris en compte les tables intermediaires, comme on a simplifié
        } catch (OntologyException e) {
            e.printStackTrace();
        }
    }
    
    
}

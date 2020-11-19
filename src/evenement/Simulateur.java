package evenement;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.ImageElement;
import gui.Simulable;
import gui.Text;

import robot.*;
import strategie.ChefPompier;

import java.io.File;

import carte.*;
import donnees.*;

/**
 * Classe implémentant un simulateur qui représente la situation et gère les évènements
 */
public class Simulateur implements Simulable {
	private long dateSimulation;
	private Evenement premierEvent;
	private GUISimulator gui;
	private DonneesSimulation donnees;
    private ChefPompier chef;
    private String cartePath;
	
	/**
	 * Crée un simulateur en initialisant la date à 0 et le nombre d'évènements à 0
	 * @param gui Interface graphique associée au simulateur
	 * @param cartePath Chemin vers le fichier contenant les données de la carte
	 */
	public Simulateur(GUISimulator gui, String cartePath) {
		this.dateSimulation = 0;
		this.premierEvent = null;
//		this.nbEvents = 0;
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.cartePath = cartePath;
        this.donnees = new DonneesSimulation(new File(cartePath), this);
        this.chef = new ChefPompier(this);
        draw();
	}

	/**
	 *  Insère l'évènement e dans la liste des évènements 
	 *  @param e évènement à ajouter à la liste chaînée
	 */
	public void ajouteEvenement (Evenement e) {
		if (premierEvent == null) {
			// Cas liste vide
			premierEvent = e;
		} else if (premierEvent.getDate() >= e.getDate()) {
			// Cas insertion en tête
			e.setSuivant(premierEvent);
			premierEvent = e;
		} else if (premierEvent.getSuivant() == null) {
			// Cas un seul élément
			premierEvent.setSuivant(e);
		} else {
			Evenement prec = premierEvent;
			Evenement courant = premierEvent.getSuivant();
			while (courant != null && courant.getDate() < e.getDate()) {
				prec = courant;
				courant = courant.getSuivant();
			}
			e.setSuivant(courant);
			prec.setSuivant(e);
		}
	}
	
	/**
	 * Augmente la date de un
	 */
	public void incrementeDate() {
		dateSimulation++;
	}
	
	/**
	 * Indique si il reste des évènements à effectuer ou non
	 * @return true si il reste des évènements à effectuer, false sinon
	 */
	public boolean simulationTerminee() {
		return (premierEvent == null);
	}
	
	
	/**
	 * Renvoie la date actuelle
	 * @return Renvoie la date actuelle
	 */
	public long getDateSimulation() {
		return dateSimulation;
	}
	
	/**
	 * Renvoie le prochain évènement
	 * @return Renvoie le prochain évènement
	 */
	public Evenement getPremierEvent() {
		return premierEvent;
	}
	
	/**
	 * Modifie la tête de la liste chaînée d'évènements
	 * @param premierEvent Nouvelle tête de liste
	 */
	public void setPremierEvent(Evenement premierEvent) {
		this.premierEvent = premierEvent;
	}
	
	/**
	 * Récupère les données de la simulation
	 * @return Renvoie les données de la simulation
	 */
	public DonneesSimulation getDonnees() {
		return this.donnees;
	}
	
	/**
	 * Augmente la date, actualise la stratégie et la carte
	 */
	@Override
    public void next() {
    	chef.donneOrdre();
    	draw();
    	if (simulationTerminee()) return;
    	Evenement premierEvenement = getPremierEvent();
    	while (premierEvenement != null && getDateSimulation() >= premierEvenement.getDate()) {
    		premierEvenement.execute();
    		premierEvenement = premierEvenement.getSuivant();
    	}
    	setPremierEvent(premierEvenement);
    	if (donnees.getCarte().getTailleCases() != 10000) incrementeDate();
    	else for (int i = 0; i < 1; i++) incrementeDate();
    }

	/**
	 * Recharge les données initiales
	 */
    @Override
    public void restart() {
    	this.dateSimulation = 0;
		this.premierEvent = null;
        this.cartePath = cartePath;
        this.chef = new ChefPompier(this);
    	this.donnees.setDonnees(new File(this.cartePath));
        draw();
    }
    


    /**
     * Dessine la simulation
     */
    private void draw() {
        gui.reset();	// clear the window
        Carte carte = donnees.getCarte();
        int tailleCase = 0;
        if (carte.getNbColonnes() == 8) tailleCase = carte.getTailleCases() / 100;
        else if (carte.getNbColonnes() == 20) tailleCase = 45;
        else tailleCase = 20;

        for (int i = 0; i < carte.getNbLignes(); i++) {
        	for (int j = 0; j < carte.getNbColonnes(); j++) {
        		Case case_ij = carte.getCase(i, j);
        		case_ij.dessineCase(gui, tailleCase);
        	}
        }

        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	robots[i].draw(gui, tailleCase);;
        }
        if(this.simulationTerminee() && this.dateSimulation != 0) {
        	int titreX = (tailleCase*carte.getNbColonnes() - 500)/2;
        	int titreY = (tailleCase*carte.getNbLignes() - 280)/2;
        	gui.addGraphicalElement(new ImageElement(titreX, titreY, "images/titreFin.png", 500, 280, null));
        }
    }
}

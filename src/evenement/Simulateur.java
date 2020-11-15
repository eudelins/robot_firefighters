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
 * Classe implémentant un simulateur qui possède la date actuelle, le prochain
 * évènement planifié ainsi que le nombre total d'évènements planifiés
 */
public class Simulateur implements Simulable {
	private long dateSimulation;
	private Evenement premierEvent;
	private int nbEvents;
	private GUISimulator gui;
	private DonneesSimulation donnees;
    private ChefPompier chef;
    private String cartePath;
	
	/**
	 * Crée un simulateur en initialisant la date à 0 et le nombre d'évènements à 0
	 * @param gui Interface graphique associée au simulateur
	 * @param donnees Donnees de la simulation
	 * @param chef Chef pompier en charge de la stratégie des robots
	 * @param cartePath Chemin vers le fichier contenant les données de la carte
	 */
	public Simulateur(GUISimulator gui, String cartePath) {
		this.dateSimulation = 0;
		this.premierEvent = null;
		this.nbEvents = 0;
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
	
	@Override
    public void next() {
    	chef.donneOrdre();
    	if (simulationTerminee()) return;
    	Evenement premierEvenement = getPremierEvent();
    	while (premierEvenement != null && getDateSimulation() >= premierEvenement.getDate()) {
    		System.out.println(premierEvenement.getDate());
    		premierEvenement.execute();
    		premierEvenement = premierEvenement.getSuivant();
    	}
    	setPremierEvent(premierEvenement);
    	if (donnees.getCarte().getTailleCases() != 10000) incrementeDate();
    	else for (int i = 0; i < 1; i++) incrementeDate();
        draw();
    }

    @Override
    public void restart() {
    	this.dateSimulation = 0;
		this.premierEvent = null;
		this.nbEvents = 0;
        this.cartePath = cartePath;
        this.chef = new ChefPompier(this);
    	this.donnees.setDonnees(new File(this.cartePath));
        draw();
    }
    
    public void dessineCase(Case uneCase, int tailleCase) {
    	int coordX = uneCase.getColonne() * tailleCase;
		int coordY = uneCase.getLigne() * tailleCase;
    	switch (uneCase.getNature()) {
    	case EAU:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/eau.png", tailleCase, tailleCase, null));
    		break;
    	case FORET:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/foret.png", tailleCase, tailleCase, null));
    		break;
    	case ROCHE:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/rock.png", tailleCase, tailleCase, null));
    		break;
    	case TERRAIN_LIBRE:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/terrainlibre.png", tailleCase, tailleCase, null));
    		break;
    	case HABITAT:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/habitat.png", tailleCase, tailleCase, null));
    		break;
    	default:
    		return;
    	}
    	if (uneCase.getIncendie() != null) {
			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/feu.png", tailleCase, tailleCase, null));
			return;
		}
    }

    /**
     * Dessine la carte.
     */
    private void draw() {
        gui.reset();	// clear the window
        Carte carte = donnees.getCarte();
        int tailleCase = 0;
        if (carte.getTailleCases() >= 10000) tailleCase = carte.getTailleCases() / 100;
        else tailleCase = 45;

        for (int i = 0; i < carte.getNbLignes(); i++) {
        	for (int j = 0; j < carte.getNbColonnes(); j++) {
        		Case case_ij = carte.getCase(i, j);
        		this.dessineCase(case_ij, tailleCase);
        	}
        }

        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	robots[i].draw(gui, tailleCase);;
        }
    }
	
	public void incrementeDate() {
		dateSimulation++;
	}
	
	public boolean simulationTerminee() {
		return (premierEvent == null);
	}

	public long getDateSimulation() {
		return dateSimulation;
	}

	public Evenement getPremierEvent() {
		return premierEvent;
	}

	public int getNbEvents() {
		return nbEvents;
	}

	public void setPremierEvent(Evenement premierEvent) {
		this.premierEvent = premierEvent;
	}
	
	public DonneesSimulation getDonnees() {
		return this.donnees;
	}
}

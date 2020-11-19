package carte;

import gui.GUISimulator;
import gui.ImageElement;

/**
 * Classe représentant une case avec sa nature, sa ligne, sa colonne et l'incendie présent si il y en a un
 */

public class Case {
	private NatureTerrain nature;
	private int ligne;
	private int colonne;
	private Incendie incendie;
	private boolean estBrulee;
	
	/**
	 * Création d'une case
	 * @param natureTerrain nature du terrain de la case
	 * @param lig la ligne sur laquelle on la positionne
	 * @param col la colonne sur laquelle on la positionne
	 * @param incendie attribution d'un incendie si besoin
	 */
	public Case(NatureTerrain natureTerrain, int lig, int col, Incendie incendie) {
		super();
		this.nature= natureTerrain;
		this.ligne = lig;
		this.colonne = col;
		this.incendie = incendie;
		this.estBrulee = false;
	}
	
	/**
	 * Permet d'obtenir la nature du terrain de la case
	 * @return nature du terrain de la case
	 */
	public NatureTerrain getNature() {
		return nature;
	}
	
	/**
	 * Permet d'obtenir la ligne sur laquelle se trouve la case
	 * @return ligne sur laquelle se trouve la case
	 */
	public int getLigne() {
		return ligne;
	}
	
	/**
	 * Permet d'obtenir la colonne sur laquelle se trouve la case
	 * @return colonne sur laquelle se situe la case
	 */
	public int getColonne() {
		return colonne;
	}
	
	/**
	 * Permet d'obtenir l'incendie présent sur la case. Retourne null si il n'y en a pas
	 * @return incendie sur la case
	 */
	public Incendie getIncendie() {
		return incendie;
	}
	
	/**
	 * Met un incendie sur la case
	 * @param incendie que l'on sur la case
	 */
	public void setIncendie(Incendie incendie) {
		this.incendie = incendie;
	}

	/**
	 * Convertit la case en chaîne de caractère, en affichant sa nature, sa ligne, sa colonne, et son incendie
	 */
	@Override
	public String toString() {
		return "Case [nature=" + nature + ", ligne=" + ligne + ", colonne=" + colonne + ", incendie=" + incendie + "]";
	}


	/**
	 * Permet d'obtenir la destination entre la case et celle voisine donnée en destination
	 * @param destination case voisine dont on veut la direction
	 * @return la direction dans laquelle se situe la case voisine
	 */
	public Direction getDirection(Case destination){
		if(destination.getLigne() - this.ligne == 1){
			return Direction.SUD;
		}
		else if(destination.getLigne() - this.ligne == -1){
			return Direction.NORD;
		}
		else if(destination.getColonne() - this.colonne == 1){
			return Direction.EST;
		}
		else if(destination.getColonne() - this.colonne == -1){
			return Direction.OUEST;
		}
		return null; //case non voisine
	}

	/**
	 * Change l'etat brulé de la case
	 * @param brulee	booléen indiquant si la case est actuellement brulée ou non
	 */
	public void setEstBrulee(boolean brulee) {
		this.estBrulee = brulee;
	}

	/**
	 * Indique si la case a été brulée par un incendie
	 * @return renvoie si la case est brulee par un incendie ou non
	 */
	public boolean isBrulee() {
		return this.estBrulee;
	}

    /**
     * Dessine une case de la carte
     * @param uneCase La case à dessiner
     * @param tailleCase La taille du côté de la case
     */
    public void dessineCase(GUISimulator gui, int tailleCase) {
    	int coordX = this.getColonne() * tailleCase;
		int coordY = this.getLigne() * tailleCase;


		// On choisit l'image en fonction de la nature de la case
		switch (this.getNature()) {
    	case EAU:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/eau.png", tailleCase, tailleCase, null));
    		break;
    	case FORET:
    		if(this.isBrulee()) {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/foretBrulee.png", tailleCase, tailleCase, null));
    			return;
    		} else {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/foret.png", tailleCase, tailleCase, null));
    		}
    		break;
    	case ROCHE:
    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/rock.png", tailleCase, tailleCase, null));
    		break;
    	case TERRAIN_LIBRE:
    		if(this.isBrulee()) {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/terrainlibreBrulee.png", tailleCase, tailleCase, null));
    			return;
    		} else {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/terrainlibre.png", tailleCase, tailleCase, null));
    		}
    		break;
    	case HABITAT:
    		if(this.isBrulee()) {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/habitatBrulee.png", tailleCase, tailleCase, null));
    			return;
    		} else {
    			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/habitat.png", tailleCase, tailleCase, null));
    		}
    		break;
    	default:
    		return;
    	}
		// On rajoute des flammes si un incendie est présent sur la case
    	if (this.getIncendie() != null) {
			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/feu.png", tailleCase, tailleCase, null));
			return;
		}
    }
}

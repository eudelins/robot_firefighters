package robot;
import java.awt.Color;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.Rectangle;
import gui.ImageElement;

/**
 * Classe représentant les robots possédant des pattes
 * @author equipe 66
 */
public class RobotAPattes extends Robot {
	
	/** Créer un Robot à pattes avec la quantite d'eau infinie dans son réservoir   
	 * 	@param carte	 	carte sur lequel le robot se déplace
	 * 	@param position		postion initiale du robot
	 * 	@param simul		simulateur de l'éxécution
	 * 	@param vitesse		vitesse initiale du robot
	 */ 
	public RobotAPattes(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 1073741823, vitesse);
		assert(vitesse == 30 && position.getNature() != NatureTerrain.EAU);
		if (position.getNature() == NatureTerrain.ROCHE) this.setVitesse(20);
		super.addTerrainInterdit(NatureTerrain.EAU);
	}


	/** Change la position du robot et adapte sa vitesse au passage   
	 * 	@param newPosition	case à laquelle le repond doit se repositionner
	 */
	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU);
		if (nature == NatureTerrain.ROCHE) this.setVitesse(20);
		else this.setVitesse(30);
		super.setPosition(newPosition);
	}


	@Override
	/** Renvoie le temps mis pour accéder à une case voisine  
	 * 	@param dir	direction vers laquelle se diriger
	 * 	@return temps d'accès au voisin 
	 */
	public int tempsAccesVoisin(Direction dir) {
		Carte carte = this.getCarte();
		int semiDistance = carte.getTailleCases()/2;
		double vitesseMetreParSeconde = this.getVitesse() / 3.6;
		double tempsSortieDouble = semiDistance / vitesseMetreParSeconde;
		int tempsSortieCase = (int)tempsSortieDouble;

		int vitesseFutur = this.getVitesse();
		int lig = this.getPosition().getLigne();
		int col = this.getPosition().getColonne();
		Case nextCase = null;


		switch (dir) {
		case NORD:
			assert(lig > 0);
			nextCase = carte.getCase(lig - 1, col);
			break;
		case SUD:
			assert(lig < carte.getNbLignes() - 1);
			nextCase = carte.getCase(lig + 1, col);
			break;
		case OUEST:
			assert(col > 0);
			nextCase = carte.getCase(lig, col - 1);
			break;
		case EST:
			assert(col < carte.getNbColonnes() - 1);
			nextCase = carte.getCase(lig, col + 1);
		}

		if (nextCase.getNature() == NatureTerrain.ROCHE) vitesseFutur = 20;
		else vitesseFutur = 30;
		
		double vitesseFuturMetreParSeconde = vitesseFutur / 3.6;
		double tempsArriveeDouble = semiDistance / vitesseFuturMetreParSeconde;
		int tempsArriveeNewCase = (int) tempsArriveeDouble;

		return tempsArriveeNewCase + tempsSortieCase;
	}
	
	/** Chaque robot remplit son réservoir d'une manière différente */
	@Override
	public void remplirReservoir() {
		return;
	}
	
	/** 
	 * 	Renvoie la durée mis par le robot pour remplir son réservoir 
	 * 	@return la durée de remplissage
	 */
	@Override
	public int dureeRemplissage() {
		return 0;
	}
	
	/** Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau    
	 * 	@param quatiteNecessaire	quantite d'eau qu'il faut déverser
	 * 	@return la durée de déversement
	 */
	@Override
	public int dureeDeversage(int quantiteNecessaire) {
		return quantiteNecessaire / 10;
	}
	
	/** Modifie la quantité d'eau dans le réservoir du robot     
	 * 	@param quatiteEau	ce paramètre ne sert à rien pour ce robot
	 */
	@Override
	public void setQuantiteEau(int quantiteEau) {
		super.setQuantiteEau(1073741823);
	}

	
	/** 
	 * 	Renvoie la capacité maximale du reservoir du robot  
	 * 	@return la capcité maximale du réservoir
	 */
	@Override
	public int capaciteReservoire() {
		return 1073741823;
	}
	
	/**
     * Dessine le robot
     * @param gui 			l'interface graphique associée à l'exécution, dans laquelle se fera le
     * 						dessin.
     * @param tailleCase	taille des cases de la simulation courante
    */
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
    	int caseX = caseRobot.getColonne() * tailleCase;
    	int caseY = caseRobot.getLigne() * tailleCase;
    	int rectWidth = tailleCase/3;
    	int rectHeight = 4*tailleCase/10;

    	// Dessin du robot avec une image
    	gui.addGraphicalElement(new ImageElement(caseX, caseY, "images/robotAPatte.png", tailleCase, tailleCase, null));
    	super.drawReservoir(gui, rectHeight + rectWidth*2/3, tailleCase, 1073741823);
	}

}

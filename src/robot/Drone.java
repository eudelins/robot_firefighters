package robot;
import java.awt.Color;

import carte.Carte;
import carte.Case;
import carte.NatureTerrain;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Oval;
import gui.Rectangle;


public class Drone extends Robot {

	/** Créer un Drone en indiquant la quatite d'eau initiale dans le réservoir 
	 * 	@param carte	 	carte sur lequel le robot se déplace
	 * 	@param position		postion initiale du robot
	 * 	@param simul		simulateur de l'éxécution
	 * 	@param quantiteEau	quantite d'eau initiale dans le réservoir du robot
	 * 	@param vitesse		vitesse initiale du robot
	 */ 
	public Drone(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		super(carte, position, simul, quantiteEau, vitesse);
		assert(quantiteEau <= 10000);
	}
	
	/** Créer un Robot à chenille avec la quantite d'eau maximale dans son réservoir  
	 * 	@param carte	 	carte sur lequel le robot se déplace
	 * 	@param position		postion initiale du robot
	 * 	@param simul		simulateur de l'éxécution
	 * 	@param vitesse		vitesse initiale du robot
	 */ 
	public Drone(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 10000, vitesse);
	}

	/** Chaque robot remplit son réservoir d'une manière différente */
	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			this.setStopped(true);
			this.setQuantiteEau(10000);
		}
	}
	
	/** 
	 * 	Renvoie la durée mis par le robot pour remplir son réservoir 
	 * 	@return la durée remplissage
	 */
	@Override
	public int dureeRemplissage() {
		return 30 * 60;
	}
	
	/** 
	 * 	Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau  
	 * 	@param quatiteNecessaire quantite d'eau qu'il faut déverser
	 * 	@return la durée de déversement
	 */
	@Override
	public int dureeDeversage(int quantiteNecessaire) {
		return 30;
	}
	
	
	/** 
	 * 	Renvoie la capcité maximale du reservoir du robot  
	 * 	@return la  capcité maximale du reservoir
	 */
	@Override
	public int capaciteReservoire() {
		return 10000;
	}
	
	
	@Override
	/** 
	 * 	Renvoie le point d'accès à l'eau du robot le plus proche 
	 * 	@return la case d'eau la plus proche ou null s'il y en a pas
	 */
	public Case accesEauPlusProche() {
		Case position = this.getPosition();
		if (position.getNature() == NatureTerrain.EAU) return position;

		Carte carteRob = this.getCarte();
		int ligCourante = position.getLigne(), colCourante = position.getColonne();
		int maxDist = Math.max(carteRob.getNbColonnes(), carteRob.getNbLignes());
		for (int dist = 1; dist < maxDist; dist++) {
			for (int lig = ligCourante - dist; lig <= ligCourante + dist; lig++) {
				int ecartLig = dist - Math.abs(ligCourante - lig);
				for (int col = colCourante - ecartLig; col <= colCourante + ecartLig; col++) {
					if (lig < 0 || lig >= carteRob.getNbLignes()) continue;
					if (col < 0 || col >= carteRob.getNbColonnes()) continue;
					Case voisin = carteRob.getCase(lig, col);
					if (voisin.getNature() == NatureTerrain.EAU) return voisin;
				}
			}
		}
		return null;
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
    	int rectX = caseX + tailleCase/3 + tailleCase/6;
    	int rectY = caseY + tailleCase/3 + tailleCase/6;
    	int rectSize = tailleCase/3;
    	
    	// Dessin du drone avec des figures
//    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectSize));
//    	for(int k = 0; k<=1; ++k) {
//    		for(int j = 0; j<= 1; ++j) {
//    			int ovalX = caseX + (1+j)*tailleCase/3;
//    			int ovalY = caseY + (1+k)*tailleCase/3;
//    			int ovalSize = rectSize*9/10;
//    			gui.addGraphicalElement(new Oval(ovalX, ovalY, Color.BLACK, null, ovalSize));
//    			gui.addGraphicalElement(new Rectangle(ovalX, ovalY, Color.BLACK, Color.BLACK, 1, ovalSize));
//    			gui.addGraphicalElement(new Rectangle(ovalX, ovalY, Color.BLACK, Color.BLACK, ovalSize, 1));
//    		}
//    	}
    	
    	// Dessin du drone avec une image
    	gui.addGraphicalElement(new ImageElement(caseX, caseY, "images/drone.png", tailleCase, tailleCase, null));
    	super.drawReservoir(gui, rectSize + rectSize*9/10, tailleCase, 10000);
	}

}

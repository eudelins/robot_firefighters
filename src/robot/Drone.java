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
	
	public Drone(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		super(carte, position, simul, quantiteEau, vitesse);
		assert(quantiteEau <= 10000);
	}
	
	public Drone(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 10000, vitesse);
	}

	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			this.setStopped(true);
			this.setQuantiteEau(10000);
		}
	}
	
	@Override
	public int dureeRemplissage() {
		return 30 * 60;
	}
	
	@Override
	public int dureeDeversage(int quantiteNecessaire) {
		return 30;
	}
	
	
	/** Renvoie la capcité maximale du reservoir du robot  */
	@Override
	public int capaciteReservoire() {
		return 10000;
	}
	
	
	@Override
	/** Renvoie le point d'accès à l'eau du robot le plus proche */
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
	
	
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
    	int rectX = caseX + tailleCase/3 + tailleCase/6;
    	int rectY = caseY + tailleCase/3 + tailleCase/6;
    	int rectSize = tailleCase/3;
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
    	gui.addGraphicalElement(new ImageElement(caseX, caseY, "images/drone.png", tailleCase, tailleCase, null));
    	super.drawReservoir(gui, rectSize + rectSize*9/10, tailleCase, 10000);
	}

}

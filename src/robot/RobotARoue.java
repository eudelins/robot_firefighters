package robot;
import java.awt.Color;

import carte.*;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotARoue extends Robot{
	
	public RobotARoue(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
		assert(position.getNature() == NatureTerrain.HABITAT || position.getNature() == NatureTerrain.TERRAIN_LIBRE);
		assert(quantiteEau <= 5000);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature == NatureTerrain.HABITAT || nature == NatureTerrain.TERRAIN_LIBRE);
		super.setPosition(newPosition);
	}
	
	@Override
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol); 
		// Il faut trouver un moyen d'accéder à l'objet "Incendie" lié à la case sur le quel se trouve le robot
		// A compléter
	}

	@Override
	public void remplirReservoir() {
		boolean voisinAvecEau = true; // Faire une fonction qui vérifie si une case voisin a pour nature EAU
		if (voisinAvecEau) {
			this.setStopped(true);
			try {
				Thread.sleep(10000); // Il me semble que ça met en pause la fonction (pas tout le programme) 
									 // pour le temps indiqué (j'ai choisi 30 min = 30 sec)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setQuantiteEau(5000);
			this.setStopped(false);
			// à compléter et vérifier
		}
	}
	
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getLigne() * tailleCase;
    	int caseY = caseRobot.getColonne() * tailleCase;
    	int rectX = caseX + tailleCase/3 + tailleCase/6;
    	int rectY = caseY + tailleCase/2;
    	int rectWidth = tailleCase/3;
    	int rectHeight = tailleCase/2;
    	
    	for(int k = 0; k<=1; ++k) {
    		for(int j = 0; j<= 1; ++j) {
    			int ovalX = caseX + (1+k)*tailleCase/3;
    			int ovalY = caseY + (1+2*j)*tailleCase/4;
    			gui.addGraphicalElement(new Oval(ovalX, ovalY, Color.BLACK, Color.DARK_GRAY, rectWidth/2));
    		}
    	}
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
	}
}

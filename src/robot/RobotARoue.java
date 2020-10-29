package robot;
import java.awt.Color;

import carte.*;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotARoue extends Robot{
	
	public RobotARoue(Carte carte, Case position, int quantiteEau, int vitesse) {
		super(carte, position, quantiteEau, vitesse);
		assert(position.getNature() == NatureTerrain.HABITAT || position.getNature() == NatureTerrain.TERRAIN_LIBRE);
		assert(quantiteEau <= 5000);
	}
	
	public RobotARoue(Carte carte, Case position, int vitesse) {
		super(carte, position, 5000, vitesse);
		assert(position.getNature() == NatureTerrain.HABITAT || position.getNature() == NatureTerrain.TERRAIN_LIBRE);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature == NatureTerrain.HABITAT || nature == NatureTerrain.TERRAIN_LIBRE);
		super.setPosition(newPosition);
	}

	@Override
	public void remplirReservoir() {
		boolean voisinAvecEau = this.estVoisinEau();
		if (voisinAvecEau) {
			this.setStopped(true);
			this.setQuantiteEau(5000);
			this.setStopped(false);
		}
	}
	
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
		int rectX = caseX + tailleCase/2;
    	int rectY = caseY + tailleCase/3 + tailleCase/6;
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

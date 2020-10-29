package robot;
import java.awt.Color;

import carte.*;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotAPattes extends Robot {

	public RobotAPattes(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU);
		if (nature == NatureTerrain.ROCHE) this.setVitesse(20);
		else this.setVitesse(30);
		super.setPosition(newPosition);
	}
	
	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub

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
    			gui.addGraphicalElement(new Rectangle(ovalX, ovalY, Color.BLACK, Color.DARK_GRAY, rectWidth/2));
    		}
    	}
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
	}

}

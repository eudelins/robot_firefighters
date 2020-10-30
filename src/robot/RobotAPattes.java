package robot;
import java.awt.Color;

import carte.*;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotAPattes extends Robot {

	public RobotAPattes(Carte carte, Case position, int vitesse) {
		super(carte, position, 0x7FFFFFFF, vitesse);
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
	public void remplirReservoir() {
		return;
	}
	
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
    	int caseX = caseRobot.getColonne() * tailleCase;
    	int caseY = caseRobot.getLigne() * tailleCase;
    	int rectX = caseX + tailleCase/3 + tailleCase/6;
    	int rectY = caseY + tailleCase/2;
    	int rectWidth = tailleCase/3;
    	int rectHeight = tailleCase/2;
    	
    	for(int k = 0; k<=1; ++k) {
    		for(int j = 0; j<= 1; ++j) {
    			int patteX = caseX + (1+k)*tailleCase/3;
    			int patteY = caseY + (1+2*j)*tailleCase/4;
    			gui.addGraphicalElement(new Rectangle(patteX, patteY, Color.BLACK, Color.DARK_GRAY, rectWidth/2));
    		}
    	}
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
    	super.drawReservoir(gui, rectHeight + rectWidth/2, tailleCase, 0x7FFFFFFF);
	}

}

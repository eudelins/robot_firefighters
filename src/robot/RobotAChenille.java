package robot;
import java.awt.Color;

import carte.*;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotAChenille extends Robot {

	public RobotAChenille(Carte carte, Case position, int quantiteEau, int vitesse) {
		super(carte, position, quantiteEau, vitesse);
		assert(quantiteEau <= 2000);
	}
	
	public RobotAChenille(Carte carte, Case position, int vitesse) {
		super(carte, position, 2000, vitesse);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE);
		if (nature == NatureTerrain.FORET) this.setVitesse(this.getVitesse() / 2);;
		super.setPosition(newPosition);
	}

	@Override
	public void remplirReservoir() {
		if (this.estVoisinEau()) {
			this.setStopped(true);
			this.setQuantiteEau(2000);
			this.setStopped(false);
		}
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
			int ovalX = caseX + (1+k)*tailleCase/3;
			int ovalWidth = rectWidth/2;
			int ovalHeight = rectHeight*4/3; 
			gui.addGraphicalElement(new Oval(ovalX, rectY, Color.BLACK, Color.DARK_GRAY, ovalWidth, ovalHeight));
    	}
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
	}

}

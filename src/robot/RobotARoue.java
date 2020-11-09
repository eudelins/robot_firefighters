package robot;
import java.awt.Color;

import carte.Carte;
import carte.Case;
import carte.NatureTerrain;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotARoue extends Robot{
	
	public RobotARoue(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		super(carte, position, simul, quantiteEau, vitesse);
		super.terrainInterdit.add(NatureTerrain.EAU);
		super.terrainInterdit.add(NatureTerrain.ROCHE);
		super.terrainInterdit.add(NatureTerrain.FORET);
		assert(!(super.terrainInterdit.contains(position.getNature())));
		assert(quantiteEau <= 5000);
	}
	
	public RobotARoue(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 5000, vitesse);
		super.terrainInterdit.add(NatureTerrain.EAU);
		super.terrainInterdit.add(NatureTerrain.ROCHE);
		super.terrainInterdit.add(NatureTerrain.FORET);
		assert(!(super.terrainInterdit.contains(position.getNature())));
	}

	
	/** Change la position du robot et adapte sa vitesse au passage */
	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature == NatureTerrain.HABITAT || nature == NatureTerrain.TERRAIN_LIBRE);
		super.setPosition(newPosition);
	}

	@Override
	public void remplirReservoir() {
		if (this.estVoisinEau() != null) {
			this.setStopped(true);
			this.setQuantiteEau(5000);
			this.setStopped(false);
		}
	}
	
	@Override
	public int dureeRemplissage() {
		return 10 * 60;
	}
	
	@Override
	public int dureeDeversage(int quantiteNecessaire) {
		return 5 * (quantiteNecessaire / 100);
	}
	
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
		int rectX = caseX + tailleCase/2;
    	int rectY = caseY + tailleCase/3 + tailleCase/6;
    	int rectWidth = tailleCase/3;
    	int rectHeight = 4*tailleCase/10;
    	
    	for(int k = 0; k<=1; ++k) {
    		for(int j = 0; j<= 1; ++j) {
    			int ovalX = caseX + (1+k)*tailleCase/3;
    			int ovalY = caseY + (3+4*j)*tailleCase/10;
    			gui.addGraphicalElement(new Oval(ovalX, ovalY, Color.BLACK, Color.DARK_GRAY, rectWidth/2));
    		}
    	}
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
    	super.drawReservoir(gui, rectHeight + rectWidth/2, tailleCase, 5000);
	}
}

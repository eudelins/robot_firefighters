package robot;
import java.awt.Color;

import carte.Case;
import carte.NatureTerrain;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;
import gui.Text;

public class Drone extends Robot {

	public Drone(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
		assert(quantiteEau <= 10000);
	}

	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			this.setStopped(true);
			try {
				Thread.sleep(30000); // Il me semble que ça met en pause la fonction (pas tout le programme) 
									 // pour le temps indiqué (j'ai choisi 30 min = 30 sec)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setQuantiteEau(10000);
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
    	int rectY = caseY + tailleCase/3 + tailleCase/6;
    	int rectSize = tailleCase/3;
    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectSize));
    	for(int k = 0; k<=1; ++k) {
    		for(int j = 0; j<= 1; ++j) {
    			int ovalX = caseX + (1+k)*tailleCase/3;
    			int ovalY = caseY + (1+j)*tailleCase/3;
    			int ovalSize = rectSize*9/10;
    			gui.addGraphicalElement(new Oval(ovalX, ovalY, Color.BLACK, null, ovalSize));
    			gui.addGraphicalElement(new Rectangle(ovalX, ovalY, Color.BLACK, Color.BLACK, 1, ovalSize));
    			gui.addGraphicalElement(new Rectangle(ovalX, ovalY, Color.BLACK, Color.BLACK, ovalSize, 1));
    		}
    	}
	}

}

package robot;
import java.awt.Color;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.NatureTerrain;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.Rectangle;


public class RobotAPattes extends Robot {

	public RobotAPattes(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 1073741823 , vitesse);
	}

	
	/** Change la position du robot et adapte sa vitesse au passage */
	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU);
		if (nature == NatureTerrain.ROCHE) this.setVitesse(20);
		else this.setVitesse(30);
		super.setPosition(newPosition);
	}

	
	@Override
	/** Renvoie le temps mis pour accéder à une case voisine */
	public int tempsAccesVoisin(Direction dir) {
		Carte carte = this.getCarte();
		int semiDistance = carte.getTailleCases()/2;
		int tempsSortieCase = semiDistance/this.getVitesse();
		
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
		
		int tempsArriveeNewCase = semiDistance/vitesseFutur;
		
		return tempsArriveeNewCase + tempsSortieCase;
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
    	super.drawReservoir(gui, rectHeight + rectWidth/2, tailleCase, 1073741823 );
	}

}

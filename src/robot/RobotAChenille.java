package robot;
import java.awt.Color;

import carte.*;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;


public class RobotAChenille extends Robot {

	public RobotAChenille(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		super(carte, position, simul, quantiteEau, vitesse);
		assert(quantiteEau <= 2000);
	}
	
	public RobotAChenille(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 2000, vitesse);
	}

	/** Change la position du robot et adapte sa vitesse au passage */
	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE);

		NatureTerrain oldNature = this.getPosition().getNature();
		if (nature == NatureTerrain.FORET && oldNature != NatureTerrain.FORET)
			this.setVitesse(this.getVitesse() / 2);

		if (nature != NatureTerrain.FORET && oldNature == NatureTerrain.FORET)
			this.setVitesse(this.getVitesse() * 2);
		super.setPosition(newPosition);
	}
	
	
	@Override
	/** Renvoie le temps mis pour accéder à une case voisine */
	public int tempsAccesVoisin(Direction dir) {
		int semiDistance = this.carte.getTailleCases()/2;
		int tempsSortieCase = semiDistance/this.getVitesse();
		
		int vitesseFutur = this.getVitesse();
		Carte carte = this.getCarte();
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
		
		if (nextCase.getNature() == NatureTerrain.FORET && this.getPosition().getNature() != NatureTerrain.FORET)
			vitesseFutur = this.getVitesse() / 2;
		if (nextCase.getNature() != NatureTerrain.FORET && this.getPosition().getNature() == NatureTerrain.FORET)
			vitesseFutur = this.getVitesse() * 2;

		int tempsArriveeNewCase = semiDistance/vitesseFutur;
		
		return tempsArriveeNewCase + tempsSortieCase;
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
    	super.drawReservoir(gui, rectHeight + rectHeight*4/3, tailleCase, 2000);
	}

}

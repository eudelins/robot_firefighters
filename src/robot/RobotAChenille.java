package robot;
import java.awt.Color;

import carte.*;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Oval;
import gui.Rectangle;


public class RobotAChenille extends Robot {
	
	/** Créer un Robot à chenille en indiquant la quatite d'eau initiale dans le réservoir */
	public RobotAChenille(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		super(carte, position, simul, quantiteEau, vitesse);
		assert(quantiteEau <= 2000);
		super.terrainInterdit.add(NatureTerrain.EAU);
		super.terrainInterdit.add(NatureTerrain.ROCHE);
	}

	/** Créer un Robot à chenille avec la quantite d'eau maximale dans son réservoir */
	public RobotAChenille(Carte carte, Case position, Simulateur simul, int vitesse) {
		super(carte, position, simul, 2000, vitesse);
		assert(vitesse <= 80);
		super.terrainInterdit.add(NatureTerrain.EAU);
		super.terrainInterdit.add(NatureTerrain.ROCHE);
		NatureTerrain nature = position.getNature();
		assert(!(super.terrainInterdit.contains(nature)));
		if (nature == NatureTerrain.FORET) this.setVitesse(vitesse / 2);
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
		Carte carte = this.getCarte();
		int semiDistance = carte.getTailleCases()/2;
		double vitesseMetreParSeconde = this.getVitesse() / 3.6;
		double tempsSortieDouble = semiDistance / vitesseMetreParSeconde;
		int tempsSortieCase = (int)tempsSortieDouble;

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

		if (nextCase.getNature() == NatureTerrain.FORET && this.getPosition().getNature() != NatureTerrain.FORET)
			vitesseFutur = this.getVitesse() / 2;
		if (nextCase.getNature() != NatureTerrain.FORET && this.getPosition().getNature() == NatureTerrain.FORET)
			vitesseFutur = this.getVitesse() * 2;

		double vitesseFuturMetreParSeconde = vitesseFutur / 3.6;
		double tempsArriveeDouble = semiDistance / vitesseFuturMetreParSeconde;
		int tempsArriveeNewCase = (int)tempsArriveeDouble;

		return tempsArriveeNewCase + tempsSortieCase;
	}
	
	/** Calcul et renvoie le temps mis pour accéder à une case voisine à partir d'une case quelconque */
	@Override
	public int tempsAccesVoisin(Case caseDepart, Direction dir) {
		Carte carte = this.getCarte();
		int semiDistance = carte.getTailleCases()/2;
		double vitesseMetreParSeconde = this.getVitesse() / 3.6;
		double tempsSortieDouble = semiDistance / vitesseMetreParSeconde;
		int tempsSortieCase = (int)tempsSortieDouble;

		int vitesseFutur = this.getVitesse();
		int lig = caseDepart.getLigne();
		int col = caseDepart.getColonne();
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

		double vitesseFuturMetreParSeconde = vitesseFutur / 3.6;
		double tempsArriveeDouble = semiDistance / vitesseFuturMetreParSeconde;
		int tempsArriveeNewCase = (int)tempsArriveeDouble;

		return tempsArriveeNewCase + tempsSortieCase;
	}


	/** Renvoie la durée mis par le robot pour remplir son réservoir */
	@Override
	public int dureeRemplissage() {
		return 5 * 60;
	}
	
	
	/** Renvoie la capcité maximale du reservoir du robot  */
	@Override
	public int capaciteReservoire() {
		return 2000;
	}
	
	/** Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau */
	@Override
	public int dureeDeversage(int quantiteNecessaire) {
		return 8 * (quantiteNecessaire / 100);
	}

	/** Chaque robot remplit son réservoir d'une manière différente */
	@Override
	public void remplirReservoir() {
		if (this.estVoisinEau() != null) {
			this.setStopped(true);
			this.setQuantiteEau(2000);
			this.setStopped(false);
		}
	}

	/**
     * Dessine le robot
     * @param gui l'interface graphique associée à l'exécution, dans laquelle se fera le
     * dessin.
    */
	@Override
	public void draw(GUISimulator gui, int tailleCase) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
    	int rectX = caseX + tailleCase/3 + tailleCase/6;
    	int rectY = caseY + tailleCase/2;
    	int rectWidth = tailleCase/3;
    	int rectHeight = 4*tailleCase/10;

    	// Dessin du robot avec des figures
//    	for(int k = 0; k<=1; ++k) {
//			int ovalX = caseX + (1+k)*tailleCase/3;
//			int ovalWidth = rectWidth/2;
//			int ovalHeight = rectHeight*4/3;
//			gui.addGraphicalElement(new Oval(ovalX, rectY, Color.BLACK, Color.DARK_GRAY, ovalWidth, ovalHeight));
//    	}
//    	gui.addGraphicalElement(new Rectangle(rectX, rectY, Color.BLACK, Color.gray, rectWidth, rectHeight));
    	
    	// Dessin du robot avec une image
    	gui.addGraphicalElement(new ImageElement(caseX, caseY, "images/robotAChenille.png", tailleCase, tailleCase, null));
    	super.drawReservoir(gui, rectHeight + rectWidth*2/3, tailleCase, 2000);
	}

}

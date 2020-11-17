package test;


import java.awt.Color;

import gui.GUISimulator;

import evenement.*;

public class TestSimulateur {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        String cartePath = "cartes/mushroomOfHell-20x20.map";
//        String cartePath = "cartes/spiralOfMadness-50x50.map";
//        String cartePath = "cartes/desertOfDeath-20x20.map";
//        String cartePath = "cartes/carteSujet.map";
        
		Simulateur simul = new Simulateur(gui, cartePath);
	}
}


//class SimulateurGui implements Simulable {
//	/** L'interface graphique associée */
//    private GUISimulator gui;
//    private Simulateur simul;
//    private DonneesSimulation donnees;
//    private ChefPompier chef;
//    private String cartePath;
//
//    /**
//     * Crée une carte et la dessine.
//     * @param gui l'interface graphique associée, dans laquelle se fera le
//     * dessin et qui enverra les messages via les méthodes héritées de
//     * Simulable.
//     * @param color la couleur des cases de la carte
//     */
//    public SimulateurGui(GUISimulator gui, DonneesSimulation donnees, Simulateur simul, ChefPompier chef, String cartePath) {
//        this.gui = gui;
//        gui.setSimulable(this);				// association a la gui!
//        this.donnees = donnees;
//        this.simul = simul;
//        this.chef = chef;
//        this.cartePath = cartePath;
//        draw();
//    }
//
//    @Override
//    public void next() {
//    	chef.donneOrdre();
//    	if (simul.simulationTerminee()) return;
//    	Evenement premierEvenement = simul.getPremierEvent();
//    	while (premierEvenement != null && simul.getDateSimulation() >= premierEvenement.getDate()) {
//    		System.out.println(premierEvenement.getDate());
//    		premierEvenement.execute();
//    		premierEvenement = premierEvenement.getSuivant();
//    	}
//    	simul.setPremierEvent(premierEvenement);
//    	if (donnees.getCarte().getTailleCases() != 10000) simul.incrementeDate();
//    	else for (int i = 0; i < 1; i++) simul.incrementeDate();
////    	for (int i = 0; i < 100; i++) simul.incrementeDate();
//        draw();
//    }
//
//    @Override
//    public void restart() {
//    	this.donnees.setDonnees(new File(this.cartePath));
//    	this.chef.setDonnees(this.donnees);
//        draw();
//    }
//
//    
//    public void dessineCase(Case uneCase, int tailleCase) {
//    	int coordX = uneCase.getColonne() * tailleCase;
//		int coordY = uneCase.getLigne() * tailleCase;
//    	switch (uneCase.getNature()) {
//    	case EAU:
//    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/eau.png", tailleCase, tailleCase, null));
//    		break;
//    	case FORET:
//    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/foret.png", tailleCase, tailleCase, null));
//    		break;
//    	case ROCHE:
//    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/rock.png", tailleCase, tailleCase, null));
//    		break;
//    	case TERRAIN_LIBRE:
//    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/terrainlibre.png", tailleCase, tailleCase, null));
//    		break;
//    	case HABITAT:
//    		gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/habitat.png", tailleCase, tailleCase, null));
//    		break;
//    	default:
//    		return;
//    	}
//    	if (uneCase.getIncendie() != null) {
//			gui.addGraphicalElement(new ImageElement(coordX, coordY, "images/feu.png", tailleCase, tailleCase, null));
//			return;
//		}
//    }
//
//    /**
//     * Dessine la carte.
//     */
//    private void draw() {
//        gui.reset();	// clear the window
//        Carte carte = donnees.getCarte();
//        int tailleCase = 0;
//        if (carte.getTailleCases() >= 10000) tailleCase = carte.getTailleCases() / 100;
//        else tailleCase = 45;
//
//        for (int i = 0; i < carte.getNbLignes(); i++) {
//        	for (int j = 0; j < carte.getNbColonnes(); j++) {
//        		Case case_ij = carte.getCase(i, j);
//        		this.dessineCase(case_ij, tailleCase);
//        	}
//        }
//
//        Robot[] robots = donnees.getRobot();
//        for (int i = 0; i < robots.length; i++) {
//        	robots[i].draw(gui, tailleCase);;
//        }
//    }
//}

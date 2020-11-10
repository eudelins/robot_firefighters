import java.awt.Color;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
import gui.ImageElement;
import gui.Simulable;
import gui.Text;

import robot.*;
import strategie.ChefPompier;
import carte.*;
import evenement.*;
import donnees.*;

public class TestSimulateur {

	public static void main(String[] args) {
		   // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

       // crée la carte, en l'associant à la fenêtre graphique précédente
		DonneesSimulation newDonnes = new DonneesSimulation(new File("cartes/carteSujet.map"));

		Simulateur simul = new Simulateur();
		ChefPompier chef = new ChefPompier(simul, newDonnes);
		
		
		/*
		Robot roue = newDonnes.getRobot()[1];
		Robot drone = newDonnes.getRobot()[0];
		Robot chenille = newDonnes.getRobot()[2];


		Gps cheminDrone = new Gps(drone, drone.getPosition(), newDonnes.getIncendie()[1].getPosition());
		Gps cheminChenille = new Gps(chenille, chenille.getPosition(), newDonnes.getIncendie()[2].getPosition());
		Gps cheminRoue = new Gps(roue, roue.getPosition(), newDonnes.getIncendie()[0].getPosition());

		cheminDrone.trouverChemin(simul, newDonnes);
		cheminDrone.creationEvenementChemin(simul, newDonnes);

		cheminRoue.trouverChemin(simul, newDonnes);
		cheminRoue.creationEvenementChemin(simul, newDonnes);

		cheminChenille.trouverChemin(simul, newDonnes);
		cheminChenille.creationEvenementChemin(simul, newDonnes);
		*/
		SimulateurGui carte = new SimulateurGui(gui, newDonnes, simul, chef);
	}



}


class SimulateurGui implements Simulable {
	/** L'interface graphique associée */
    private GUISimulator gui;
    private Simulateur simul;
    private DonneesSimulation donnees;
    private ChefPompier chef;

    /**
     * Crée une carte et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur des cases de la carte
     */
    public SimulateurGui(GUISimulator gui, DonneesSimulation donnees, Simulateur simul, ChefPompier chef) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donnees = donnees;
        this.simul = simul;
        this.chef = chef;
        draw();
    }

    @Override
    public void next() {
    	chef.donneOrdre();
    	if (simul.simulationTerminee()) return;
    	Evenement premierEvenement = simul.getPremierEvent();
    	while (premierEvenement != null && simul.getDateSimulation() >= premierEvenement.getDate()) {
    		System.out.println(premierEvenement.getDate());
    		premierEvenement.execute();
    		premierEvenement = premierEvenement.getSuivant();
    	}
    	simul.setPremierEvent(premierEvenement);
    	for (int i = 0; i < 100; i++) simul.incrementeDate();
        draw();
    }

    @Override
    public void restart() {
        draw();
    }

    public static Color colorCase(Case uneCase) {
    	if (uneCase.getIncendie() != null) return Color.RED;
    	switch (uneCase.getNature()) {
    	case EAU:
    		return Color.BLUE;
    	case FORET:
    		return Color.decode("#0f7400");
    	case ROCHE:
    		return Color.GRAY;
    	case TERRAIN_LIBRE:
    		return Color.GREEN;
    	case HABITAT:
    		return Color.decode("#92390b");
    	default:
    		return Color.BLACK;
    	}
    }

    
    public static ImageElement imageCase(Case uneCase, int tailleCase) {
    	int coordX = uneCase.getColonne() * tailleCase;
		int coordY = uneCase.getLigne() * tailleCase;
    	switch (uneCase.getNature()) {
    	case EAU:
    		return new ImageElement(coordX, coordY, "images/eau.png", tailleCase, tailleCase, null);
    	case FORET:
    		return new ImageElement(coordX, coordY, "images/foret.png", tailleCase, tailleCase, null);
    	case ROCHE:
    		return new ImageElement(coordX, coordY, "images/rock.png", tailleCase, tailleCase, null);
    	case TERRAIN_LIBRE:
    		return new ImageElement(coordX, coordY, "images/terrainlibre.png", tailleCase, tailleCase, null);
    	case HABITAT:
    		return new ImageElement(coordX, coordY, "images/habitat.png", tailleCase, tailleCase, null);
    	default:
    		return null;
    	}
    }

    /**
     * Dessine la carte.
     */
    private void draw() {
        gui.reset();	// clear the window
        Carte carte = donnees.getCarte();
        int tailleCase = carte.getTailleCases() / 100;

        for (int i = 0; i < carte.getNbLignes(); i++) {
        	for (int j = 0; j < carte.getNbColonnes(); j++) {
        		Case case_ij = carte.getCase(i, j);
        		Color couleurCase = colorCase(case_ij);
//        		int coordX = j * tailleCase + (tailleCase>>1);
//        		int coordY = i * tailleCase + (tailleCase>>1);
        		gui.addGraphicalElement(imageCase(case_ij, tailleCase));
//        		gui.addGraphicalElement(new Rectangle(coordX, coordY, Color.BLACK, couleurCase, tailleCase));
        	}
        }

        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	robots[i].draw(gui, tailleCase);;
        }
    }
}

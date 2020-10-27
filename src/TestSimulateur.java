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
import carte.*;
import evenement.*;

public class TestSimulateur {

	public static void main(String[] args) {
		   // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
    
       // crée la carte, en l'associant à la fenêtre graphique précédente
		DonneesSimulation newDonnes = new DonneesSimulation(new File("cartes/carteSujet.map"));
		
		Simulateur simul = new Simulateur();
		Robot drone = newDonnes.getRobot()[0];
		for (int i = 0; i < 4; i++) {
			DeplacementDebut move = new DeplacementDebut(i, drone, Direction.NORD, newDonnes.getCarte());
			simul.ajouteEvenement(move);
		}
       
		SimulateurGui carte = new SimulateurGui(gui, newDonnes, simul);
	}
	
	

}


class SimulateurGui implements Simulable {
	/** L'interface graphique associée */
    private GUISimulator gui;
    private Simulateur simul;
    private DonneesSimulation donnees;

    /**
     * Crée une carte et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur des cases de la carte
     */
    public SimulateurGui(GUISimulator gui, DonneesSimulation donnees, Simulateur simul) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donnees = donnees;
        this.simul = simul;
        draw();
    }

    @Override
    public void next() {
    	if (simul.simulationTerminee()) return;
    	Evenement premierEvenement = simul.getPremierEvent();
    	while (premierEvenement != null && simul.getDateSimulation() == premierEvenement.getDate()) {
    		premierEvenement.execute();
    		premierEvenement = premierEvenement.getSuivant();
    	}
    	simul.setPremierEvent(premierEvenement);
    	simul.incrementeDate();
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
        		int coordX = i * tailleCase + tailleCase/2;
        		int coordY = j * tailleCase + tailleCase/2;
        		gui.addGraphicalElement(new Rectangle(coordX, coordY, Color.BLACK, couleurCase, tailleCase));
        	}
        }
        
        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	Case caseRobot = robots[i].getPosition();
        	int coordX = caseRobot.getLigne() * tailleCase + tailleCase/2;
        	int coordY = caseRobot.getColonne() * tailleCase + tailleCase/2;
        	int dimImage = 2 * tailleCase / 3;
        	String fileName = "images/drone.jpg";
        	gui.addGraphicalElement(new Oval(coordX, coordY, Color.BLACK, Color.DARK_GRAY, tailleCase/2));
        }
    }
}
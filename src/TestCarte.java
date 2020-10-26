import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
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

public class TestCarte {

	public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
         GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
     
        // crée la carte, en l'associant à la fenêtre graphique précédente
		DonneesSimulation newDonnes = new DonneesSimulation(new File("cartes/carteSujet.map"));
        
		CarteGui carte = new CarteGui(gui, newDonnes);
    }
}


class CarteGui implements Simulable {
	/** L'interface graphique associée */
    private GUISimulator gui;
    
    private DonneesSimulation donnees;

    /**
     * Crée une carte et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur des cases de la carte
     */
    public CarteGui(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donnees = donnees;

        draw();
    }

    @Override
    public void next() {		
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
        		String fileName = "images/foret.jpg";
        		Color couleurCase = colorCase(case_ij);
        		int coordX = i * tailleCase + tailleCase/2;
        		int coordY = j * tailleCase + tailleCase/2;
        		gui.addGraphicalElement(new Rectangle(coordX, coordY, Color.BLACK, couleurCase, tailleCase));
//        		ImageElement image = new ImageElement(coordX, coordY, fileName, tailleCase, tailleCase, null);
//        		gui.addGraphicalElement(image);
        	}
        }
        
        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	Case caseRobot = robots[i].getPosition();
        	int coordX = caseRobot.getLigne() * tailleCase + tailleCase/6;
        	int coordY = caseRobot.getColonne() * tailleCase + tailleCase/6;
        	int dimImage = 2 * tailleCase / 3;
        	String fileName = "images/drone.jpg";
        	gui.addGraphicalElement(new ImageElement(coordX, coordY, fileName, dimImage, dimImage, null));
        }
    }
    
}
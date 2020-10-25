import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.File;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Oval;
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
        int taille_case = carte.getTailleCases() / 100;
        
        for (int i = 0; i < carte.getNbLignes(); i++) {
        	for (int j = 0; j < carte.getNbColonnes(); j++) {
        		Case case_ij = carte.getCase(i, j);
        		Color couleurCase = colorCase(case_ij);
        		int coordX = i * taille_case + taille_case/2;
        		int coordY = j * taille_case + taille_case/2;
        		gui.addGraphicalElement(new Rectangle(coordX, coordY, Color.BLACK, couleurCase, taille_case));
        		
        	}
        }
        
        Robot[] robots = donnees.getRobot();
        for (int i = 0; i < robots.length; i++) {
        	Case caseRobot = robots[i].getPosition();
        	int coordX = caseRobot.getLigne() * taille_case + taille_case/2;
        	int coordY = caseRobot.getColonne() * taille_case + taille_case/2;
        	gui.addGraphicalElement(new Oval(coordX, coordY, Color.BLACK, Color.DARK_GRAY, taille_case/2));
        }
    }
    
}
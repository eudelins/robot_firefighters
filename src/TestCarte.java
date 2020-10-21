import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;


public class TestCarte {

	public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
     
        // crée la carte, en l'associant à la fenêtre graphique précédente
        CarteGui carte = new CarteGui(gui, new Carte(50, 50, 20), Color.decode("#f2ff28"));
    }
}


class CarteGui implements Simulable {
	/** L'interface graphique associée */
    private GUISimulator gui;
    
    private Carte carte;

    /** La couleur de dessin des cases de la carte */
    private Color carteColor;	

    /**
     * Crée une carte et la dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur des cases de la carte
     */
    public CarteGui(GUISimulator gui, Carte carte, Color carteColor) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.carte = carte;
        this.carteColor = carteColor;

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
    	switch (uneCase.getNature()) {
    	case EAU:
    		return Color.BLUE;
    	case FORET:
    		return Color.GREEN;
    	case ROCHE:
    		return Color.GRAY;
    	case TERRAIN_LIBRE:
    		return Color.GREEN;
    	case HABITAT:
    		return Color.WHITE;
    	default:
    		return Color.BLACK;
    	}
    }
    
    /**
     * Dessine la carte.
     */
    private void draw() {
        gui.reset();	// clear the window
        int taille_case = carte.getTailleCases();
        
        for (int i = 0; i < carte.getNbLignes(); i++) {
        	for (int j = 0; j < carte.getNbColonnes(); j++) {
        		Case case_ij = carte.getCase(i, j);
        		Color couleurCase = colorCase(case_ij);
        		int coordX = i * taille_case + taille_case/2;
        		int coordY = j * taille_case + taille_case/2;
        		gui.addGraphicalElement(new Rectangle(coordX, coordY, Color.BLACK, couleurCase, taille_case));
        		
        	}
        }
    }
    
}
package test;

import java.awt.Color;

import gui.GUISimulator;

import evenement.*;


/**
 * Classe testant les simulations
 * @author equipe 66
 */
public class TestSimulateur {

	/**
	 * Exécute la simulation appliquée au fichier args[0]
	 * @param args args[0] correspond au chemin vers le fichier de la carte
	 */
	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		Simulateur simul = new Simulateur(gui, args[0]);
	}
}

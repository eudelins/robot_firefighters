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

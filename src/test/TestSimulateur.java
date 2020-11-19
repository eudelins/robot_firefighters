package test;


import java.awt.Color;

import gui.GUISimulator;

import evenement.*;

public class TestSimulateur {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		Simulateur simul = new Simulateur(gui, args[0]);
	}
}

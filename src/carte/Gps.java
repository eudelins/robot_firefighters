package carte;

import java.util.ArrayList;

import robot.Robot;

public class Gps {
	private Robot robot;
	private ArrayList<Case> ferme = new ArrayList<Case>();
	private ArrayList<Case> ouvert = new ArrayList<Case>();
	private Case debut;
	private Case fin;
	
	public Gps(Robot robot, Case debut, Case fin) {
		if(!(robot.getTerrainInterdit().contains(fin.getNature()))) {
			this.robot = robot;
			this.debut = debut;
			this.fin = fin;
		}
	}
	
	public void trouverChemin() {
		
	}
}

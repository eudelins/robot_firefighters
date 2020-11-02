package carte;

import java.util.ArrayList;
import java.util.Math;

import robot.Robot;
import carte.Carte;

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

	// On pourrait renvoyer un ArrayList qui contient le chemin trouvé ou null si pas de chemin
	public void trouverChemin() {
		// Créer une variable g qui augmente à chaque fois qu'on rajoute une étape au chemin
		while(!ouvert.isEmpty()){
			Case current = this.trouver_f_mini(ouvert);
			ArrayList<Case> voisins = this.robot.carte.getVoisins(current);
			
			for(Case caseVoisine : )

		}
	}

	private int calcul_manhattan(Case case1, Case case2){
		return Math.abs(case1.getColonne() - case2.getColonne()) + Math.abs(case1.getLigne() - case2.getLigne());
	}

	private Case trouver_f_mini(ArrayListe<Case> ouverts){
		Case case_min = ouverts[0];
		int valeur_min = case_min.getf();
		for(Case case: ouverts){
			// Au lieu de faire un get, on peut calculer f, en rajoutant g en paramètre
			// Genre f = g + calcul_manhattan(fin, case)
			// Pour l'initialisation de valeur min, on peut mettre le plus grand int existant
			if(case.getf() < valeur_min){
				case_min = case;
				valeur_min = case_min.getf();
			}
		}
		return case_min;
	}
}

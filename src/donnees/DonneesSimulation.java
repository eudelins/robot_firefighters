package donnees;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import robot.*;
import carte.*;
import evenement.*;


public class DonneesSimulation {
	private Incendie[] incendies;
	private Carte carte;
	private Robot[] robots;
	private Simulateur simul;

	public Carte getCarte() {
		return carte;
	}

	public Robot[] getRobot() {
		return robots;
	}

	public Incendie[] getIncendie(){
		return incendies;
	}

	/* Initialise les données de la simulation à partir du fichier file */
	public DonneesSimulation(File file) {
		this.simul = new Simulateur();
		this.setDonnees(file);
	}
	
	public void setDonnees(File file) {
		Scanner scan;
		try {
			scan = new Scanner(file);
			String newLine = scan.nextLine();  // premiere ligne de commentaire

			int nbLignes, nbColonnes, tailleCase;  // On récupère les paramètres de la carte
			nbLignes = scan.nextInt();
			nbColonnes = scan.nextInt();
			tailleCase = scan.nextInt();

			// On saute les lignes vides
			newLine = scan.nextLine();
			while (scan.hasNext() && "".equals(newLine)) newLine = scan.nextLine();

			// On récupère les cases de la carte
			Case[][] matriceCase = new Case[nbLignes][nbColonnes];
			recupCarte(scan, matriceCase, tailleCase);

			newLine = scan.nextLine();
			while (scan.hasNext() && "".equals(newLine)) newLine = scan.nextLine();
			
			// On récupère les incendies
			recupIncendies(scan, matriceCase);

			// saute les lignes vides
			newLine = scan.nextLine();
			while (scan.hasNext() && ("".equals(newLine) || "\t".equals(newLine))) newLine = scan.nextLine();

			// On récupère les robots
			recupRobots(scan, matriceCase);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* Récupère les données de la carte dans un fichier pour initialiser l'attribut carte */
	public void recupCarte(Scanner scan, Case[][] matriceCase, int tailleCase) {
		String newLine;
		for(int i = 0; i < matriceCase.length; i++) {
			for (int j = 0; j < matriceCase[0].length; j++) {
				newLine = scan.nextLine();
				matriceCase[i][j] = new Case(NatureTerrain.stringToNature(newLine), i, j, null);
			}
			newLine = scan.nextLine();  // On se débarasse du commentaire
		}
		this.carte = new Carte(matriceCase, tailleCase);
	}


	/* Récupère les données des incendies pour initialiser l'attribut incendies */
	public void recupIncendies(Scanner scan, Case[][] matriceCase) {
		int nbIncendies = scan.nextInt();
		this.incendies = new Incendie[nbIncendies];
		for (int i = 0; i < nbIncendies; i++) {
			int lig = scan.nextInt();
			int col = scan.nextInt();
			int litres = scan.nextInt();
			this.incendies[i] = new Incendie(matriceCase[lig][col], litres);
			matriceCase[lig][col].setIncendie(this.incendies[i]);
		}
	}


	/* Récupère les données des robots pour initialiser l'attribut robots */
	public void recupRobots(Scanner scan, Case[][] matriceCase) {
		int nbRobots = scan.nextInt();
		this.robots = new Robot[nbRobots];
		for (int i = 0; i < nbRobots; i++) {
			int lig = scan.nextInt();
			int col = scan.nextInt();
			String[] finLigne = scan.nextLine().split(" ");
			int vitesse;
			if (finLigne.length < 3) {
				vitesse = -1;
			} else {
				vitesse = Integer.parseInt(finLigne[2]);
			}
			switch (finLigne[1]) {
			case "DRONE":
				if (vitesse == -1) this.robots[i] = new Drone(carte, matriceCase[lig][col], simul, 100);
				else this.robots[i] = new Drone(carte, matriceCase[lig][col], simul, vitesse);
				break;
			case "ROUES":
				if (vitesse == -1) this.robots[i] = new RobotARoue(carte, matriceCase[lig][col], simul, 80);
				else this.robots[i] = new RobotARoue(carte, matriceCase[lig][col], simul, vitesse);
				break;
			case "PATTES":
				// A modifier
				if (vitesse == -1) this.robots[i] = new RobotAPattes(carte, matriceCase[lig][col], simul, 30);
				else this.robots[i] = new RobotAPattes(carte, matriceCase[lig][col], simul, vitesse);
				break;
			case "CHENILLES":
				// A modifier
				if (vitesse == -1) this.robots[i] = new RobotAChenille(carte, matriceCase[lig][col], simul, 60);
				else this.robots[i] = new RobotAChenille(carte, matriceCase[lig][col], simul, vitesse);
				break;
			}
		}
	}
}

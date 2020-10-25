import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import robot.*;
import carte.*;


public class DonneesSimulation {
	private Incendie[] incendies;
	private Carte carte;
	private Robot[] robots;
	
	public Carte getCarte() {
		return carte;
	}
	
	public Robot[] getRobot() {
		return robots;
	}
	
	/* Initialise les données de la simulation à partir du fichier file */
	public DonneesSimulation(File file) {
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
			sauteLigneVide(scan, newLine);
			
			// On récupère les cases de la carte
			Case[][] matriceCase = new Case[nbLignes][nbColonnes];
			recupCarte(scan, matriceCase, tailleCase);
			
			sauteLigneVide(scan, newLine);
			
			// On récupère les incendies
			recupIncendies(scan, matriceCase);
			
			sauteLigneVide(scan, newLine);
			newLine = scan.nextLine();  // On se débarasse du commentaire
			
			// On récupère les robots
			recupRobots(scan, matriceCase);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	/* Fonction qui saute les lignes vides du fichier */
	public static void sauteLigneVide(Scanner scan, String newLine) {
		while (scan.hasNext() && "".equals(newLine)) {
			newLine = scan.nextLine();
		}
	}
	
	
	/* Récupère les données de la carte dans un fichier pour initialiser l'attribut carte */
	public void recupCarte(Scanner scan, Case[][] matriceCase, int tailleCase) {
		String newLine;
		for(int i = 0; i < matriceCase.length; i++) {
			for (int j = 0; j < matriceCase[0].length; j++) {
				newLine = scan.nextLine();
				System.out.println(newLine);
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
			System.out.println(i + ", " + nbIncendies + ", " + lig + ", " + col + ", " + litres);
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
			System.out.println(finLigne[1]);
			int vitesse;
			if (finLigne.length < 3) {
				vitesse = 0;
			} else {
				vitesse = Integer.parseInt(finLigne[2]);
			}
			System.out.println(vitesse);
			switch (finLigne[1]) {
			case "DRONE":
				this.robots[i] = new Drone(matriceCase[lig][col], 0, vitesse);
				break;
			case "ROUES":
				this.robots[i] = new RobotARoue(matriceCase[lig][col], 0, vitesse);
				break;
			case "PATTES":
				// A modifier
				this.robots[i] = new RobotAPattes(matriceCase[lig][col], 0, vitesse);
				break;
			case "CHENILLES":
				// A modifier
				this.robots[i] = new RobotAChenille(matriceCase[lig][col], 0, vitesse);
				break;
			}
		}
	}
}

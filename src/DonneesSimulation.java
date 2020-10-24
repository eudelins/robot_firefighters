import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DonneesSimulation {
	private Incendie[] incendies;
	private Carte carte;
	private Robot[] robots;
	
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
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
			// On récupère les cases de la carte
			Case[][] matriceCase = new Case[nbLignes][nbColonnes];
			for(int i = 0; i < nbLignes; i++) {
				for (int j = 0; j < nbColonnes; j++) {
					newLine = scan.nextLine();
					System.out.println(newLine);
					matriceCase[i][j] = new Case(NatureTerrain.stringToNature(newLine), i, j, null);
				}
				newLine = scan.nextLine();  // On se débarasse du commentaire
			}
			this.carte = new Carte(matriceCase, tailleCase);
			
			// On saute les lignes vides
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
			// On récupère les incendies
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
			
			// On saute les lignes vides
			newLine = scan.nextLine();
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
			newLine = scan.nextLine();  // On se débarasse du commentaire
			
			// On récupère les robots
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
					this.robots[i] = new RobotARoue(matriceCase[lig][col], 0, vitesse);
					break;
				default:
					// A modifier
					this.robots[i] = new RobotARoue(matriceCase[lig][col], 0, vitesse);
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

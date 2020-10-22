import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DonneesSimulation {
	private Incendie[] incendies;
	private Carte carte;
	private Robot[] robots;
	
	public DonneesSimulation(File file) {
		Scanner scan;
		try {
			scan = new Scanner(file);
			String newLine = scan.nextLine();  // premier ligne de commentaire
			
			int nbLignes, nbColonnes, tailleCase;
			nbLignes = scan.nextInt();
			nbColonnes = scan.nextInt();
			tailleCase = scan.nextInt();
			
			newLine = scan.nextLine();
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
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
			
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
			newLine = scan.nextLine();  // On se débarasse du commentaire
			int nbIncendies = scan.nextInt();
			this.incendies = new Incendie[nbIncendies];
			
			for (int i = 0; i < nbIncendies; i++) {
				int lig = scan.nextInt();
				int col = scan.nextInt();
				int litres = scan.nextInt();
				this.incendies[i] = new Incendie(matriceCase[lig][col], litres);
				matriceCase[lig][col].setIncendie(this.incendies[i]);
			}
			
			newLine = scan.nextLine();
			while (scan.hasNext() && "".equals(newLine)) {
				newLine = scan.nextLine();
			}
			
//			newLine = scan.nextLine();  // On se débarasse du commentaire
//			int nbRobots = scan.nextInt();
//			this.robots = new Robot[nbRobots];
//			
//			for (int i = 0; i < nbRobots; i++) {
//				int lig = scan.nextInt();
//				int col = scan.nextInt();
//				String[] finLigne = scan.nextLine().split(" ");
//				switch (finLigne[0]) {
//				case "DRONE":
//					this.robots[i] = new Drone();
//				case "ROUES":
//				}
//			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

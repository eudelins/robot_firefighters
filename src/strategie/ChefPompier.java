package strategie;

import robot.*;
import carte.*;
import evenement.*;
import donnees.*;

/**
 * Classe du chef pompier qui gère la stratégie
 */
public class ChefPompier {
	private Simulateur simul;
	
	/**
	 * Crée le chef pompier
	 * @param simul le simulateur qui gère les données
	 */
	public ChefPompier(Simulateur simul) {
		this.simul = simul;
	}
	
	/** 
	 * Mène la stratégie naïve
	 */
	public void donneOrdreNaif() {
		// On récupère les données
		DonneesSimulation donnees = this.simul.getDonnees();
		Incendie[] incendies = donnees.getIncendie();
		Robot[] robots = donnees.getRobot();
		
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			// On cherche un feu non affectée
			if (incendies[i] == null || incendies[i].isAffecte()) continue;
			fire = incendies[i];
			
			Robot rob = null;
			for (int j = 0; j < robots.length; j++) {
				// On cherche un robot libre
				if (robots[j].isOccupe()) continue;
				rob = robots[j];
				rob.setOccupe(true);
				
				// On remplit son réservoir s'il n'est pas plein ou pas capable d'éteindre le feu avec
				if (rob.getQuantiteEau() < fire.getNbLitres() && rob.getQuantiteEau() < rob.capaciteReservoire()) {
					Case pointEau = rob.accesEauPlusProche();
					Gps cheminEau = new Gps(rob, rob.getPosition(), pointEau);
					cheminEau.trouverChemin(simul, donnees);
					long dateArriveeEau = cheminEau.creationEvenementChemin(simul, donnees);
					DebutRemplissage remplissage = new DebutRemplissage(dateArriveeEau, simul, rob);
					simul.ajouteEvenement(remplissage);
				
				// Sinon on envoie le robot éteindre le feu
				} else {
					Gps chemin = new Gps(rob, rob.getPosition(), fire.getPosition());
					if (chemin.trouverChemin(simul, donnees)) {
						fire.setAffecte(true);
						long dateArrivee = chemin.creationEvenementChemin(simul, donnees);
						DeverserDebut deversage = new DeverserDebut(dateArrivee, simul, rob, fire.getPosition());
						simul.ajouteEvenement(deversage);
						break;
					}
				}
			}
			
			if (rob == null) return;
		}
	}
	
	
	/**
	 * Mène la stratégie de manière plus avancée
	 */
	public void donneOrdre() {
		// On récupère les données
		DonneesSimulation donnees = this.simul.getDonnees();
		Incendie[] incendies = donnees.getIncendie();
		Robot[] robots = donnees.getRobot();
		
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			// On cherche un feu non affectée
			if (incendies[i] == null || incendies[i].isAffecte()) continue;
			fire = incendies[i];
			
			Robot robMin = null;
			Gps cheminMin = null;
			long tempsMin = 0x7fffffffffffffffL;  // infini
			// On cherche le robot le plus près du feu
			for (int j = 0; j < robots.length; j++) {
				if (robots[j].isOccupe()) continue;
				
				// On remplit le réservoir du robot s'il n'est pas plein ou pas capable d'éteindre le feu avec
				if (robots[j].getQuantiteEau() < fire.getNbLitres() 
						&& robots[j].getQuantiteEau() < robots[j].capaciteReservoire()) {
					robots[j].setOccupe(true);
					Case pointEau = robots[j].accesEauPlusProche();
					Gps cheminEau = new Gps(robots[j], robots[j].getPosition(), pointEau);
					cheminEau.trouverChemin(simul, donnees);
					long dateArriveeEau = cheminEau.creationEvenementChemin(simul, donnees);
					DebutRemplissage remplissage = new DebutRemplissage(dateArriveeEau, simul, robots[j]);
					simul.ajouteEvenement(remplissage);

				// Sinon on calcule le temps qu'il met pour aller jusqu'à l'incendie
				} else {
					Gps chemin = new Gps(robots[j], robots[j].getPosition(), fire.getPosition());
					if (chemin.trouverChemin(simul, donnees)) {
//						System.out.println(j + ": Je suis la");
						long dateArrivee = chemin.dateArrivee(simul);
						if (dateArrivee < tempsMin) {
							robMin = robots[j];
							tempsMin = dateArrivee;
							cheminMin = chemin;
						}
					}
				}
			}
			
			// On envoie le robot le plus proche éteindre le feu
			if (cheminMin != null) {
				robMin.setOccupe(true);
				fire.setAffecte(true);
				cheminMin.creationEvenementChemin(simul, donnees);
				DeverserDebut deversage = new DeverserDebut(tempsMin, simul, robMin, fire.getPosition());
				simul.ajouteEvenement(deversage);
			} else {
				for (int j = 0; j < 100; j++) simul.incrementeDate();
				return;
			}
		}
		if (fire == null ) for (int j = 0; j < 100; j++) simul.incrementeDate();
	}

}

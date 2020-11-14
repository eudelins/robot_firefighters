package strategie;

import robot.*;
import carte.*;
import evenement.*;
import donnees.*;

public class ChefPompier {
	private Simulateur simul;
	private DonneesSimulation donnees;
	
	public ChefPompier(Simulateur simul, DonneesSimulation donnees) {
		this.simul = simul;
		this.donnees = donnees;
	}
	
	public void setDonnees(DonneesSimulation donnees) {
		this.donnees = donnees;
	}
	
	/** Mène la stratégie naïve */
	public void donneOrdreNaif() {
		Incendie[] incendies = donnees.getIncendie();
		Robot[] robots = donnees.getRobot();
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			if (incendies[i] == null || incendies[i].isAffecte()) continue;
			fire = incendies[i];
			
			Robot rob = null;
			for (int j = 0; j < robots.length; j++) {
				if (robots[j].isOccupe()) continue;
				rob = robots[j];
				rob.setOccupe(true);
				
				if (rob.getQuantiteEau() < fire.getNbLitres() && rob.getQuantiteEau() < rob.capaciteReservoire()) {
					Case pointEau = rob.accesEauPlusProche();
					Gps cheminEau = new Gps(rob, rob.getPosition(), pointEau);
					cheminEau.trouverChemin(simul, donnees);
					long dateArriveeEau = cheminEau.creationEvenementChemin(simul, donnees);
					DebutRemplissage remplissage = new DebutRemplissage(dateArriveeEau, simul, rob);
					simul.ajouteEvenement(remplissage);
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
	
	
	/** Mène la stratégie naïve */
	public void donneOrdre() {
		Incendie[] incendies = donnees.getIncendie();
		Robot[] robots = donnees.getRobot();
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			if (incendies[i] == null || incendies[i].isAffecte()) continue;
			fire = incendies[i];
			
			Robot robMin = null;
			Gps cheminMin = null;
			long tempsMin = 0x7fffffffffffffffL;  // infini
			for (int j = 0; j < robots.length; j++) {
				if (robots[j].isOccupe()) continue;
				
				if (robots[j].getQuantiteEau() < fire.getNbLitres() 
						&& robots[j].getQuantiteEau() < robots[j].capaciteReservoire()) {
					robots[j].setOccupe(true);
					Case pointEau = robots[j].accesEauPlusProche();
					Gps cheminEau = new Gps(robots[j], robots[j].getPosition(), pointEau);
					cheminEau.trouverChemin(simul, donnees);
					long dateArriveeEau = cheminEau.creationEvenementChemin(simul, donnees);
					DebutRemplissage remplissage = new DebutRemplissage(dateArriveeEau, simul, robots[j]);
					simul.ajouteEvenement(remplissage);
				} else {
					Gps chemin = new Gps(robots[j], robots[j].getPosition(), fire.getPosition());
					if (chemin.trouverChemin(simul, donnees)) {
						long dateArrivee = chemin.dateArrivee(simul);
						if (dateArrivee < tempsMin) {
							robMin = robots[j];
							tempsMin = dateArrivee;
							cheminMin = chemin;
						}
					}
				}
			}
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

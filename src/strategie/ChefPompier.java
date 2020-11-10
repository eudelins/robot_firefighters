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
	
	/** Mène la stratégie */
	public void donneOrdre() {
		Incendie[] incendies = donnees.getIncendie();
		Robot[] robots = donnees.getRobot();
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			if (incendies[i].isAffecte()) continue;
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
		if (fire == null) return;
	}

}

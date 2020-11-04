package strategie;

import robot.*;
import carte.*;

public class chefPompier {
	private Robot[] robots;
	private Incendie[] incendies;
	
	public chefPompier(Robot[] robots) {
		this.robots = robots;
	}
	
	/** Mène la stratégie */
	void donneOrdre() {
		Incendie fire = null;
		for (int i = 0; i < incendies.length; i++) {
			if (incendies[i].isAffecte()) continue;
			fire = incendies[i];
			
			Robot rob = null;
			for (int j = 0; j < robots.length; j++) {
				if (robots[j].isOccupe()) continue;
//				Chemin chemin = robots[j].calculChemin(fire.getPosition());
//				if (chemin == null) continue;
				rob = robots[j];
//				simul.planifie(chemin);
//				simul.planifie(rob.deverseEau)
			}
			
			if (rob == null) return;
		}
		if (fire == null) return;
	}

}


public class Drone extends Robot {

	public Drone(Case position, int quantiteEau, int vitesse) {
		assert(quantiteEau <= 10000);
		this.setPosition(position);
		this.setQuantiteEau(quantiteEau);
		this.setVitesse(vitesse);
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		return 100;
	}

	@Override
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol); 
		// Il faut trouver un moyen d'accéder à l'objet "Incendie" lié à la case sur le quel se trouve le robot
		// A compléter
	}

	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			this.setStopped(true);
			try {
				Thread.sleep(30000); // Il me semble que ça met en pause la fonction (pas tout le programme) 
									 // pour le temps indiqué (j'ai choisi 30 min = 30 sec)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setQuantiteEau(10000);
			this.setStopped(false);
			// à compléter et vérifier
		}
	}

}

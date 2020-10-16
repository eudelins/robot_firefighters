
public class RobotARoue extends Robot{
	
	public RobotARoue(Case position, int quantiteEau) {
		assert(quantiteEau <= 5000);
		this.setPosition(position);
		this.setQuantiteEau(quantiteEau);
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		if (nature == NatureTerrain.TERRAIN_LIBRE || nature == NatureTerrain.HABITAT)
			return 80;
		else
			return 0;
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
		boolean voisinAvecEau = true; // Faire une fonction qui vérifie si une case voisin a pour nature EAU
		if (voisinAvecEau) {
			this.setStopped(true);
			try {
				Thread.sleep(10000); // Il me semble que ça met en pause la fonction (pas tout le programme) 
									 // pour le temps indiqué (j'ai choisi 30 min = 30 sec)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setQuantiteEau(5000);
			this.setStopped(false);
			// à compléter et vérifier
		}
	}
}

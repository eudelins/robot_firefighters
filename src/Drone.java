
public class Drone extends Robot {

	public Drone(Case position, int quantiteEau) {
		assert(quantiteEau < 10000);
		this.setPosition(position);
		this.setQuantiteEau(quantiteEau);
	}

	@Override
	public double getVitesse(NatureTerrain nature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol); 
		// A compléter
	}

	@Override
	public void remplirReservoir() {
		if (this.getPosition().getNature() == NatureTerrain.EAU) {
			
		}
	}

}

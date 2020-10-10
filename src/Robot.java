public abstract class Robot {
	private Case position;
	private int quantiteEau;
	
	public Robot(Case position, int quantiteEau) {
		this.position = position;
		this.quantiteEau  = quantiteEau;
	}
	
	public Robot() {
		this.position = new Case(NatureTerrain.EAU, 0, 0);
		this.quantiteEau  = 0;
	}
	
	public Case getPosition() {
		return position;
	}
	
	public void setPosition(Case newPosition) {
		this.position = newPosition;
	}
	
	public int getQuantiteEau() {
		return quantiteEau;
	}

	public void setQuantiteEau(int quantiteEau) {
		this.quantiteEau = quantiteEau;
	}

	public abstract double getVitesse(NatureTerrain nature);
	
	public abstract void deverserEau(int vol);
	
	public abstract void remplirReservoir();
}

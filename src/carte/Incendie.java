package carte;


public class Incendie {
	private Case position;
	private int nbLitres;
	
	public Incendie(Case position, int nbLitres) {
		super();
		this.position = position;
		this.nbLitres = nbLitres;
	}

	public Case getPosition() {
		return position;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public int getNbLitres() {
		return nbLitres;
	}

	public void setNbLitres(int nbLitres) {
		this.nbLitres = nbLitres;
	}
	
	public void eteindre(int litre) {
		if(this.nbLitres <= litre)
			this.nbLitres = 0;
		else
			this.nbLitres = this.nbLitres - litre;
	}
}

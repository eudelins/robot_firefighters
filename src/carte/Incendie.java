package carte;

/**
*	Classe représentant les incendies, contenant la position, le nombre de litres nécéssaires, et son occupation
*/

public class Incendie {
	private Case position;
	private int nbLitres;
	private boolean affecte;

	public Incendie(Case position, int nbLitres) {
		super();
		this.position = position;
		this.nbLitres = nbLitres;
		this.affecte = false;
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
	/**
	* Verse de l'eau sur l'incendie, diminuant le volume d'eau nécessaire pour l'éteindre
	*/
	public void eteindre(int litre) {
		if(this.nbLitres <= litre)
			this.nbLitres = 0;
		else
			this.nbLitres = this.nbLitres - litre;
	}
	/**
	*	Permet de voir si un robot s'occupe déjà de l'incendie en question 
	*/
	public boolean isAffecte() {
		return affecte;
	}

	public void setAffecte(boolean estAffecte) {
		this.affecte = estAffecte;
	}
}

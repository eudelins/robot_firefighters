package carte;

/**
*	Classe représentant les incendies, contenant la position, le nombre de litres nécéssaires, et son occupation
*/

public class Incendie {
	private Case position;
	private int nbLitres;
	private boolean affecte;
	/**
	* Création de l'incendie
	* @param position position de l'incendie
	* @param nbLitres nombres de litres nécessaires à éteindre l'incendie
	*/
	public Incendie(Case position, int nbLitres) {
		super();
		this.position = position;
		this.nbLitres = nbLitres;
		this.affecte = false;
	}
	/**
	* Renvoie la case sur laquelle est l'incendie
	*/
	public Case getPosition() {
		return position;
	}
	/**
	* Met l'incendie sur une case
	* @param position case sur laquelle on veut mettre l'incendie
	*/

	public void setPosition(Case position) {
		this.position = position;
	}

	/**
	*	Nombre de litre nécessaire pour éteindre l'incendie
	*/

	public int getNbLitres() {
		return nbLitres;
	}

	/**
	*	Change la valeur du nombre de litre nécessaire pour éteindre l'incendie
	*/

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

	/**
	* Change la valeur de l'incendie si il est déjà affecté
	*/
	public void setAffecte(boolean estAffecte) {
		this.affecte = estAffecte;
	}
}

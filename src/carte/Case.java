package carte;
/**
* Classe représentant une case avec sa nature, sa ligne, sa colonne et l'incendie présent si il y en a un
*/

public class Case {
	private NatureTerrain nature;
	private int ligne;
	private int colonne;
	private Incendie incendie;
	private boolean estBrulee;


	public Case(NatureTerrain natureTerrain, int lig, int col, Incendie incendie) {
		super();
		this.nature= natureTerrain;
		this.ligne = lig;
		this.colonne = col;
		this.incendie = incendie;
		this.estBrulee = false;
	}
	/**
	*	Permet d'obtenir la nature du terrain de la case
	*/
	public NatureTerrain getNature() {
		return nature;
	}
	/**
	*	Permet d'obtenir la ligne sur laquelle se trouve la case
	*/
	public int getLigne() {
		return ligne;
	}
	/**
	*	Permet d'obtenir la colonne sur laquelle se trouve la case
	*/
	public int getColonne() {
		return colonne;
	}
	/**
	*	Permet d'obtenir l'incendie présent sur la case. Retourne null si il n'y en a pas
	*/
	public Incendie getIncendie() {
		return incendie;
	}
	/**
	*	Met un incendie sur la case
	*/
	public void setIncendie(Incendie incendie) {
		this.incendie = incendie;
	}

	@Override
	public String toString() {
		return "Case [nature=" + nature + ", ligne=" + ligne + ", colonne=" + colonne + ", incendie=" + incendie + "]";
	}


	/**
	*	Permet d'obtenir la destination entre la case et celle voisine donnée en destination
	* @param destination case voisine dont on veut la direction
	*/
	public Direction getDirection(Case destination){
		if(destination.getLigne() - this.ligne == 1){
			return Direction.SUD;
		}
		else if(destination.getLigne() - this.ligne == -1){
			return Direction.NORD;
		}
		else if(destination.getColonne() - this.colonne == 1){
			return Direction.EST;
		}
		else if(destination.getColonne() - this.colonne == -1){
			return Direction.OUEST;
		}
		return null;
	}
	
	/**
	 * Change l'etat brulé de la case 
	 * @param brulee	booléen indiquant si la case est actuellement brulée ou non
	 */
	public void setEstBrulee(boolean brulee) {
		this.estBrulee = brulee;
	}
	
	/**
	 * Indique si la case a été brulée par un incendie
	 */
	public boolean isBrulee() {
		return this.estBrulee;
	}
}

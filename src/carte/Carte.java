package carte;

import java.util.ArrayList;

/**
 * Classe représentant la carte sur laquelle on travaille
 * Elle contient simplement la taille des cases et une matrice contenant toutes les cases
 * @author equipe 66
 */
public class Carte {
	private Case[][] cases;
	private int tailleCases;

	/**
	 * Création d'un objet de tyoe Carte vierge, avec que des terrains libres
	 * @param nbLignes nombre total de lignes dans la carte
	 * @param nbColonnes nombre total de colonnes
	 * @param tailleCases taille d'une case
	 */
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.tailleCases = tailleCases;
		this.cases = new Case[nbLignes][nbColonnes];
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				this.cases[i][j] = new Case(NatureTerrain.TERRAIN_LIBRE, i, j, null);
			}
		}
	}

	/**
	 * Creation d'un objet de type carte a partir d'une matrice de cases
	 * @param carte matrice de Cases
	 * @param tailleCases taille d'une case
	 */
	public Carte(Case[][] carte, int tailleCases) {
		this.tailleCases = tailleCases;
		this.cases = carte;
	}


	/**
	 * Renvoie la taille d'une case
	 * @return renvoie la taille d'une case
	 */
	public int getTailleCases() {
		return tailleCases;
	}
	
	/**
	 * Change la taille d'une case
	 * @param tailleCases nouvelle taille de case
	 */
	public void setTailleCases(int tailleCases) {
		this.tailleCases = tailleCases;
	}

	/**
	 * Renvoie le nombre de lignes total
	 * @return nombre total de lignes
	 */
	public int getNbLignes() {
		return cases.length;
	}

	/**
	 * Renvoie le nombre de colonnes au total
	 * @return nombre total de collones
	 */
	public int getNbColonnes() {
		return cases[0].length;
	}

	/**
	 * Renvoie la case située à la ligne lig et à la colonne col
	 * @return la case associée à la ligne lig et la colonne col
	 */
	public Case getCase(int lig, int col) {
		return cases[lig][col];
	}

	/**
	 * Renvoie un booléen si il existe une case dans la direction donnée
	 * @param src case de laquelle on veut regarder les voisins
	 * @param dir direction dans laquelle on veut regarder
	 * @return un booléen indiquant si il y un un booléen dans la direction donnée
	 */
	public boolean voisinExiste(Case src, Direction dir) {
		int ligSrc = src.getLigne();
		int colSrc = src.getColonne();

		switch(dir) {
		case OUEST:
			if (colSrc <= 0) return false;
			return (getCase(ligSrc, colSrc - 1) != null);
		case EST:
			if (colSrc >= this.getNbColonnes() - 1) return false;
			return (getCase(ligSrc, colSrc + 1) != null);
		case NORD:
			if (ligSrc <= 0) return false;
			return (getCase(ligSrc - 1, colSrc) != null);
		default:
			if (ligSrc >= this.getNbLignes()-1) return false;
			return (getCase(ligSrc + 1, colSrc) != null);
		}
	}

	/**
	 * Renvoie la case voisine dans la direction donnée
	 * @param src case de laquelle on veut regarder les voisins
	 * @param dir direction dans laquelle on regarde
	 * @return case voisine dans la direction donnée
	 */

	public Case getVoisin(Case src, Direction dir) {
		int ligSrc = src.getLigne();
		int colSrc = src.getColonne();



		switch(dir) {
		case OUEST:
			if (colSrc <= 0) return null;
			return getCase(ligSrc, colSrc - 1);
		case EST:
			if (colSrc >= this.getNbColonnes()-1) return null;
			return getCase(ligSrc, colSrc + 1);
		case NORD:
			if (ligSrc <= 0) return null;
			return getCase(ligSrc - 1, colSrc);
		default:
			if (ligSrc >= this.getNbLignes()-1) return null;
			return getCase(ligSrc + 1, colSrc);
		}
	}

	/**
	 *	Permet d'obtenir la liste des voisins dans les quatre directions d'une case
	 * @param base case dont on veut obtenir les voisins
	 * @return renvoie la liste des voisins
	 */

	public ArrayList<Case> getVoisins(Case base){
		ArrayList<Case> voisins = new ArrayList<Case>();

		if(this.voisinExiste(base, Direction.OUEST)) voisins.add(this.getVoisin(base, Direction.OUEST));
		if(this.voisinExiste(base, Direction.NORD)) voisins.add(this.getVoisin(base, Direction.NORD));
		if(this.voisinExiste(base, Direction.EST)) voisins.add(this.getVoisin(base, Direction.EST));
		if(this.voisinExiste(base, Direction.SUD)) voisins.add(this.getVoisin(base, Direction.SUD));
		return voisins;
	}
}

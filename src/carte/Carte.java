package carte;

import java.util.ArrayList;
public class Carte {
	private Case[][] cases;
	private int tailleCases;

	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.tailleCases = tailleCases;
		this.cases = new Case[nbLignes][nbColonnes];
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				this.cases[i][j] = new Case(NatureTerrain.TERRAIN_LIBRE, i, j, null);
			}
		}
	}


	public Carte(Case[][] carte, int tailleCases) {
		this.tailleCases = tailleCases;
		this.cases = carte;
	}



	public int getTailleCases() {
		return tailleCases;
	}

	public void setTailleCases(int tailleCases) {
		this.tailleCases = tailleCases;
	}

	public int getNbLignes() {
		return cases.length;
	}

	public int getNbColonnes() {
		return cases[0].length;
	}

	public Case getCase(int lig, int col) {
		return cases[lig][col];
	}

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

	public ArrayList<Case> getVoisins(Case base){
		ArrayList<Case> voisins = new ArrayList<Case>();

		if(this.voisinExiste(base, Direction.OUEST)) voisins.add(this.getVoisin(base, Direction.OUEST));
		if(this.voisinExiste(base, Direction.NORD)) voisins.add(this.getVoisin(base, Direction.NORD));
		if(this.voisinExiste(base, Direction.EST)) voisins.add(this.getVoisin(base, Direction.EST));
		if(this.voisinExiste(base, Direction.SUD)) voisins.add(this.getVoisin(base, Direction.SUD));
		return voisins;
	}
}

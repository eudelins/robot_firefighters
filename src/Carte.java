public class Carte {
	private Case[][] cases;
	private int tailleCases;

	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.tailleCases = tailleCases;
		this.cases = new Case[nbLignes][nbColonnes];
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
			if (colSrc == 0) return false;
			return (getCase(ligSrc, colSrc - 1) != null);
		case EST:
			if (colSrc == this.getNbColonnes()) return false;
			return (getCase(ligSrc, colSrc + 1) != null);
		case NORD:
			if (ligSrc == 0) return false;
			return (getCase(ligSrc - 1, colSrc) != null);
		default:
			if (ligSrc == this.getNbLignes()) return false;
			return (getCase(ligSrc + 1, colSrc) != null);
		}	
	}
	
	public Case getVoisin(Case src, Direction dir) {
		int ligSrc = src.getLigne();
		int colSrc = src.getColonne();
		
		switch(dir) {
		case OUEST:
			if (colSrc == 0) return null;
			return getCase(ligSrc, colSrc - 1);
		case EST:
			if (colSrc == this.getNbColonnes()) return null;
			return getCase(ligSrc, colSrc + 1);
		case NORD:
			if (ligSrc == 0) return null;
			return getCase(ligSrc - 1, colSrc);
		default:
			if (ligSrc == this.getNbLignes()) return null;
			return getCase(ligSrc + 1, colSrc);
		}
	}
}


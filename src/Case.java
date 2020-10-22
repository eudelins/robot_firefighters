public class Case {
	private NatureTerrain nature;
	private int ligne;
	private int colonne;
	private Incendie incendie;
	
	public Case(NatureTerrain natureTerrain, int lig, int col, Incendie incendie) {
		super();
		this.nature= natureTerrain;
		this.ligne = lig;
		this.colonne = col;
		this.incendie = incendie;
	}

	public NatureTerrain getNature() {
		return nature;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public Incendie getIncendie() {
		return incendie;
	}

	public void setIncendie(Incendie incendie) {
		this.incendie = incendie;
	}

	@Override
	public String toString() {
		return "Case [nature=" + nature + ", ligne=" + ligne + ", colonne=" + colonne + ", incendie=" + incendie + "]";
	}
}


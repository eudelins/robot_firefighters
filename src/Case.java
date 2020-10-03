public class Case {
	private NatureTerrain nature;
	private int ligne;
	private int colonne;
	
	public Case(NatureTerrain natureTerrain, int lig, int col) {
		super();
		this.nature= natureTerrain;
		this.ligne = lig;
		this.colonne = col;
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

	@Override
	public String toString() {
		return "Case [natureTerrain=" + natureTerrain + ", lig=" + lig + ", col=" + col + "]";
	}
}


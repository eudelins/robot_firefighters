package carte;


public class Case {
	private NatureTerrain nature;
	private int ligne;
	private int colonne;
	private Incendie incendie;
	// Attributs pour le plus courts chemins :
	private int f = 0;
	private int g;
	private int h;
	private Case parent;

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

	public void setf() {
		this.f = this.h + this.g;
	}

	public void setg(int g) {
		this.g = g;
	}

	public void seth(int h) {
		this.h = h;
	}

	public void setParent(Case parent) {
		this.parent = parent;
	}

	public int getf() {
		return this.f;
	}

	public int getg() {
		return this.g;
	}

	public int geth() {
		return this.h;
	}

	public Case getParent() {
		return this.parent;
	}

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
}

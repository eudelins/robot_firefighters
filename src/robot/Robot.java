package robot;
import carte.*;
import gui.GUISimulator;


public abstract class Robot {
	private Case position;
	private int quantiteEau;
	private int vitesse;
	private boolean stopped; // Booléen qui permet de savoir si le robot est arrêté 
							 //(arrếté pour par exemple remplir son réservoir)
	
	public Robot(Case position, int quantiteEau, int vitesse) {
		this.position = position;
		this.quantiteEau  = quantiteEau;
		this.vitesse = vitesse;
	}
	
	public Robot() {
		this.position = new Case(NatureTerrain.EAU, 0, 0, null);
		this.quantiteEau  = 0;
	}
	
	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	
	public Case getPosition() {
		return position;
	}
	
	public void setPosition(Case newPosition) {
		this.position = newPosition;
	}
	
	public int getQuantiteEau() {
		return quantiteEau;
	}

	public void setQuantiteEau(int quantiteEau) {
		this.quantiteEau = quantiteEau;
	}
	
	public boolean isStopped() {
		return this.stopped;
	}
	
	public void setStopped(boolean stop) {
		this.stopped = stop;
	}
	
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol);
		Incendie feuAEteindre = this.getPosition().getIncendie(); 
		if(feuAEteindre != null) {
			feuAEteindre.eteindre(vol);
		}
	}
	
	/** Indique si le robot est a un voisin qui contient de l'eau */
	public boolean estVoisinEau(Carte carte) {
		int lig = this.position.getLigne();
		int col = this.position.getColonne();
		
		// On récupère les voisins
		Case caseNord = null, caseSud = null, caseOuest = null, caseEst = null;
		if (lig > 0) caseNord = carte.getCase(lig - 1, col);
		if (caseNord != null && caseNord.getNature() == NatureTerrain.EAU) return true;
		
		if (lig < carte.getNbLignes() - 1) caseSud = carte.getCase(lig + 1, col);
		if (caseSud != null && caseSud.getNature() == NatureTerrain.EAU) return true;
		
		if (col > 0) caseOuest = carte.getCase(lig, col - 1);
		if (caseOuest != null && caseOuest.getNature() == NatureTerrain.EAU) return true;
		
		if (col < carte.getNbColonnes() - 1) caseEst = carte.getCase(lig, col + 1);
		if (caseEst != null && caseEst.getNature() == NatureTerrain.EAU) return true;
		
		return false;
	}
	
	public abstract void remplirReservoir();
	
	public abstract void draw(GUISimulator gui, int tailleCase);
}

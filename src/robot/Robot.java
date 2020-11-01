package robot;
import java.awt.Color;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.Incendie;
import carte.NatureTerrain;
import evenement.Evenement;
import evenement.Simulateur;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Text;


public abstract class Robot {
	private Case position;
	private int quantiteEau;
	private int vitesse;
	private Carte carte;
	private Simulateur simul;
	private boolean remplissage;
	private boolean deversage;
	private boolean stopped;
	
	public Robot(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		this.carte = carte;
		this.position = position;
		this.simul = simul;
		this.quantiteEau  = quantiteEau;
		this.vitesse = vitesse;
		this.remplissage = false;
		this.deversage = false;
		this.stopped = false;
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
	
	/** Change la position du robot et adapte sa vitesse au passage */
	public void setPosition(Case newPosition) {
		this.position = newPosition;
	}
	
	public int getQuantiteEau() {
		return quantiteEau;
	}
	public Carte getCarte() {
		return carte;
	}

	public Simulateur getSimul() {
		return simul;
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
	
	public boolean isRemplissage() {
		return remplissage;
	}
	
	public void setRemplissage(boolean remplissage) {
		this.remplissage = remplissage;
	}
	
	public boolean isDeversage() {
		return deversage;
	}
	
	public void setDeversage(boolean deversage) {
		this.deversage = deversage;
	}
	
	/** Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau */
	public abstract int dureeDeversage(int quantiteNecessaire);
	
	
	/** Renvoie la durée mis par le robot pour remplir son réservoir */
	public abstract int dureeRemplissage();
	
	
	/** Deverse une quantité d'eau sur une case */
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol);
		Incendie feuAEteindre = this.getPosition().getIncendie(); 
		if(feuAEteindre != null) {
			feuAEteindre.eteindre(vol);
			if (feuAEteindre.getNbLitres() == 0) this.getPosition().setIncendie(null);
		}
	}
	
	/** Indique si le robot est a un voisin qui contient de l'eau */
	public boolean estVoisinEau() {
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
	
	public void drawReservoir(GUISimulator gui, int heightRobot, int tailleCase, int qteEauMax) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
		int barreHeight = 8;
		int barreWidth = tailleCase/3;
		int barreX = caseX + tailleCase/2;
		int barreY = caseY + tailleCase/2 + heightRobot/2 + 4 + barreHeight/2;
		Color couleurFond = Color.decode("#a5b4b8");
		if(this.quantiteEau == qteEauMax) {
			couleurFond = Color.decode("#08cdfe");
		}
		gui.addGraphicalElement(new Rectangle(barreX, barreY, Color.BLACK, couleurFond, barreWidth, barreHeight));
		if(this.quantiteEau > 0 && this.quantiteEau < qteEauMax) {
			int barreVarieWidth = barreWidth*this.quantiteEau/qteEauMax;
			int barreVarieX = barreX - (barreWidth - barreVarieWidth);
			
			gui.addGraphicalElement(new Rectangle(barreVarieX, barreY, null, Color.decode("#08cdfe"), barreVarieWidth, barreHeight));
			gui.addGraphicalElement(new Rectangle(barreX, barreY, Color.BLACK, null, barreWidth, barreHeight));
		}
		
		if(this.deversage || this.remplissage) {
			int textX = caseX + tailleCase/2;
			int textY = caseY + (tailleCase - heightRobot)/2 - 10;
			String etat;
			if(this.deversage) etat = "Deversement...";
			else etat = "Remplissage...";
			gui.addGraphicalElement(new Text(textX, textY, Color.BLACK, etat));
		}
	}

	/** Renvoie le temps mis pour accéder à une case voisine */
	public int tempsAccesVoisin(Direction dir) {
		int distance = this.carte.getTailleCases();
		return distance/this.vitesse;
	}
	
	public abstract void remplirReservoir();
	
	public abstract void draw(GUISimulator gui, int tailleCase);

}

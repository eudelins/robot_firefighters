package robot;
import java.awt.Color;
import java.util.ArrayList;

import carte.Carte;
import carte.Case;
import carte.Direction;
import carte.Incendie;
import carte.NatureTerrain;
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
	protected ArrayList<NatureTerrain> terrainInterdit = new ArrayList<NatureTerrain>();
	private boolean remplissage;
	private boolean deversage;
	private boolean stopped;
	private boolean occupe;

	public Robot(Carte carte, Case position, Simulateur simul, int quantiteEau, int vitesse) {
		this.carte = carte;
		this.position = position;
		this.simul = simul;
		this.quantiteEau  = quantiteEau;
		this.vitesse = vitesse;
		this.remplissage = false;
		this.deversage = false;
		this.stopped = false;
		this.occupe = false;
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

	public boolean isOccupe() {
		return occupe;
	}

	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}

	public ArrayList<NatureTerrain> getTerrainInterdit(){
		return this.terrainInterdit;
	}

	/** Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau */
	public abstract int dureeDeversage(int quantiteNecessaire);


	/** Renvoie la durée mis par le robot pour remplir son réservoir */
	public abstract int dureeRemplissage();


	/** Renvoie la capcité maximale du reservoir du robot  */
	public abstract int capaciteReservoire();
	

	/** Deverse une quantité d'eau sur une case */
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol);
		Incendie feuAEteindre = this.getPosition().getIncendie();
		if(feuAEteindre != null) {
			feuAEteindre.eteindre(vol);
			if (feuAEteindre.getNbLitres() == 0) {
				this.getPosition().setIncendie(null);
				this.occupe = false;
			}
		}
	}

	/** Indique si le robot a un voisin qui contient de l'eau */
	public Case estVoisinEau() {
		int lig = this.position.getLigne();
		int col = this.position.getColonne();

		// On récupère les voisins
		Case caseNord = null, caseSud = null, caseOuest = null, caseEst = null;
		if (lig > 0) caseNord = carte.getCase(lig - 1, col);
		if (caseNord != null && caseNord.getNature() == NatureTerrain.EAU) return caseNord;

		if (lig < carte.getNbLignes() - 1) caseSud = carte.getCase(lig + 1, col);
		if (caseSud != null && caseSud.getNature() == NatureTerrain.EAU) return caseSud;

		if (col > 0) caseOuest = carte.getCase(lig, col - 1);
		if (caseOuest != null && caseOuest.getNature() == NatureTerrain.EAU) return caseOuest;

		if (col < carte.getNbColonnes() - 1) caseEst = carte.getCase(lig, col + 1);
		if (caseEst != null && caseEst.getNature() == NatureTerrain.EAU) return caseEst;

		return null;
	}
	
	
	/** Indique si la case a un voisin qui contient de l'eau */
	public Case estVoisinEau(Case caseDest) {
		int lig = caseDest.getLigne();
		int col = caseDest.getColonne();

		// On récupère les voisins
		Case caseNord = null, caseSud = null, caseOuest = null, caseEst = null;
		if (lig > 0) caseNord = carte.getCase(lig - 1, col);
		if (caseNord != null && caseNord.getNature() == NatureTerrain.EAU) return caseNord;

		if (lig < carte.getNbLignes() - 1) caseSud = carte.getCase(lig + 1, col);
		if (caseSud != null && caseSud.getNature() == NatureTerrain.EAU) return caseSud;

		if (col > 0) caseOuest = carte.getCase(lig, col - 1);
		if (caseOuest != null && caseOuest.getNature() == NatureTerrain.EAU) return caseOuest;

		if (col < carte.getNbColonnes() - 1) caseEst = carte.getCase(lig, col + 1);
		if (caseEst != null && caseEst.getNature() == NatureTerrain.EAU) return caseEst;

		return null;
	}
	
	
	/** Renvoie le point d'accès à l'eau du robot le plus proche */
	public Case accesEauPlusProche() {
		if (this.estVoisinEau() != null) return this.position;

		int ligCourante = this.position.getLigne(), colCourante = this.position.getColonne();
		int maxDist = Math.max(carte.getNbColonnes(), carte.getNbLignes());
		for (int dist = 1; dist < maxDist; dist++) {
			for (int lig = ligCourante - dist; lig <= ligCourante + dist; lig++) {
				int ecartLig = dist - Math.abs(ligCourante - lig);
				for (int col = colCourante - ecartLig; col <= colCourante + ecartLig; col++) {
					if (lig < 0 || lig >= carte.getNbLignes()) continue;
					if (col < 0 || col >= carte.getNbColonnes()) continue;
					Case voisin = carte.getCase(lig, col);
					if (this.estVoisinEau(voisin) != null) return voisin;
				}
			}
		}
		return null;
	}


	/** Renvoie le temps mis pour accéder à une case voisine */
	public int tempsAccesVoisin(Direction dir) {
		int distance = this.carte.getTailleCases();
		double vitesseMetreParSeconde = this.vitesse / 3.6;
		double temps = distance / vitesseMetreParSeconde;
		int tempsEntier = (int)temps;
		return tempsEntier;
	}

	public int tempsAccesVoisin(Case caseDepart, Direction dir){
		int distance = this.carte.getTailleCases();
		double vitesseMetreParSeconde = this.vitesse / 3.6;
		double temps = distance / vitesseMetreParSeconde;
		int tempsEntier = (int)temps;
		return tempsEntier;
	}

	public abstract void remplirReservoir();

	public abstract void draw(GUISimulator gui, int tailleCase);

	
	public void drawReservoir(GUISimulator gui, int heightRobot, int tailleCase, int qteEauMax) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
		int barreHeight = 8;
		int barreWidth = tailleCase/3;
		int barreX = caseX + tailleCase/2;
		int barreY = caseY + tailleCase/2 + heightRobot/2 + 4 + barreHeight/2;
		Color couleurFond = Color.WHITE;
		if(this.quantiteEau == qteEauMax) {
			couleurFond = Color.decode("#3393ff");
		}
		gui.addGraphicalElement(new Rectangle(barreX, barreY, Color.BLACK, couleurFond, barreWidth, barreHeight));
		if(this.quantiteEau > 0 && this.quantiteEau < qteEauMax) {
			int barreVarieWidth = barreWidth*this.quantiteEau/qteEauMax;
			int barreVarieX = barreX - (barreWidth - barreVarieWidth);
			
			gui.addGraphicalElement(new Rectangle(barreVarieX, barreY, null, Color.decode("#3393ff"), barreVarieWidth, barreHeight));
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
}

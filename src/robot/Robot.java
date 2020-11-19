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
import gui.ImageElement;
import gui.Rectangle;
import gui.Text;


public abstract class Robot {
	private Case position;
	private int quantiteEau;
	private int vitesse;
	private Carte carte;
	private Simulateur simul;
	private ArrayList<NatureTerrain> terrainInterdit = new ArrayList<NatureTerrain>();
	private boolean remplissage;
	private boolean deversage;
	private boolean stopped;
	private boolean occupe;
	
	/**	Créer un robot quelconque avec les attributs généraux à tous les robots 
	 * 	@param carte	 	carte sur lequel le robot se déplace
	 * 	@param position		postion initiale du robot
	 * 	@param simul		simulateur de l'éxécution
	 * 	@param quantiteEau	quantite d'eau initiale dans le réservoir du robot
	 * 	@param vitesse		vitesse initiale du robot
	 */ 
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

	/** Donne la vitesse actuelle du robot 
	 *	@return la vitesse actuelle du robot  
	 */
	public int getVitesse() {
		return vitesse;
	}
	
	/**	Modifie la vitesse de déplacement du robot 
	 * 	@param vitesse	nouvelle vitesse du robot
	 */
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}
	
	/** Donne case à laquelle se situe le robot 
	 * 	@return la position du robot
	 */
	public Case getPosition() {
		return position;
	}

	/** Change la position du robot 
	 * 	@param newPosition	case à laquelle le repond doit se repositionner
	 */
	public void setPosition(Case newPosition) {
		this.position = newPosition;
		
	}
	
	/** Donne la quantité d'eau présente dans le réservoir du robot 
	 * 	@return la quatité d'eau du réservoir du robot 
	 */
	public int getQuantiteEau() {
		return quantiteEau;
	}
	
	/** Renvoie la carte de l'éxecution 
	 * 	@return la carte 
	 */
	public Carte getCarte() {
		return carte;
	}
	
	/** Renvoie le simulateur de l'éxecution 
	 * 	@return le simulateur
	 */
	public Simulateur getSimul() {
		return simul;
	}
	
	/** Modifie la quantité d'eau dans le réservoir du robot 
	 * 	@param quantiteEau	nouvelle quantite d'eau présente dans le réservoir du robot
	 */
	public void setQuantiteEau(int quantiteEau) {
		this.quantiteEau = quantiteEau;
	}

	/** Indique si le robot est occupé à se remplir ou déverser 
	 * 	@return le booléen stopped
	 */
	public boolean isStopped() {
		return this.stopped;
	}
	
	/** Change le booléen indiquant si le robot est arrêté pour déverser de l'eau ou remplir
	 * 	@param stop	statut actuel du robot (stoppé ou non)
	 */
	public void setStopped(boolean stop) {
		this.stopped = stop;
	}

	/** Indique si le robot est entrain de se remplir 
	 * 	@return  le booléen remplissage
	 */
	public boolean isRemplissage() {
		return remplissage;
	}
	
	/** Change la booléen indiquant si le robot est entrain de se remplir
	 * 	@param remplissage	statut actuel du robot (entrain de se remplir ou non)
	 */
	public void setRemplissage(boolean remplissage) {
		this.remplissage = remplissage;
	}
	
	/** Indique si le robot est entrain de déverser de l'eau 
	 * 	@return  le booléen remplissage
	 */
	public boolean isDeversage() {
		return deversage;
	}
	
	/** Change la booléen indiquant si le robot est entrain de déverser de l'eau 
	 * 	@param deversage	statut actuel du robot (entrain de déverser ou non)
	 */
	public void setDeversage(boolean deversage) {
		this.deversage = deversage;
	}
	
	/** 
	 * 	Indique si le robot est occupé à se remplir, se déplacer ou déverser
	 * 	@return le booléen occupe 
	 */
	public boolean isOccupe() {
		return occupe;
	}
	
	/** 
	 * 	Change le booléen indiquant statut d'un robot 
	 * 	@param occupe	statut actuel du robot (occupé ou non)
	 */ 
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}
	
	public void addTerrainInterdit(NatureTerrain terrain) {
		this.terrainInterdit.add(terrain);
	}
	
	/** 
	 * 	Renvoie la liste des terrains sur lesquels le robot ne peut se déplacer 
	 *	@return la liste des terrains interdits 
	 */
	public ArrayList<NatureTerrain> getTerrainInterdit(){
		return this.terrainInterdit;
	}
	

	/** 
	 * 	Renvoie la durée mis par le robot pour vider son réservoir d'une quantite d'eau 
	 * 	@param quatiteNecessaire	quantite d'eau qu'il faut déverser
	 */
	public abstract int dureeDeversage(int quantiteNecessaire);


	/** 
	 * 	Renvoie la durée mis par le robot pour remplir son réservoir 
	 * 	@return la durée de remplissage 
	 */
	public abstract int dureeRemplissage();


	/** 
	 * 	Renvoie la capcité maximale du reservoir du robot  
	 * 	@return la capacité maximale du réservoir
	 */
	public abstract int capaciteReservoire();
	

	/** 
	 * 	Deverse une quantité d'eau sur une case 
	 * 	@param vol	volume d'eau qu'il faut déverser
	 */
	public void deverserEau(int vol) {
		int quantiteEauRestante = this.getQuantiteEau();
		assert(vol <= quantiteEauRestante && vol > 0);
		this.setQuantiteEau(quantiteEauRestante - vol);
		Incendie feuAEteindre = this.getPosition().getIncendie();
		if(feuAEteindre != null) {
			feuAEteindre.eteindre(vol);
			if (feuAEteindre.getNbLitres() == 0) {
				this.getPosition().setIncendie(null);
				this.getPosition().setEstBrulee(true);
				this.occupe = false;
			}
		}
	}

	/** 
	 * 	Indique si le robot a un voisin qui contient de l'eau 
	 *	@return la case voisine de nature eau ou null s'il y en a pas 
	 */
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
	
	
	/** 
	 * 	Retourne, pour une case donée, la case voisin ayant de l'eau sinon null  
	 * 	@param caseDest	case pour laquelle on veut le voisin
	 * 	@return la case voisine de nature eau ou null s'il y en a pas
	 */
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
	
	
	/** 
	 * 	Renvoie le point d'accès à l'eau du robot le plus proche 
	 * 	@return la case d'eau la plus proche ou null s'il y en a pas
	 */
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


	/** Calcul et renvoie le temps mis pour accéder à une case voisine à partir de la 
	 * 	case où se situe le robot
	 * 	@param dir	direction vers laquelle se diriger
	 * 	@return temps d'accès au voisin
	 */
	public int tempsAccesVoisin(Direction dir) {
		int distance = this.carte.getTailleCases();
		double vitesseMetreParSeconde = this.vitesse / 3.6;
		double temps = distance / vitesseMetreParSeconde;
		int tempsEntier = (int)temps;
		return tempsEntier;
	}

	/** Calcul et renvoie le temps mis pour accéder à une case voisine à partir d'une case quelconque
	 * 	@param caseDepart	case à partir de laquelle le calcul est fait
	 * 	@param dir			direction vers laquelle se diriger
	 * 	@return temps d'accès au voisin
	 */
	public int tempsAccesVoisin(Case caseDepart, Direction dir){
		int distance = this.carte.getTailleCases();
		double vitesseMetreParSeconde = this.vitesse / 3.6;
		double temps = distance / vitesseMetreParSeconde;
		int tempsEntier = (int)temps;
		return tempsEntier;
	}
	
	/** Chaque robot remplit son réservoir d'une manière différente */
	public abstract void remplirReservoir();

	/**
     * Dessine le robot
     * @param gui 			l'interface graphique associée à l'exécution, dans laquelle se fera le
     * 						dessin.
     * @param tailleCase	taille des cases de la simulation courante
    */
	public abstract void draw(GUISimulator gui, int tailleCase);

	
	/**
     * Dessine une barre indiquant l'eau restante dans le réservoir du robot 
     * et un texte indiquant si le robot est entrain de déverser de l'eay ou de se remplir
     * @param gui l'interface graphique associée à l'exécution, dans laquelle se fera le
     * dessin.
     * @param heightRobot longueur du dessin du robot
     * @param tailleCase	taille des cases de la simulation courante
     * @param qteEauMax quantité d'eau maximale que le robot peut avoir dans son réservoir
    */
	public void drawReservoir(GUISimulator gui, int heightRobot, int tailleCase, int qteEauMax) {
		Case caseRobot = this.getPosition();
		int caseX = caseRobot.getColonne() * tailleCase;
		int caseY = caseRobot.getLigne() * tailleCase;
		int barreHeight = tailleCase*8/100;
		int barreWidth = tailleCase/3;
		int barreX = caseX + tailleCase/2;
		int barreY = caseY + tailleCase/2 + heightRobot/2 + 4 + barreHeight/2;
		Color couleurFond = Color.WHITE;
		if(this.quantiteEau == qteEauMax) {
			couleurFond = Color.decode("#3393ff");
		}
		Rectangle reservEntier = new Rectangle(barreX, barreY, Color.BLACK, couleurFond, barreWidth, barreHeight); 
		gui.addGraphicalElement(reservEntier);
		if(this.quantiteEau > 0 && this.quantiteEau < qteEauMax) {
			int barreVarieWidth = barreWidth*this.quantiteEau/qteEauMax;
			int barreVarieX = barreX - (barreWidth - barreVarieWidth)/2;
			
			Rectangle reservVarie = new Rectangle(barreVarieX, barreY, null, Color.decode("#3393ff"), barreVarieWidth, barreHeight);
			gui.addGraphicalElement(reservVarie);
			Rectangle contourReserv = new Rectangle(barreX, barreY, Color.BLACK, null, barreWidth, barreHeight); 
			gui.addGraphicalElement(contourReserv);
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

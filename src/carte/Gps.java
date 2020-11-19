package carte;

import java.util.ArrayList;
import java.util.*;
//import java.util.Math;

import robot.Robot;
import carte.Carte;
import carte.NatureTerrain;
import carte.Case;
import evenement.*;
import donnees.DonneesSimulation;

/**
 * @author equipe 66
 * Permet de déterminer le plus court chemin pour un robot donné entre deux cases.
 * Pour ce faire, on utilise l'algorithme A*, plus rapide que Dijkstra.
 * En plus de déterminer le plus court chemin entre deux cases, cette classe
 * permet la génération des événements permettant au robot de se déplacer
 * Les parents des cases sont stockées dans des HashMap avec comme clé la fille et comme valeur le parent associé
 */
public class Gps {

	private Robot robot;
	private ArrayList<Case> fermees = new ArrayList<Case>();
	private ArrayList<Case> ouverts = new ArrayList<Case>();

	private Map<Case, Case> parents;
	private Map<Case, Integer> valeursG;
	private Map<Case, Integer> valeursH;

	private Case debut;
	private Case fin;

	/**
	 * Initialisation des case de debut, de fin, du robot et des differents dictionnaires permettant les calculs
	 * @param robot robot dont on veut determiner l'itinéraire
	 * @param debut case de départ
	 * @param case d'arrivée
	 */
	public Gps(Robot robot, Case debut, Case fin) {
		if(!(robot.getTerrainInterdit().contains(fin.getNature()))) {
			this.robot = robot;
			this.debut = debut;
			this.fin = fin;
		}
		this.parents = new HashMap<Case, Case>();
		this.valeursG = new HashMap<Case, Integer>();
		this.valeursH = new HashMap<Case, Integer>();
	}
	
	
	/**
	 * Associe un parent a fille dans le dictionnaire. Créer une association si fille n'a pas encore de parent
	 * @param fille case fille
	 * @param parent case parent
	 */
	public void setParent(Case fille, Case parent){
			this.parents.put(fille, parent);
	}
	
	
	/**
	 * Renvoie le parent de la case demandée
	 * @param fille case dont on veut trouver le parent dans l'itinéraire
	 * @return parent de la case demandée
	 */
	public Case getParent(Case fille){
		return this.parents.get(fille);
	}
	
	
	/**
	 * Actualise le temps pour arriver a la case en question depuis le départ
	 * @param caseActuelle case dont on veut le chemin le plus court pour l'instat pour y accéder depuis le départ
	 * @param valeur temps depuis le départ
	 */
	public void setG(Case caseActuelle, int valeur){
		this.valeursG.put(caseActuelle, valeur);
	}
	
	
	/**
	 * Permet d'obtenir le temps pour accéder a la case
	 * @param caseActuelle case dont on veut le temps
	 * @return temps calculée depuis la départ
	 */
	public int getG(Case caseActuelle){
		return this.valeursG.get(caseActuelle);
	}
	
	
	/**
	 * Modifie distance de Manhattan entre la case et l'arrivée
	 * @param caseCible case dont on veut calculler la distance de Manhattan avec l'arrivée
	 */
	public void setH(Case caseCible){
		this.valeursH.put(caseCible, this.calculManhattan(caseCible, this.fin));
	}
	
	
	/**
	 * Permet d'obtenir la distance de Manhattan avec l'arrivée distance de Manhattan entre la case et l'arrivée
	 * @param caseCible case dont on veut la distance de Manhattan avec l'arrivée
	 * @return temps estimée pour l'arrivée
	 */
	public int getH(Case caseCible){
		return this.valeursH.get(caseCible);
	}


	/** Calcul le plus court chemin en modifiant les attributs de la classe
	 *  La méthode renvoie false si ce chemin n'existe pas, true sinon
	 *
	 * Principe de A* :
	 * On possède deux listes : la liste des ouverts et la liste des fermeés.
	 * La liste des fermés contient les cases dont on sait déjà qu'on a trouvé le plus court chemin pour y accéder
	 * La liste des ouverts contient les cases dont on a déjà calculé le parent. Ces cases sont les potentiels sujets à la prochaine itération
	 * A chaque tour de boucle, tant que l'on a encore des cases a regarder (c'est -à-dire si la liste des ouverts est non vide), on prend la case de la liste des ouverts dont la somme (cout depuis la case de départ + cout supposé pour atteindre l'arrivée) est la plus faible.
	 * Cette case est enlevée à la liste des ouverts, et rajoutter à la liste des fermés. (On connait maintenant le plus court chemin pour l'atteindre)
	 * On regarde alors ses voisins : pour chaque voisin, si on ne peut pas y accéder ou bien il est déja dans la liste des fermés, on passe.
	 * Sinon, si il n'est dans la la liste des ouvets, on le rajoutte, et dans le cas inverse, on actualise ses valeurs si on est dans un plus court chemin pour l'atteindre.
	 * On recommence ces tours de boucle jusqu'à mettre la case de fin dans la liste des fermés ou bien quand il n'y pas plus de candidat.
	 * @param simul simulation dans laquelle on se situe
	 * @param donnees donnes utilisées
	 * @return booléen si le chemin a été trouvé on non
	 */
	public boolean trouverChemin(Simulateur simul, DonneesSimulation donnees) {
		if (this.debut == null) return false;
		// Initialisation des valeurs de la liste des ouverts, avec la case de début
		this.ouverts.add(this.debut);
		this.setG(this.debut, 0);
		this.setH(this.debut);

		while(!ouverts.isEmpty()){ //tant que l'on a des cases atteignables

			Case current = this.trouverFMini(ouverts); //on prend celle qui semble être la plus proche de l'arrivée ET du départ
			if(current == this.fin){ //si c'est la case de fin, on a trouvé le chemin
				return true;
			}

			ArrayList<Case> voisins = this.robot.getCarte().getVoisins(current); //on liste les voisins de la case en quesion
			this.fermees.add(current); //on met la case dans la lite des fermés
			this.ouverts.remove(current); //et on l'enlève de la liste des ouverts
			for(Case caseVoisine : voisins){ //pour chaque case voisine

				if(this.peutMarcher(caseVoisine) && !this.fermees.contains(caseVoisine)){ //si elle est atteignable et qu'elle est pas dans les fermés
					if (!this.ouverts.contains(caseVoisine)) { //si elle n'est pas encore dans la liste des ouverts, on calcule ses valeurs et on l'ajoutte
						this.setH(caseVoisine);
						this.setParent(caseVoisine, current);
						this.setG(caseVoisine, this.getG(current) + this.robot.tempsAccesVoisin(current, current.getDirection(caseVoisine)));
						this.ouverts.add(caseVoisine);
					} else { //sinon, on actualise ses diffèrentes valeurs
						if(this.getG(caseVoisine) > this.getG(current) +  this.robot.tempsAccesVoisin(current, current.getDirection(caseVoisine))){
							this.setParent(caseVoisine,current);
							this.setG(caseVoisine, this.getG(current) +  this.robot.tempsAccesVoisin(current, current.getDirection(caseVoisine)));
						}
					}
				}
			}
		}
		System.out.println("Chemin non trouvé");
		return false;
	}
	
	
	/**
	 * Permet de calculer la distance de Manhattan entre deux cases.
	 * @param case1 premiere case
	 * @param case2 deuxieme case
	 * @return renvoie la distande Manhattan entre deux cases
	 */
	private int calculManhattan(Case case1, Case case2){
		return Math.abs(case1.getColonne() - case2.getColonne()) + Math.abs(case1.getLigne() - case2.getLigne());
	}
	
	
	/**
	 * Renvoie un booléen permettant de savoir si le robot dont on veut calculer la trajectoire peut accéder la case
	 * @param surCase case sur laquelle on veut peut-être marcher
	 * @return renvoie si le robot peut marcher sur la case
	 */
	private boolean peutMarcher(Case surCase){
		return !(this.robot.getTerrainInterdit().contains(surCase.getNature()));
	}
	
	
	/**
	 * Permet d'obtenir la case d'une liste ayant la valeur de f = g+h la plus faible
	 * @param ouverts liste sur laquelle ont travaille
	 * @return renvoie la case dont la valeur g+h est la plus faible
	 */
	private Case trouverFMini(ArrayList<Case> ouverts){
		int indiceMin = 0;
		int valeurMin = this.getG(ouverts.get(0)) + this.getH(ouverts.get(0));
		for(int curseur = 1; curseur < ouverts.size(); curseur++){
			if(this.getG(ouverts.get(curseur)) + this.getH(ouverts.get(curseur)) < valeurMin){
				indiceMin = curseur;
				valeurMin = this.getG(ouverts.get(curseur)) + this.getH(ouverts.get(curseur));
			}
		}
		return ouverts.get(indiceMin);
	}

	
	/**
	 * Permet de créer les différents événements permetttant au robot de suivre le chemin
	 * @param caseChemin case que l'on veut atteindre
	 * @param simul simulation utilisée
	 * @param donnees données utilisées
	 * @return date de fin de déplacements
	 */
	private long creationEvenementChemin(Case caseChemin, Simulateur simul, DonneesSimulation donnees){
			if(!this.parents.containsKey(caseChemin)){
				return simul.getDateSimulation();
			}
			else{
				long ancienneDate = creationEvenementChemin(this.getParent(caseChemin), simul, donnees);
				DeplacementDebut nouvelleEtapeChemin = new DeplacementDebut(ancienneDate, simul, this.robot, this.getParent(caseChemin).getDirection(caseChemin), donnees.getCarte());
				simul.ajouteEvenement(nouvelleEtapeChemin);
				ancienneDate += this.robot.tempsAccesVoisin(this.getParent(caseChemin).getDirection(caseChemin));
				return ancienneDate;
			}
	}

	
	/** 
	 * Crée les évènemenets de déplacements du robot et renvoie la date de fin du déplacement
	 * @param simul simulation utilisée
	 * @param donnees données utilisées
	 * @return date de fin de déplacements
	 */
	public long creationEvenementChemin(Simulateur simul, DonneesSimulation donnees){
		ArrayList<Case> chemin = new ArrayList<Case>();
		Case current = this.fin;
		while(this.parents.containsKey(current)) {
			chemin.add(current);
			current = this.getParent(current);
		}

		long date = simul.getDateSimulation();
		Case caseChemin;
		for(int i = chemin.size() - 1 ; i >= 0 ; i--) {
			caseChemin = chemin.get(i);
			DeplacementDebut nouvelleEtapeChemin = new DeplacementDebut(date, simul, this.robot, this.getParent(caseChemin).getDirection(caseChemin), donnees.getCarte());
			simul.ajouteEvenement(nouvelleEtapeChemin);
			date += this.robot.tempsAccesVoisin(this.getParent(caseChemin).getDirection(caseChemin));
		}
		return date;
	}


	/** 
	 * Renvoie la date de fin du déplacement sans rajoutter des événements
	 * @param simul simulateur utilisée
	 * @return renvoie la date de fin de déplacement
	 */
	public long dateArrivee(Simulateur simul){
		ArrayList<Case> chemin = new ArrayList<Case>();
		Case current = this.fin;
		while(this.parents.containsKey(current)) {
			chemin.add(current);
			current = this.getParent(current);
		}

		long date = simul.getDateSimulation();
		Case caseChemin;
		for(int i = chemin.size() - 1 ; i >= 0 ; i--) {
			caseChemin = chemin.get(i);
			date += this.robot.tempsAccesVoisin(this.getParent(caseChemin).getDirection(caseChemin));
		}
		return date;
	}



}

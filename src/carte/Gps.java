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

public class Gps {

	private Robot robot;
	private ArrayList<Case> fermees = new ArrayList<Case>();
	private ArrayList<Case> ouverts = new ArrayList<Case>();

	private Map<Case, Case> parents;
	private Map<Case, Integer> valeursG;
	private Map<Case, Integer> valeursH;

	private Case debut;
	private Case fin;

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

	public void setParent(Case fille, Case parent){
			this.parents.put(fille, parent);
	}

	public Case getParent(Case fille){
		return this.parents.get(fille);
	}

	public void setG(Case caseActuelle, int valeur){
		this.valeursG.put(caseActuelle, valeur);
	}

	public int getG(Case caseActuelle){
		return this.valeursG.get(caseActuelle);
	}

	public void setH(Case caseCible){
		this.valeursH.put(caseCible, this.calculManhattan(caseCible, this.fin));
	}

	public int getH(Case caseCible){
		return this.valeursH.get(caseCible);
	}


	/** Calcul le plus court chemin en modifiant les attributs de la classe 
	 *  La méthode renvoie false si ce chemin n'existe pas, true sinon */
	public boolean trouverChemin(Simulateur simul, DonneesSimulation donnees) {
		if (this.debut == null) return false;
		this.ouverts.add(this.debut);
		this.setG(this.debut, 0);
		this.setH(this.debut);

		while(!ouverts.isEmpty()){

			Case current = this.trouverFMini(ouverts);
			if(current == this.fin){
//				this.creationEvenementChemin(simul, donnees);
				return true;
			}
			
			ArrayList<Case> voisins = this.robot.getCarte().getVoisins(current);
			this.fermees.add(current);
			this.ouverts.remove(current);
			for(Case caseVoisine : voisins){

				if(this.peutMarcher(caseVoisine) && !this.fermees.contains(caseVoisine)){
					if (!this.ouverts.contains(caseVoisine)) {
						this.setH(caseVoisine);
						this.setParent(caseVoisine, current);
						this.setG(caseVoisine, this.getG(current) + this.robot.tempsAccesVoisin(current, current.getDirection(caseVoisine)));
						this.ouverts.add(caseVoisine);
					} else {
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

	private int calculManhattan(Case case1, Case case2){
		return Math.abs(case1.getColonne() - case2.getColonne()) + Math.abs(case1.getLigne() - case2.getLigne());
	}

	private boolean peutMarcher(Case surCase){
		return !(this.robot.getTerrainInterdit().contains(surCase.getNature()));
	}

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

	/** Crée les évènemenets de déplacements du robot et renvoie la date de fin du déplacement */
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
	
	
	/** Renvoie la date de fin du déplacement */
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

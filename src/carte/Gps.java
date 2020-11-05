package carte;

import java.util.ArrayList;
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
	private Case debut;
	private Case fin;

	public Gps(Robot robot, Case debut, Case fin) {
		if(!(robot.getTerrainInterdit().contains(fin.getNature()))) {
			this.robot = robot;
			this.debut = debut;
			this.fin = fin;
		}
	}


	// On pourrait renvoyer un ArrayList qui contient le chemin trouvé ou null si pas de chemin
	public void trouverChemin(Simulateur simul, DonneesSimulation donnees) {
		this.ouverts.add(this.debut);
		// Créer une variable g qui augmente à chaque fois qu'on rajoute une étape au chemin

		while(!ouverts.isEmpty()){

			Case current = this.trouverFMini(ouverts);
			if(current == this.fin){
				this.creationEvenementChemin(current, simul, donnees);
				return ;
			}
			ArrayList<Case> voisins = this.robot.getCarte().getVoisins(current);
			this.fermees.add(current);
			this.ouverts.remove(current);
			for(Case caseVoisine : voisins){

				caseVoisine.seth(calculManhattan(caseVoisine, this.fin));
				if(!this.peutMarcher(caseVoisine) || this.fermees.contains(caseVoisine)){
					;
				}
				else{
					if(!this.ouverts.contains(caseVoisine)){
						caseVoisine.setParent(current);
						caseVoisine.seth(calculManhattan(caseVoisine, this.fin));
						caseVoisine.setg(current.getg() + this.robot.tempsAccesVoisin(current.getDirection(caseVoisine)));
						this.ouverts.add(caseVoisine);
					}
					else{
						if(caseVoisine.getg() > current.getg() +  this.robot.tempsAccesVoisin(current.getDirection(caseVoisine))){
							caseVoisine.setParent(current);
							caseVoisine.setg(current.getg() +  this.robot.tempsAccesVoisin(current.getDirection(caseVoisine)));
						}
					}
				}
			}

		}
	}

	private int calculManhattan(Case case1, Case case2){
		return Math.abs(case1.getColonne() - case2.getColonne()) + Math.abs(case1.getLigne() - case2.getLigne());
	}

	private boolean peutMarcher(Case surCase){
		return !(this.robot.getTerrainInterdit().contains(surCase.getNature()));
	}

	private Case trouverFMini(ArrayList<Case> ouverts){
		int indiceMin = 0;
		int valeurMin = ouverts.get(0).getg() + ouverts.get(0).geth();
		for(int curseur = 1; curseur < ouverts.size(); curseur++){
			// Au lieu de faire un get, on peut calculer f, en rajoutant g en paramètre
			// Genre f = g + calcul_manhattan(fin, case)
			// Pour l'initialisation de valeur min, on peut mettre le plus grand int existant
			if(ouverts.get(curseur).getg() + ouverts.get(curseur).geth() < valeurMin){
				indiceMin = curseur;
				valeurMin = ouverts.get(curseur).getg() + ouverts.get(curseur).geth();
			}
		}
		return ouverts.get(indiceMin);
	}

	private long creationEvenementChemin(Case caseChemin, Simulateur simul, DonneesSimulation donnees){
			if(caseChemin.getParent() == null){
				return simul.getDateSimulation();
			}
			else{
				long ancienneDate = creationEvenementChemin(caseChemin.getParent(), simul, donnees);
				DeplacementDebut nouvelleEtapeChemin = new DeplacementDebut(ancienneDate, simul, this.robot, caseChemin.getParent().getDirection(caseChemin), donnees.getCarte());
				simul.ajouteEvenement(nouvelleEtapeChemin);
				ancienneDate += this.robot.tempsAccesVoisin(caseChemin.getParent().getDirection(caseChemin));
				return ancienneDate;
			}
	}

}

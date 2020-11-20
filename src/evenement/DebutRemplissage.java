package evenement;

import robot.*;
import carte.*;

/**
 * Evenement qui correspond au début du remplissage du réservoir d'un robot
 * @author equipe 66
 */
public class DebutRemplissage extends Evenement {
	Robot robot;
	
	/**
	 * Créé l'évènement qui correspond au début du remplissage du réservoir d'un robot
	 * @param date date de début du remplissage
	 * @param simul simulateur associé à l'évènement
	 * @param r robot concerné par l'évènement
	 */
	public DebutRemplissage(long date, Simulateur simul, Robot r) {
		super(date, simul);
		this.robot = r;
	}

	/**
	 * Exécute l'évènement
	 */
	@Override
	public void execute() {
		robot.setRemplissage(true);
		
		// On programme la fin du remplissage
		long dateFinRemplissage = dateFinEvenement();
		FinRemplissage remplissageFin = new FinRemplissage(dateFinRemplissage, this.getSimul(), robot);
		this.getSimul().ajouteEvenement(remplissageFin);
	}

	/**
	 * Calcul la date de fin de l'évènement
	 * @return La date de fin de l'évènement
	 */
	public long dateFinEvenement() {
		return this.getDate() + robot.dureeRemplissage();
	}
}

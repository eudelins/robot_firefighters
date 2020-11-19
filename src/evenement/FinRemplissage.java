package evenement;

import robot.*;

/**
 * @author equipe 66
 * Evenement qui correspond à la fin du remplissage du réservoir d'un robot
 */
public class FinRemplissage extends Evenement {
	private Robot robot;
	
	/**
	 * Créé l'évènement qui correspond à la fin du remplissage du réservoir d'un robot
	 * @param date date de début du remplissage
	 * @param simul simulateur associé à l'évènement
	 * @param r robot concerné par l'évènement
	 */
	public FinRemplissage(long date, Simulateur simul, Robot r) {
		super(date, simul);
		this.robot = r;
	}
	
	/**
	 * Exécute l'évènement
	 */
	@Override
	public void execute() {
		this.robot.remplirReservoir();
		this.robot.setOccupe(false);
		this.robot.setRemplissage(false);
	}

}

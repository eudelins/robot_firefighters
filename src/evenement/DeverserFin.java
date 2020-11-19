package evenement;

import carte.Case;
import robot.Robot;

/**
 * Evenement qui correspond à la fin du déversage de l'eau d'un robot
 */
public class DeverserFin extends Evenement{
	private Robot robot;
	private int qteDeverse;
	private Case caseDeversage;
	
	/**
	 * Crée l'évènement correspondant à la fin du déversage de l'eau d'un robot
	 * @param date la date à laquelle prend fin le déversage (donc celle où l'évènement a lieu)
	 * @param simul le simulateur associé à l'évènement
	 * @param robot le robot concerné par l'évènement
	 * @param qteDeverse la quantité d'eau déversée
	 * @param caseDeversage la case sur laquelle le robot déverse son eau
	 */
	public DeverserFin(long date, Simulateur simul, Robot robot, int qteDeverse, Case caseDeversage) {
		super(date, simul);
		this.robot = robot;
		this.qteDeverse = qteDeverse;
		this.caseDeversage = caseDeversage;
	}
	
	/**
	 * Execute l'évènement
	 */
	public void execute() {
		robot.deverserEau(this.qteDeverse);
		this.robot.setOccupe(false);
		if (caseDeversage.getIncendie() != null) {
			caseDeversage.getIncendie().setAffecte(false);
		}
		this.robot.setDeversage(false);
	}
}

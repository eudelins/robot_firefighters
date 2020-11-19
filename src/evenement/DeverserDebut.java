package evenement;

import robot.Robot;
import carte.*;

/**
 * @author equipe 66
 * Evenement qui correspond au début du déversage de l'eau d'un robot
 */
public class DeverserDebut extends Evenement{
	private Robot robot;
	private Case caseDeversage;
	private int qteDeverse;

	/**
	 * Crée l'évènement correspondant au début du déversage de l'eau d'un robot
	 * @param date la date à laquelle prend fin le déversage (donc celle où l'évènement a lieu)
	 * @param simul le simulateur associé à l'évènement
	 * @param robot le robot concerné par l'évènement
	 * @param caseDeversage la case sur laquelle le robot déverse son eau
	 */
	public DeverserDebut(long date, Simulateur simul, Robot robot, Case caseDeversage) {
		super(date, simul);
		this.robot = robot;
		this.caseDeversage = caseDeversage;
		if (caseDeversage.getIncendie() == null) this.qteDeverse = 0;
		else this.qteDeverse = Math.min(robot.getQuantiteEau(), caseDeversage.getIncendie().getNbLitres());
	}
	
	/**
	 * Exécute l'évènement
	 */
	public void execute() {
		robot.setDeversage(true);

		// On programme la fin du déversage
		long dateFinDeverser = dateFinEvenement();
		DeverserFin finDeverser = new DeverserFin(dateFinDeverser, this.getSimul(), robot, qteDeverse, caseDeversage);
		this.getSimul().ajouteEvenement(finDeverser);
	}
	
	/**
	 * Calcul la date de fin de l'évènement
	 * @return La date de fin de l'évènement
	 */
	public long dateFinEvenement() {
		return this.getDate() + robot.dureeDeversage(qteDeverse);
	}
}

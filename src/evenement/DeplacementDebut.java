package evenement;

import carte.Direction;
import carte.Carte;
import robot.*;

/**
 * Evenement qui correspond au début du déplacement d'un robot
 * @author equipe 66
 */
public class DeplacementDebut extends Evenement {
	private Robot robot;
	private Direction dir;
	private Carte carte;
	
	/**
	 * Crée l'évènement qui correspond au début du déplacement d'un robot
	 * @param date la date de début du déplacement
	 * @param simul le simulateur associé à l'évènement
	 * @param robot le robot concerné par le déplacement
	 * @param dir la direction dans laquelle se déplace le robot
	 * @param carte la carte associée au robot
	 */
	public DeplacementDebut(long date, Simulateur simul, Robot robot, Direction dir, Carte carte) {
		super(date, simul);
		this.robot = robot;
		this.dir = dir;
		this.carte = carte;
	}

	/**
	 * Exécute l'évènement
	 */
	@Override
	public void execute() {
		// On planifie la fin du déplacement du robot
		long dateFinDeplacement = dateFinEvenement();
		DeplacementFin finDeplacement = new DeplacementFin(dateFinDeplacement, getSimul(), robot, dir, carte);
		this.getSimul().ajouteEvenement(finDeplacement);
	}
	
	/**
	 * Calcul la date de fin de l'évènement
	 * @return La date de fin de l'évènement
	 */
	public long dateFinEvenement() {
		return this.getDate() + robot.tempsAccesVoisin(dir);
	}
}

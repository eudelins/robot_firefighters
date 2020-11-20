package evenement;

import carte.Carte;
import carte.Case;
import carte.Direction;
import robot.Robot;

/**
 * Evenement qui correspond à la fin du déplacement d'un robot
 * @author equipe 66
 */
public class DeplacementFin extends Evenement {
	private Robot robot;
	private Direction dir;
	private Carte carte;

	/**
	 * Crée l'évènement qui correspond à la fin du déplacement d'un robot
	 * @param date la date de début du déplacement
	 * @param simul le simulateur associé à l'évènement
	 * @param robot le robot concerné par le déplacement
	 * @param dir la direction dans laquelle se déplace le robot
	 * @param carte la carte associée au robot
	 */
	public DeplacementFin(long date, Simulateur simul, Robot robot, Direction dir, Carte carte) {
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
		Case caseActuelle = this.robot.getPosition();
		int lig = caseActuelle.getLigne();
		int col = caseActuelle.getColonne();
		
		switch (dir) {
		case NORD:
			lig--;
			break;
		case SUD:
			lig++;
			break;
		case OUEST:
			col--;
			break;
		case EST:
			col++;
			break;
		}
		
		if (col < 0 || lig < 0 || col > (carte.getNbColonnes() - 1) || lig > (carte.getNbLignes() - 1)) {
			throw new IllegalArgumentException("Le robot sort de la carte");
		} else {
			Case newPosition = carte.getCase(lig, col);
			this.robot.setPosition(newPosition);
		}
	}

}

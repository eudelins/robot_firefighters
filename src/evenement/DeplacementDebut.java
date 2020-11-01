package evenement;

import carte.Direction;
import carte.Carte;
import carte.Case;
import robot.*;

public class DeplacementDebut extends Evenement {
	private Robot robot;
	private Direction dir;
	private Carte carte;
	
	public DeplacementDebut(long date, Simulateur simul, Robot robot, Direction dir, Carte carte) {
		super(date, simul);
		this.robot = robot;
		this.dir = dir;
		this.carte = carte;
	}

	@Override
	public void execute() {
		assert(!robot.isStopped());
		long dateFinDeplacement = dateFinEvenement();
		DeplacementFin finDeplacement = new DeplacementFin(dateFinDeplacement, getSimul(), robot, dir, carte);
		this.getSimul().ajouteEvenement(finDeplacement);
	}
	
	public long dateFinEvenement() {
		return this.getDate() + robot.tempsAccesVoisin(dir);
	}
}

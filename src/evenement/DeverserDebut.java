package evenement;

import robot.Robot;

public class DeverserDebut extends Evenement{
	private Robot robot;
	private int qteDeverse;

	public DeverserDebut(long date, Simulateur simul, Robot robot, int qteDeverse) {
		super(date, simul);
		this.robot = robot;
		this.qteDeverse = qteDeverse;
	}
	
	public void execute() {
		robot.setStopped(true);
		robot.setDeversage(true);
		long dateFinDeverser = dateFinEvenement();
		DeverserFin finDeverser = new DeverserFin(dateFinDeverser, this.getSimul(), robot, qteDeverse);
		this.getSimul().ajouteEvenement(finDeverser);
	}
	
	public long dateFinEvenement() {
		return this.getDate() + robot.dureeDeversage(qteDeverse);
	}
}

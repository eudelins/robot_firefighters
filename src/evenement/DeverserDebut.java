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
		robot.deverserEau(this.qteDeverse);
	}
}

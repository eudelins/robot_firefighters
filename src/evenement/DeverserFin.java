package evenement;

import robot.Robot;

public class DeverserFin extends Evenement{
	private Robot robot;
	private int qteDeverse;
	
	public DeverserFin(long date, Simulateur simul, Robot robot, int qteDeverse) {
		super(date, simul);
		this.robot = robot;
		this.qteDeverse = qteDeverse;
	}
	
	public void execute() {
		robot.deverserEau(this.qteDeverse);
		this.robot.setStopped(false);
		this.robot.setDeversage(false);
	}
}

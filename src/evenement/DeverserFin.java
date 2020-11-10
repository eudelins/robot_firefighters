package evenement;

import carte.Case;
import robot.Robot;

public class DeverserFin extends Evenement{
	private Robot robot;
	private int qteDeverse;
	private Case caseDeversage;
	
	public DeverserFin(long date, Simulateur simul, Robot robot, int qteDeverse, Case caseDeversage) {
		super(date, simul);
		this.robot = robot;
		this.qteDeverse = qteDeverse;
		this.caseDeversage = caseDeversage;
	}
	
	public void execute() {
		robot.deverserEau(this.qteDeverse);
		this.robot.setStopped(false);
		this.robot.setOccupe(false);
		if (caseDeversage.getIncendie() != null) {
			caseDeversage.getIncendie().setAffecte(false);
		}
		this.robot.setDeversage(false);
	}
}

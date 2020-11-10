package evenement;

import robot.Robot;
import carte.*;

public class DeverserDebut extends Evenement{
	private Robot robot;
	private Case caseDeversage;
	private int qteDeverse;

	public DeverserDebut(long date, Simulateur simul, Robot robot, int qteDeverse, Case caseDeversage) {
		super(date, simul);
		this.robot = robot;
		this.qteDeverse = qteDeverse;
		this.caseDeversage = caseDeversage;
	}

	public DeverserDebut(long date, Simulateur simul, Robot robot, Case caseDeversage) {
		super(date, simul);
		this.robot = robot;
		this.caseDeversage = caseDeversage;
		if (caseDeversage.getIncendie() == null) this.qteDeverse = 0;
		else this.qteDeverse = Math.min(robot.getQuantiteEau(), caseDeversage.getIncendie().getNbLitres());
	}
	
	public void execute() {
		robot.setStopped(true);
		robot.setDeversage(true);
		long dateFinDeverser = dateFinEvenement();
		System.out.println("Quantite deverser: " + qteDeverse);
		System.out.println("Date Fin Deverser:" + dateFinDeverser);
		DeverserFin finDeverser = new DeverserFin(dateFinDeverser, this.getSimul(), robot, qteDeverse, caseDeversage);
		this.getSimul().ajouteEvenement(finDeverser);
	}
	
	public long dateFinEvenement() {
		return this.getDate() + robot.dureeDeversage(qteDeverse);
	}
}

package evenement;

import robot.*;
import carte.*;

public class DebutRemplissage extends Evenement {
	Robot robot;
	
	public DebutRemplissage(long date, Simulateur simul, Robot r) {
		super(date, simul);
		this.robot = r;
	}

	@Override
	public void execute() {
		robot.setStopped(true);
		robot.setRemplissage(true);
		long dateFinRemplissage = dateFinEvenement();
		FinRemplissage remplissageFin = new FinRemplissage(dateFinRemplissage, this.getSimul(), robot);
		this.getSimul().ajouteEvenement(remplissageFin);
	}

	public long dateFinEvenement() {
		return this.getDate() + robot.dureeRemplissage();
	}
}

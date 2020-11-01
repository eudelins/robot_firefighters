package evenement;

import robot.*;

public class FinRemplissage extends Evenement {
	private Robot robot;
	
	public FinRemplissage(long date, Simulateur simul, Robot r) {
		super(date, simul);
		this.robot = r;
	}
	
	@Override
	public void execute() {
		this.robot.remplirReservoir();
		this.robot.setStopped(false);
		this.robot.setRemplissage(false);
	}

}

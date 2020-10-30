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
		this.robot.remplirReservoir();
	}

}

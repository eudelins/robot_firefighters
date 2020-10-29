package evenement;

import robot.*;
import carte.*;

public class DebutRemplissage extends Evenement {
	Robot robot;
	
	public DebutRemplissage(long date, Robot r) {
		super(date);
		this.robot = r;
	}

	@Override
	public void execute() {
		this.robot.remplirReservoir();
	}

}

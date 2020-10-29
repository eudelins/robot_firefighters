import src.robot.*;

package src.evenement;

public class FinRemplissage extends Evenement {

	src.robot.Robot robot;
	public FinRemplissage(long date, Robot r) {
		super(date);
		this.robot = r;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

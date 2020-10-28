package evenement;

import robot.Robot;

public class DeverserFin extends Evenement{
	private Robot robot;
	
	public DeverserFin(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	public void execute() {
		this.robot.setStopped(false);
	}
}

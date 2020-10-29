import src.robot.Robot;
import src.carte.*;

package src.evenement;

public class DebutRemplissage extends Evenement {
	Robot robot;
	
	public DebutRemplissage(long date, Robot r) {
		super(date);
		this.robot = r;
		
		if(this.robot.getPosition().getNature() != NatureTerrain.EAU) {
			
		}
	}

	@Override
	public void execute() {
		this.robot.remplirReservoir();
	}

}

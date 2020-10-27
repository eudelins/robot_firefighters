package evenement;

import carte.Direction;
import carte.Carte;
import carte.Case;
import robot.*;

public class DeplacementDebut extends Evenement {
	private Robot robot;
	private Direction dir;
	private Carte carte;
	
	public DeplacementDebut(long date, Robot robot, Direction dir) {
		super(date);
		this.robot = robot;
		this.dir = dir;
	}

	@Override
	public void execute() {
		Case caseActuelle = this.robot.getPosition();
		int lig = caseActuelle.getLigne();
		int col = caseActuelle.getColonne();
		
		switch (dir) {
		case NORD:
			assert(lig > 0);
			lig -= 1;
			break;
		case SUD:
			assert(lig < carte.getNbLignes() - 1);
			lig += 1;
			break;
		case OUEST:
			assert(col > 0);
			col -= 1;
			break;
		case EST:
			assert(col < carte.getNbColonnes() - 1);
			col += 1;
			break;
		}
		
		Case newPosition = carte.getCase(lig, col);
		this.robot.setPosition(newPosition);
	}

}

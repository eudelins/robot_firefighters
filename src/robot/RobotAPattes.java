package robot;
import carte.*;


public class RobotAPattes extends Robot {

	public RobotAPattes(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU);
		if (nature == NatureTerrain.ROCHE) this.setVitesse(20);
		else this.setVitesse(30);
		super.setPosition(newPosition);
	}
	
	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub

	}

}

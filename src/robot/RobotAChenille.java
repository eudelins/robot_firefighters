package robot;
import carte.*;


public class RobotAChenille extends Robot {

	public RobotAChenille(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
		assert(quantiteEau <= 2000);
	}

	@Override
	public void setPosition(Case newPosition) {
		NatureTerrain nature = newPosition.getNature();
		assert(nature != NatureTerrain.EAU && nature != NatureTerrain.ROCHE);
		if (nature == NatureTerrain.FORET) this.setVitesse(this.getVitesse() / 2);;
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

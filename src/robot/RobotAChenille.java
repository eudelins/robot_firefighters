package robot;
import carte.*;


public class RobotAChenille extends Robot {

	public RobotAChenille(Case position, int quantiteEau, int vitesse) {
		super(position, quantiteEau, vitesse);
		assert(quantiteEau <= 2000);
	}

//	@Override
//	public int getVitesse() {
//		NatureTerrain nature = this.getPosition().getNature();
//		if (nature == NatureTerrain.ROCHE || nature == NatureTerrain.EAU)
//			return 0;
//		else if (nature == NatureTerrain.FORET)
//			return 30;
//		else
//			return 60;
//	}

	@Override
	public void deverserEau(int vol) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remplirReservoir() {
		// TODO Auto-generated method stub

	}

}

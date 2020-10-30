package evenement;

public abstract class Evenement {
	private long date;
	private Evenement suivant;
	private Simulateur simul;
	
	public Evenement(long date, Simulateur simul) {
		this.date = date;
		this.simul = simul;
		this.suivant = null;
	}

	public long getDate() {
		return date;
	}
	
	public Evenement getSuivant() {
		return suivant;
	}

	public Simulateur getSimul() {
		return simul;
	}
	
	public void setSuivant(Evenement e) {
		suivant = e;
	}
	
	public abstract void execute();
}

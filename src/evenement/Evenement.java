package evenement;

public abstract class Evenement {
	private long date;
	private Evenement suivant;
	
	public Evenement(long date) {
		this.date = date;
		this.suivant = null;
	}

	public long getDate() {
		return date;
	}
	
	public Evenement getSuivant() {
		return suivant;
	}
	
	public void setSuivant(Evenement e) {
		suivant = e;
	}
	
	public abstract void execute();
}

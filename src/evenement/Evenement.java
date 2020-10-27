package evenement;

public abstract class Evenement {
	private long date;
	
	public Evenement(long date) {
		// TODO Auto-generated constructor stub
	}

	public long getDate() {
		return date;
	}
	
	public abstract execute();
}

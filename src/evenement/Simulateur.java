package evenement;

public class Simulateur {
	private long dateSimulation;
	private Evenement premierEvent;
	private int nbEvents;
	
	public Simulateur() {
		this.dateSimulation = 0;
		this.premierEvent = null;
		this.nbEvents = 0;
	}

	/* Insère l'évènement e dans la liste des évènements */
	public void ajouteEvenement (Evenement e) {
		if (premierEvent == null) {
			// Cas liste vide
			premierEvent = e;
		} else if (premierEvent.getDate() >= e.getDate()) {
			// Cas insertion en tête
			e.setSuivant(premierEvent);
			premierEvent = e;
		} else if (premierEvent.getSuivant() == null) {
			// Cas un seul élément
			premierEvent.setSuivant(e);
		} else {
			Evenement prec = premierEvent;
			Evenement courant = premierEvent.getSuivant();
			while (courant != null && courant.getDate() < e.getDate()) {
				prec = courant;
				courant = courant.getSuivant();
			}
			e.setSuivant(courant);
			prec.setSuivant(e);
		}
	}
	
	public void incrementeDate() {
		dateSimulation++;
	}
	
	public boolean simulationTerminee() {
		return (premierEvent == null);
	}

	public long getDateSimulation() {
		return dateSimulation;
	}

	public Evenement getPremierEvent() {
		return premierEvent;
	}

	public int getNbEvents() {
		return nbEvents;
	}

	public void setPremierEvent(Evenement premierEvent) {
		this.premierEvent = premierEvent;
	}
}

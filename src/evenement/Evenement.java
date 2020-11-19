package evenement;

/**
 * @author equipe 66
 * Classe abstraite décrivant la structure d'un évènement
 */
public abstract class Evenement {
	private long date;
	private Evenement suivant;
	private Simulateur simul;
	
	/** 
	 * Crée un nouvel évènement
	 * @param date la date à laquelle va commencer l'évènement
	 * @param simul le simulateur dans lequel va être ajouté l'évènement
	 */
	public Evenement(long date, Simulateur simul) {
		this.date = date;
		this.simul = simul;
		this.suivant = null;
	}

	/**
	 * Récupère la date de début de l'évènement
	 * @return Renvoie la date de début de l'évènement
	 */
	public long getDate() {
		return date;
	}
	
	/**
	 * Récupère le prochain évènement, qui est programmé après celui-ci
	 * @return Renvoie le prochain évènement
	 */
	public Evenement getSuivant() {
		return suivant;
	}

	/** 
	 * Récupère le simulateur associé à l'évènement
	 * @return Renvoie le simulateur associé à l'évènement
	 */
	public Simulateur getSimul() {
		return simul;
	}
	
	/** 
	 * Fixe le prochain évènement qui est programmé après celui-ci
	 * @param e prochain évènement
	 */
	public void setSuivant(Evenement e) {
		suivant = e;
	}
	
	/**
	 * Méthode abstraite exécutant l'évènement programmé
	 */
	public abstract void execute();
}

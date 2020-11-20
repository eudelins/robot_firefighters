package carte;

/**
 * Classe qui décrit les différents types de terrain qui existent
 * @author equipe 66
 */
public enum NatureTerrain {
	EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT;
	
	/**
	 * Convertit une chaîne de caractère en un objet de type NatureTerrain
	 * @param str la chaîne à convertir
	 * @return l'objet de type NatureTerrain convertit à partir de la chaîne de caractère
	 */
	public static NatureTerrain stringToNature(String str) {
		switch (str) {
		case "EAU":
			return EAU;
		case "FORET":
			return FORET;
		case "ROCHE":
			return ROCHE;
		case "TERRAIN_LIBRE":
			return TERRAIN_LIBRE;
		case "HABITAT":
			return HABITAT;
		default:
			throw new IllegalArgumentException("Nature du terrain inconnu !");
		}
	}
}

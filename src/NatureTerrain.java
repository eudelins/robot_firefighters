public enum NatureTerrain {
	EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT;
	
	
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

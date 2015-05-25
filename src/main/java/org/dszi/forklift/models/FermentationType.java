package org.dszi.forklift.models;

/**
 *
 * @author RasGrass
 */
public enum FermentationType {

	TOP_FERMENTATION("ale"),
	BOTTOM_FERMENTATION("lager");

	private final String beerType;

	private FermentationType(String beerType) {
		this.beerType = beerType;
	}

	public boolean isLager() {
		return beerType.equals(BOTTOM_FERMENTATION.name());
	}

	public boolean isAle() {
		return beerType.equals(TOP_FERMENTATION.name());
	}
	
	public String getName() {
		return beerType;
	}

}

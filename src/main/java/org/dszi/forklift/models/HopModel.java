package org.dszi.forklift.models;

/**
 *
 * @author RasGrass
 */
public class HopModel implements BeerIngredient{

	private final String name;

	private final String countryOfOrigin;

	public HopModel(final String name, final String countryOfOrigin) {
		this.name = name;
		this.countryOfOrigin = countryOfOrigin;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
}

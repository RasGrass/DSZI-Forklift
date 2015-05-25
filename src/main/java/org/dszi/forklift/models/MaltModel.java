package org.dszi.forklift.models;

/**
 *
 * @author RasGrass
 */
public class MaltModel implements BeerIngredient {

	private final String name;

	private final String maltType;

	public MaltModel(final String name, final String maltType) {
		this.name = name;
		this.maltType = maltType;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getMaltType() {
		return maltType;
	}

}

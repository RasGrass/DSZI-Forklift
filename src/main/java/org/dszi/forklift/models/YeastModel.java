package org.dszi.forklift.models;

/**
 *
 * @author RasGrass
 */
public class YeastModel implements BeerIngredient {

	private final String name;

	private final FermentationType type;

	public YeastModel(final String name, final FermentationType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	public FermentationType getType() {
		return type;
	}

}

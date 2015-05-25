package org.dszi.forklift.models;

import java.util.List;

/**
 *
 * @author RasGrass
 */
public class BeerModel {

	private String name;

	private List<YeastModel> yeasts;

	private List<HopModel> hops;

	private List<MaltModel> malts;

	private List<? extends BeerIngredient> otherIngredients;

	private double alcoholPercentage;

	private double ibu;

	private double extractPercentage;

	private String species;

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	private BeerModel() {

	}

	public List<YeastModel> getYeasts() {
		return yeasts;
	}

	public List<HopModel> getHops() {
		return hops;
	}

	public List<MaltModel> getMalts() {
		return malts;
	}

	public List<? extends BeerIngredient> getOtherIngredients() {
		return otherIngredients;
	}

	public double getAlcoholPercentage() {
		return alcoholPercentage;
	}

	public double getIbu() {
		return ibu;
	}

	public double getExtractPercentage() {
		return extractPercentage;
	}

	public String getName() {
		return name;
	}

	public static class Builder {

		private final BeerModel beer;

		public Builder() {
			beer = new BeerModel();
		}

		public Builder withSetOfHops(List<HopModel> hops) {
			beer.hops = hops;
			return this;
		}

		public Builder withSetOfYeasts(List<YeastModel> yeasts) {
			beer.yeasts = yeasts;
			return this;
		}

		public Builder withSetOfMalts(List<MaltModel> malts) {
			beer.malts = malts;
			return this;
		}

		public Builder withSetOfOtherIngredients(List<? extends BeerIngredient> otherIngredients) {
			beer.otherIngredients = otherIngredients;
			return this;
		}

		public Builder withAlcoholPercentage(double percentage) {
			beer.alcoholPercentage = percentage;
			return this;
		}

		public Builder withIbu(double ibu) {
			beer.ibu = ibu;
			return this;
		}

		public Builder named(String name) {
			beer.name = name;
			return this;
		}

		public Builder withExtractPercentage(double percentage) {
			beer.extractPercentage = percentage;
			return this;
		}

		public BeerModel build() {
			return beer;
		}
	}

	@Override
	public String toString() {
		String mergedMalts, megredHops, mergedYeasts;
		StringBuilder builder = new StringBuilder();
		for (MaltModel malt : malts) {
			builder.append(malt.getMaltType());
			builder.append(", ");
		}
		mergedMalts = builder.toString();
		builder = new StringBuilder();
		for (HopModel hop : hops) {
			builder.append(hop.getCountryOfOrigin());
			builder.append(", ");
		}
		megredHops = builder.toString();
		builder = new StringBuilder();
		for (YeastModel yeast : yeasts) {
			builder.append(yeast.getType().name());
			builder.append(", ");
		}
		mergedYeasts = builder.toString();

		builder = new StringBuilder();
		for (BeerIngredient ingredient : otherIngredients) {
			builder.append(ingredient.getName());
			builder.append(", ");
		}
		String mergedOtherIngredients = builder.toString();

		return "<html>"
				+ "Chmiele: " + megredHops
				+ "<br/>" + "Słody: " + mergedMalts
				+ "<br/>" + " Drożdżde: " + mergedYeasts
				+ "<br/>" + " Inne składniki: " + mergedOtherIngredients
				+ "<br/>" + " IBU: " + ibu
				+ "<br/>" + " Alkohol: " + alcoholPercentage
				+ "<br/>" + " Ekstrakt: " + extractPercentage
				+ "</html>";
	}

}

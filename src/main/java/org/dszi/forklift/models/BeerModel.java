package org.dszi.forklift.models;

import java.util.List;

/**
 *
 * @author RasGrass
 */
public class BeerModel {

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

		public Builder withExtractPercentage(double percentage) {
			beer.extractPercentage = percentage;
			return this;
		}

		public BeerModel build() {
			return beer;
		}
	}
}

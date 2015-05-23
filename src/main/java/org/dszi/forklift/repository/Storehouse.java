package org.dszi.forklift.repository;

import java.util.List;
import java.util.ArrayList;
import org.dszi.forklift.models.BeerModel;
import org.dszi.forklift.models.HopModel;
import org.dszi.forklift.models.MaltModel;
import org.dszi.forklift.models.YeastModel;

public class Storehouse {

	private final List<BeerModel> beers = new ArrayList<>();

	public Storehouse() {
	}

	public String[][] getTooltipMessage() {
		String malts, hops, yeasts;
		StringBuilder builder = new StringBuilder();
		String[][] arr = new String[beers.size()][6];
		for (int i = 0; i < beers.size(); i++) {
			for (BeerModel beer : beers) {
				for (MaltModel malt : beer.getMalts()) {
					builder.append(malt.getMaltType());
					builder.append(",");
				}
				malts = builder.toString();
				builder = new StringBuilder();
				for (HopModel hop : beer.getHops()) {
					builder.append(hop.getCountryOfOrigin());
					builder.append(",");
				}
				hops = builder.toString();
				builder = new StringBuilder();
				for (YeastModel hop : beer.getYeasts()) {
					builder.append(hop.getType().name());
					builder.append(",");
				}
				yeasts = builder.toString();

				arr[i][0] = malts;
				arr[i][1] = hops;
				arr[i][2] = yeasts;
				arr[i][3] = Double.toString(beers.get(i).getAlcoholPercentage());
				arr[i][4] = Double.toString(beers.get(i).getExtractPercentage());
				arr[i][5] = Double.toString(beers.get(i).getExtractPercentage());
			}
		}
		return arr;

	}

	public String[][] _getBeers() {
		String[][] result = new String[beers.size()][2];
		int i = 0;
		for (BeerModel beer : beers) {
			result[i][0] = beer.getName();
			result[i][1] = beer.getSpecies();
			i++;
		}
		return result;
	}

	public void addBeer(BeerModel beer) {
		beers.add(beer);
	}

	public List<BeerModel> getBeers() {
		return beers;
	}
}

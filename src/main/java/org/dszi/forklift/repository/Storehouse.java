package org.dszi.forklift.repository;

import java.util.List;
import java.util.ArrayList;
import org.dszi.forklift.models.BeerModel;

public class Storehouse {

	private final List<BeerModel> beers = new ArrayList<>();

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

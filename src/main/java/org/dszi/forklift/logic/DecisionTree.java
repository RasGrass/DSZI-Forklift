package org.dszi.forklift.logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.dszi.forklift.models.BeerModel;
import org.dszi.forklift.models.BeerSpecies;
import weka.classifiers.trees.J48graft;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public final class DecisionTree {

	private transient DataSource source;

	private transient Instances data;

	private transient final J48graft tree;

	public DecisionTree() {
		tree = new J48graft();
		try {
			source = new DataSource(System.getProperty("user.dir") + "\\res\\data.arff");
			data = source.getDataSet().trainCV(10, 0);
			buildClassifier(data);
		} catch (Exception ex) {
			System.err.println(ex.getMessage() + ex);
		}

	}

	public DataSource getSource() {
		return source;
	}

	public Instances getData() {
		return data;
	}

	public void buildClassifier(final Instances data) throws Exception {
		String[] options = new String[5];
		// unpruned tree
		options[0] = "-L";
		options[1] = "-M 2";
		options[3] = "-U";
		options[2] = "-A";
		options[4] = "-E";

		tree.setOptions(options);

		data.setClassIndex(data.attribute("species").index());
		tree.buildClassifier(data);
	}

	public String recognizeBeerSpecies(BeerModel beer) {
		Instance beerInstance;
		double result = 0;
		try {
			beerInstance = beerModelToInstance(beer);
			beerInstance.setWeight(1.0);
			beerInstance.setDataset(data);
			double[] r = tree.distributionForInstance(beerInstance);
			beerInstance.setClassValue(tree.classifyInstance(beerInstance));
			result = tree.classifyInstance(beerInstance);
			beerInstance.classAttribute().toString();
		} catch (Exception ex) {
			System.err.println(ex.getMessage() + ex);
		}
		return BeerSpecies.BEER_SPECIES[(int) result + 1];
	}

	public Instance beerModelToInstance(BeerModel beer) {

		double[] beerVals = new double[data.numAttributes()];
		beerVals[0] = data.attribute("yeasts/0/type").indexOfValue(beer.getYeasts().get(0).getType().getName());
		beerVals[1] = data.attribute("yeasts/1/type").indexOfValue(beer.getYeasts().get(0).getType().getName());
		beerVals[2] = data.attribute("hops/0/countryOfOrigin").indexOfValue(beer.getHops().get(0).getCountryOfOrigin().toLowerCase());
		beerVals[3] = data.attribute("hops/1/countryOfOrigin").indexOfValue(beer.getHops().get(1).getCountryOfOrigin().toLowerCase());
		beerVals[4] = data.attribute("malts/0/maltType").indexOfValue(beer.getMalts().get(0).getMaltType().toLowerCase().split(" ")[0]);
		beerVals[5] = data.attribute("malts/1/maltType").indexOfValue(beer.getMalts().get(1).getMaltType().toLowerCase().split(" ")[0]);
		beerVals[7] = beer.getAlcoholPercentage();
		beerVals[8] = beer.getIbu();
		beerVals[9] = beer.getExtractPercentage();
		beerVals[10] = data.attribute("malts/2/maltType").addStringValue(beer.getMalts().get(2).getMaltType().toLowerCase().split(" ")[0]);
		beerVals[11] = data.attribute("hops/2/countryOfOrigin").addStringValue(beer.getHops().get(2).getCountryOfOrigin().toLowerCase());
		beerVals[12] = data.attribute("otherIngredients/0/name").addStringValue(beer.getOtherIngredients().get(0).getName());
		beerVals[13] = data.attribute("otherIngredients/1/name").addStringValue(beer.getOtherIngredients().get(1).getName());

		Instance instance = new Instance(1.0, beerVals);
		return instance;
	}

}

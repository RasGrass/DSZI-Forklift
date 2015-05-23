package org.dszi.forklift.logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.dszi.forklift.models.BeerModel;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DecisionTree {

	private transient DataSource source;

	private transient Instances data;

	private transient final J48 tree;

	public DecisionTree(final String filePath) {
		tree = new J48();
		try {
			source = new DataSource(filePath);
			data = source.getDataSet();
		} catch (Exception ex) {
			Logger.getLogger(DecisionTree.class.getName()).log(Level.SEVERE, null, ex);
		}
		filterAttributes(data, "name");
	}

	public DataSource getSource() {
		return source;
	}

	public Instances getData() {
		return data;
	}

	public void buildClassifier(final Instances data) throws Exception {
		String[] options = new String[1];
		// unpruned tree
		options[0] = "-U";
		tree.setOptions(options);
		tree.buildClassifier(data);
	}
	
	public Instance beerModelToInstance(BeerModel beer) {
		//TODO
		return null;
	}

	private void filterAttributes(final Instances data, final String nameContains) {
		for (int i = 0; i < data.numAttributes(); i++) {
			if (data.attribute(i).name().contains(nameContains)) {
				data.deleteAttributeAt(i);
			}
		}
	}

}

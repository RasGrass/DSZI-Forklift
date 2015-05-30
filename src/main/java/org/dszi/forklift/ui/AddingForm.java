package org.dszi.forklift.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.StringUtils;
import org.dszi.forklift.Forklift;
import org.dszi.forklift.logic.DecisionTree;
import org.dszi.forklift.models.BeerIngredient;
import org.dszi.forklift.models.BeerModel;
import org.dszi.forklift.models.FermentationType;
import org.dszi.forklift.models.GenericBeerIngredient;
import org.dszi.forklift.models.HopModel;
import org.dszi.forklift.models.MaltModel;
import org.dszi.forklift.repository.Storehouse;
import org.dszi.forklift.models.YeastModel;
import org.dszi.forklift.utils.SpringUtilities;

public class AddingForm extends JPanel {

	private final Dimension panelSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final String[] labels;
	private final int numPairs;
	private final JPanel panel;
	private final JComboBox[] comboBoxes;
	private final JTextField[] textFields;

	private final JButton send;
	private final JButton cancel;
	private final String[] hops;
	private final String[] malts;

	private final Storehouse storehouse;
	private final String[] yeasts;
	private final String[] listLabels;

	private final JTextField beerName;

	private final int numberOfMalts = 3;
	private final int numberOfHops = 3;
	private final int numberOfYeats = 2;
	private final String[] maltsEn;
	
	private final DecisionTree decisionTree;

	public AddingForm() {
		this.malts = new String[]{"Brak", "Pale Ale", "Palony", "Pilzneński", "Karmelowy", "Pszeniczny"};
		this.maltsEn = new String[]{"biscuit", "toffee", "wheat", "spices", "floral", "caramel", "spicy", "rye", "pilsner", "barley", "roasted", "pale ale", "munich", "citrus", "piceous", "clove", "vienna", "oat"};
		
		this.hops = new String[]{"english","usa","german","austria","munich","czech","polish","spicy","noble"};
		this.yeasts = new String[]{"ale","lager"};
		this.decisionTree=Forklift.getInjector().getInstance(DecisionTree.class);
		this.cancel = new JButton("Anuluj");
		this.send = new JButton("Dodaj");
		this.panel = new JPanel(new SpringLayout());
		this.comboBoxes = new JComboBox[8];
		this.listLabels = new String[]{"Chmiel", "Słód", "Drożdże"};
		this.labels = new String[]{"Inny składnik 1", "Inny składnik 2", "IBU", "Alkohol", "Ekstrakt"};
		this.numPairs = labels.length;
		this.textFields = new JTextField[labels.length];
		this.storehouse = Forklift.getInjector().getInstance(Storehouse.class);
		this.beerName = new JTextField();
		initPanel();
		initComponents();
		setUpComponents();
		setUpMouseListeners();
		setUpActionListeners();
	}

	private BeerModel addObjectUsingForm() throws NumberFormatException {
		int ibu;
		double alcohol;
		double extract;
		try {
			ibu = Integer.parseInt(textFields[2].getText());
		} catch (NumberFormatException e) {
			textFields[2].setForeground(Color.red);
			textFields[2].setText("Must be a number");
			return null;

		}
		try {
			alcohol = Double.parseDouble(textFields[3].getText());

		} catch (NumberFormatException e) {
			textFields[3].setForeground(Color.red);
			textFields[3].setText("Must be a number");

			return null;
		}
		try {
			extract = Double.parseDouble(textFields[4].getText());

		} catch (NumberFormatException e) {
			textFields[4].setForeground(Color.red);
			textFields[4].setText("Must be a number");

			return null;
		}

		if (StringUtils.isBlank(beerName.getText())) {
			beerName.setForeground(Color.red);
			beerName.setText("Must not be empty");
			return null;
		}

		List<HopModel> selectedHops = new ArrayList();
		List<MaltModel> selectedMalts = new ArrayList();
		List<YeastModel> selectedYeasts = new ArrayList();
		List<BeerIngredient> otherSelectedingredients = new ArrayList();

		for (int i = 0; i < numberOfHops; i++) {
			selectedHops.add(new HopModel("", (String) comboBoxes[i].getSelectedItem()));
		}

		for (int i = numberOfHops; i < numberOfHops + numberOfMalts; i++) {
			selectedMalts.add(new MaltModel("", (String) comboBoxes[i].getSelectedItem()));
		}

		for (int i = numberOfHops + numberOfMalts; i < numberOfHops + numberOfMalts + numberOfYeats; i++) {
			if (comboBoxes[i].getSelectedItem().equals("lager")) {
				selectedYeasts.add(new YeastModel("", FermentationType.BOTTOM_FERMENTATION));
			} else if (comboBoxes[i].getSelectedItem().equals("ale")) {
				selectedYeasts.add(new YeastModel("", FermentationType.TOP_FERMENTATION));
			}
		}
		otherSelectedingredients.add(new GenericBeerIngredient(textFields[0].getText()));
		otherSelectedingredients.add(new GenericBeerIngredient(textFields[1].getText()));

		BeerModel beer = new BeerModel.Builder().
				withSetOfHops(selectedHops).
				withSetOfMalts(selectedMalts).
				withSetOfYeasts(selectedYeasts).
				withSetOfOtherIngredients(otherSelectedingredients).
				withAlcoholPercentage(alcohol).
				withIbu(ibu).
				named(beerName.getText()).
				withExtractPercentage(extract).
				build();

		return beer;

	}

	private void initPanel() {
		setPreferredSize(panelSize);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);
	}

	private void initComponents() {

		JLabel beerNameTextFieldLabel = new JLabel("Nazwa", JLabel.TRAILING);
		beerNameTextFieldLabel.setLabelFor(beerName);
		panel.add(beerNameTextFieldLabel);
		panel.add(beerName);

		int j = 1;

		for (int i = 0; i < listLabels.length; i++) {
			JLabel l = new JLabel(listLabels[0] + " " + j, JLabel.TRAILING);
			panel.add(l);
			comboBoxes[i] = new JComboBox();
			l.setLabelFor(comboBoxes[i]);
			panel.add(comboBoxes[i]);
			j++;
		}
		j = 1;

		for (int i = numberOfHops; i < numberOfHops + numberOfMalts; i++) {

			JLabel l = new JLabel(listLabels[1] + " " + j, JLabel.TRAILING);
			panel.add(l);
			comboBoxes[i] = new JComboBox();
			l.setLabelFor(comboBoxes[i]);
			panel.add(comboBoxes[i]);
			j++;

		}
		j = 1;

		for (int i = numberOfHops + numberOfMalts; i < numberOfHops + numberOfMalts + numberOfYeats; i++) {
			JLabel l = new JLabel(listLabels[2] + " " + j, JLabel.TRAILING);
			panel.add(l);
			comboBoxes[i] = new JComboBox();
			l.setLabelFor(comboBoxes[i]);
			panel.add(comboBoxes[i]);
			j++;
		}

		for (int c = 0; c < numPairs; c++) {
			JLabel l = new JLabel(labels[c], JLabel.TRAILING);
			panel.add(l);
			textFields[c] = new JTextField();
			l.setLabelFor(textFields[c]);
			panel.add(textFields[c]);
		}

	}

	private void setUpComponents() {
		int comboBoxesCounter = 0;
		String[] items;
		for (JComboBox comboBox : comboBoxes) {
			if (comboBoxesCounter < numberOfHops) {
				items = hops;
			} else if (comboBoxesCounter >= numberOfHops && comboBoxesCounter < numberOfHops + numberOfMalts) {
				items = maltsEn;
			} else {
				items = yeasts;
			}
			for (String item : items) {
				comboBox.addItem(item);
			}
			comboBoxesCounter++;
		}

		panel.add(send);
		panel.add(cancel);
		panel.setBackground(Color.LIGHT_GRAY);

		SpringUtilities.makeCompactGrid(panel,
				15, 2, //rows, cols
				0, 50, //initX, initY
				150, 10); //xPad, yPad
		JLabel title = new JLabel("Dodawanie obiektu");
		title.setFont(new Font("Serif", Font.PLAIN, 24));
		panel.add(title);
		add(panel);
	}

	private void setUpMouseListeners() {
		comboBoxes[0].addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Runnable doRun = new Runnable() {
					@Override
					public void run() {
						comboBoxes[0].getEditor().setItem(0);
						comboBoxes[0].getEditor().selectAll();
						comboBoxes[0].getEditor().getEditorComponent().requestFocus();
					}
				};
				SwingUtilities.invokeLater(doRun);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				comboBoxes[0].getEditor().selectAll();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	private void setUpActionListeners() {
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BeerModel beer = addObjectUsingForm();
				if (beer != null) {
					beer.setSpecies(decisionTree.recognizeBeerSpecies(beer));
					storehouse.addBeer(beer);
					textFields[2].setForeground(Color.black);
					textFields[3].setForeground(Color.black);
					textFields[4].setForeground(Color.black);
					textFields[2].setText("");
					textFields[3].setText("");
					textFields[4].setText("");
					beerName.setForeground(Color.black);
					beerName.setText("");
				}
			}
		});

	}
}

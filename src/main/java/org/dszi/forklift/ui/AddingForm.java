package org.dszi.forklift.ui;

import com.google.inject.Inject;
import org.dszi.forklift.models.Item;
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
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import org.dszi.forklift.models.Storehouse;
import org.dszi.forklift.utils.SpringUtilities;
//import javax.swing.SpringUtilities;

public class AddingForm extends JPanel {

	private final Dimension panelSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final List<String> savedNames;
	private final String randomChoice = " < Losowo > ";
	private final String[] labels;
	private final int numPairs;
	private final JPanel p;
	private final JComboBox[] comboBoxes;
	private final JButton send;
	private final JButton cancel;
	private final String[] colors;
	private final String[] types;
	private final Random rand;

	@Inject
	private Storehouse storehouse;

	public AddingForm() {
		this.rand = new Random();
		this.types = new String[]{"Prostokąt", "Kwadrat", "Koło", "Trójkąt", "Gwiazda"};
		this.colors = new String[]{"czerwony", "zielony", "niebieski", "czarny", "żółty"};
		this.cancel = new JButton("Anuluj");
		this.send = new JButton("Dodaj");
		this.p = new JPanel(new SpringLayout());
		this.comboBoxes = new JComboBox[4];
		this.labels = new String[]{"Nazwa: ", "Ciężar: ", "Kolor: ", "Typ: "};
		this.numPairs = labels.length;
		this.savedNames = new ArrayList<>();
		initPanel();
		initComponents();
		setUpComponents();
		setUpMouseListeners();
		setUpActionListeners();

	}

	private boolean isNameDistinct() {
		boolean isDistinct = true;
		if (!savedNames.isEmpty()) {
			for (String savedName : savedNames) {
				if (savedName.equals(comboBoxes[0].getSelectedItem())) {
					isDistinct = false;
				}
			}
		}
		return isDistinct;
	}

	private void addName(boolean isDistinct) {
		if ((!comboBoxes[0].getSelectedItem().toString().equals(randomChoice)) && isDistinct) {
			savedNames.add(comboBoxes[0].getSelectedItem().toString());
		}
	}

	private void addObjectUsingForm() throws NumberFormatException {
		boolean randomColor = comboBoxes[2].getSelectedItem().equals(randomChoice);
		boolean randomWeight = comboBoxes[1].getSelectedItem().equals(randomChoice);
		boolean randomType = comboBoxes[3].getSelectedItem().equals(randomChoice);
		boolean randomName = comboBoxes[0].getSelectedItem().equals(randomChoice);

		Item obj = new Item(
				randomName ? "Obiekt" + rand.nextInt(200) : comboBoxes[0].getSelectedItem().toString(),
				randomWeight ? randomizeWeight() : Double.parseDouble(comboBoxes[1].getSelectedItem().toString()),
				randomColor ? randomizeColor() : parseColor(comboBoxes[2].getSelectedItem().toString()),
				randomType ? randomizeType() : comboBoxes[3].getSelectedItem().toString());

		storehouse.addObjectAnywhere(obj);
		System.out.println(obj.getShelfNumber());
		System.out.println(obj.getRackNumber());
	}

	private void refreshNameList() {
		if (!savedNames.isEmpty()) {
			comboBoxes[0].removeAllItems();
			comboBoxes[0].addItem(randomChoice);
			for (String savedName : savedNames) {
				comboBoxes[0].addItem(savedName);
			}
		}
	}

	private void initPanel() {
		int temp;
		temp = (int) (panelSize.width / 1.5);
		panelSize.width = panelSize.width - temp;
		setPreferredSize(panelSize);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);
	}

	private void initComponents() {
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			p.add(l);
			comboBoxes[i] = new JComboBox();
			l.setLabelFor(comboBoxes[i]);
			p.add(comboBoxes[i]);
		}
	}

	private void setUpComponents() {
		if (!savedNames.isEmpty()) {
			comboBoxes[0].removeAllItems();
			comboBoxes[0].addItem(randomChoice);
			for (String savedName : savedNames) {
				comboBoxes[0].addItem(savedName);
			}
		} else {
			comboBoxes[0].addItem(randomChoice);

		}

		comboBoxes[0].setEditable(true);

		comboBoxes[1].addItem(randomChoice);
		comboBoxes[1].addItem("0.5");
		comboBoxes[1].addItem("1");
		comboBoxes[1].addItem("2");
		comboBoxes[1].addItem("3");
		comboBoxes[1].addItem("4");
		comboBoxes[1].addItem("5");
		comboBoxes[1].setEditable(true);

		comboBoxes[2].addItem(randomChoice);
		comboBoxes[2].addItem("Zielony");
		comboBoxes[2].addItem("Czerwony");
		comboBoxes[2].addItem("Niebieski");
		comboBoxes[2].addItem("Żółty");
		comboBoxes[2].addItem("Czarny");

		comboBoxes[3].addItem(randomChoice);
		comboBoxes[3].addItem("Gwiazdka");
		comboBoxes[3].addItem("Kółko");
		comboBoxes[3].addItem("Kwadrat");
		comboBoxes[3].addItem("Trójkąt");
		comboBoxes[3].addItem("Prostokąt");

		p.add(send);
		p.add(cancel);
		p.setBackground(Color.LIGHT_GRAY);

		SpringUtilities.makeCompactGrid(p,
				numPairs + 1, 2, //rows, cols
				50, 80, //initX, initY
				60, 35); //xPad, yPad
		JLabel title = new JLabel("Dodawanie obiektu");
		title.setFont(new Font("Serif", Font.PLAIN, 24));
		p.add(title);
		add(p);
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
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		});
	}

	private void setUpActionListeners() {
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isDistinct = isNameDistinct();
				addName(isDistinct);
				addObjectUsingForm();
				refreshNameList();
				CommandlinePanel.addInfo("Pomyślnie dodano obiekt");

			}
		});
	}

	private String randomizeColor() {
		int choice = rand.nextInt(5);
		return colors[choice];
	}

	private Double randomizeWeight() {
		double choice = rand.nextInt(20);
		return (double) choice;

	}

	private String randomizeType() {
		int choice = rand.nextInt(5);
		return types[choice];
	}

	private String parseColor(String colorInPolish) {
		switch (colorInPolish) {
			case "Zielony":
				return "green";
			case "Niebieski":
				return "blue";
			case "Czerwony":
				return "red";
			case "Czarny":
				return "black";
			case "Żółty":
				return "yellow";
			default:
				return "black";
		}

	}
}

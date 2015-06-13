package org.dszi.forklift.models;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import org.dszi.forklift.Forklift;

/**
 *
 * @author RasGrass
 */
public class Rack extends JPanel {

	private Shelf[] shelfs = new Shelf[3];
	private static int counter = 0;
	private final int rackNumber;
	public static final double RACK_HEIGHT = (Toolkit.getDefaultToolkit().getScreenSize().height) * 0.875;
	public static final double RACK_WIDTH = (Toolkit.getDefaultToolkit().getScreenSize().width) / 30;
	public static final double START_POS = (Toolkit.getDefaultToolkit().getScreenSize().width) / 10;
	public static final double SPACER = (Toolkit.getDefaultToolkit().getScreenSize().width) / 2;

	private final Grid grid;
	
	
	public Rack() {
		super();
		grid=Forklift.getInjector().getInstance(Grid.class);
		//setLayout(new BorderLayout());
		rackNumber = counter++;
		for (int i = 0; i < 3; i++) {
			shelfs[i] = new Shelf();
			add(shelfs[i]);
		}
		setPreferredSize(new Dimension((int) RACK_WIDTH, (int) RACK_HEIGHT));
		setLocation(80 * rackNumber + 1, 10);
		setLayout(new GridLayout());
	}

	public Shelf getShelf(int shelfNumber) {
		return shelfs[shelfNumber];
	}

	@Override
	public String toString() {
		String command;

		command = "******************************" + " Rack " + rackNumber + " ******************************" + System.lineSeparator();
		for (int j = 0; j < 5; j++) {
			command += "***************Shelf " + j + "*****************" + System.lineSeparator();
			command += getShelf(j).toString();
		}

		return command;
	}

	@Override
	public void paintComponent(Graphics g) {
		setBounds((int) START_POS * rackNumber + (int) SPACER, 40, (int) RACK_WIDTH, (int) RACK_HEIGHT);
	}
}

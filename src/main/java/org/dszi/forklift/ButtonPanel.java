package org.dszi.forklift;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author RasGrass
 */
public class ButtonPanel extends JPanel {
	//private TextPanel rightTextPanel = new TextPanel();

	public JButton dodajObiekt = new JButton("Dodaj obiekt");
	public JButton stanMagazynu = new JButton("Stan Magazynu");
	public JButton Pomoc = new JButton("Pomoc");

	ButtonPanel() {
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder("Opcje programu"));

		add(dodajObiekt);
		add(stanMagazynu);
		add(Pomoc);

	}
}

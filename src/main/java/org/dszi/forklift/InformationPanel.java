package org.dszi.forklift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 *
 * @author RasGrass
 */
public class InformationPanel extends JTextArea {

	protected Dimension panelSize = Toolkit.getDefaultToolkit().getScreenSize();

	InformationPanel() {
		super();
		int temp;
		temp = (int) (panelSize.width / 1.5);
		panelSize.width = panelSize.width - temp;
		setEditable(false);
		setPreferredSize(panelSize);
		setLineWrap(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.LIGHT_GRAY);

	}
}

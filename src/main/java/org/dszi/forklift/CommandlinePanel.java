package org.dszi.forklift;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.dszi.forklift.analiza.NaturalLanguage;

/**
 *
 * @author RasGrass
 */
public class CommandlinePanel extends JPanel {

	private String command = new String();
	private JTextField commandLine = new JTextField("Wpisz polecenie");
	private static JTextArea commandArea = new JTextArea();
	private JScrollPane CommandLineScrollField = new JScrollPane(commandArea);
	private Dimension panelSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int mouseClicksCount = 0;

	CommandlinePanel() {
		super();
		int temp;
		setLayout(new BorderLayout());
		temp = (int) (panelSize.height * 0.7);
		panelSize.height = panelSize.height - temp;
		setPreferredSize(panelSize);
		setBackground(Color.LIGHT_GRAY);
		commandArea.setEditable(false);
		commandLine.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
		commandLine.setBackground(Color.LIGHT_GRAY);
		commandLine.setEditable(true);
		commandArea.setBackground(Color.LIGHT_GRAY);
		commandArea.setLineWrap(true);
		add(CommandLineScrollField, BorderLayout.CENTER);
		commandLine.addActionListener(panelAction);
		commandLine.setEnabled(true);
		commandLine.selectAll();
		commandArea.setAutoscrolls(true);
		add(commandLine, BorderLayout.SOUTH);

		commandLine.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClicksCount++;
				if (mouseClicksCount == 1) {
					commandLine.setText("");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				//throw new UnsupportedOperationException("Not supported yet.");
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

		commandLine.registerKeyboardAction(panelAction, "ENTER", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);

	}

	public static void addInfo(String info) {
		Font font = commandArea.getFont();
		commandArea.setFont(font.deriveFont(Font.TRUETYPE_FONT));
		commandArea.append(info + System.lineSeparator());
		//commandArea.setFont(font.deriveFont(Font.PLAIN));
		try {
			Forklift.playSound();
		} catch (MalformedURLException ex) {
			Logger.getLogger(CommandlinePanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void processAction() {
		if (!(commandLine.getText().equals(""))) {
			command = commandLine.getText();
			commandArea.append(command + System.lineSeparator() + NaturalLanguage.interpret(command) + System.lineSeparator());
			System.out.println(command);
			commandLine.setText("");

		}
	}
	ActionListener panelAction = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent event) {
			if ("ENTER".equals(event.getActionCommand())) {
				processAction();
			}
		}
	};
}

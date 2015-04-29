package org.dszi.forklift;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.dszi.forklift.logic.Task;
import org.dszi.forklift.logic.GameLogic;
import org.dszi.forklift.ui.ItemListPanel;
import org.dszi.forklift.ui.AddingForm;
import org.dszi.forklift.ui.CommandlinePanel;
import org.dszi.forklift.ui.InformationPanel;
import org.dszi.forklift.ui.ButtonPanel;
import org.dszi.forklift.ui.TextPanel;
import org.dszi.forklift.models.Trash;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Storehouse;
import org.dszi.forklift.models.Item;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;

public class Forklift extends Canvas {

	private final JPanel panel;
	private boolean isRunning = true;
	private final Storehouse magazyn;
	private final ButtonPanel buttonPanel;
	private InformationPanel rightPanel;
	private final TextPanel rightTextPanel;
	private final CommandlinePanel CommandField;
	private final JPanel drawingPane;
	private final RepaintManager myRepaintManager;
	private final GameLogic gameLogic = new GameLogic();
	private final ActionListener panelAction;
	private JFrame frame;
	private final Cart forklift;
	private final Trash trash;
	private static Injector injector;

	public Cart getCart() {
		return forklift;
	}

	public Trash getTrash() {
		return trash;
	}

	public static Injector getInjector() {
		return injector;
	}

	public Forklift() {

		this.injector = Guice.createInjector(new RepositoryModule());
		this.magazyn = injector.getInstance(Storehouse.class);
		this.trash = injector.getInstance(Trash.class);
		this.forklift = injector.getInstance(Cart.class);
		this.drawingPane = injector.getInstance(JPanel.class);
		this.frame = injector.getInstance(JFrame.class);

		this.panelAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				if ("ESC".equals(event.getActionCommand())) {
					isRunning = false;
					System.exit(0);
				}
				if ("ENTER".equals(event.getActionCommand())) {
				}
			}
		};
		this.CommandField = new CommandlinePanel();
		this.rightTextPanel = new TextPanel();
		this.buttonPanel = new ButtonPanel();
		frame = new JFrame("Wozek Widlowy");
		panel = new JPanel();
		frame.setContentPane(panel);
		frame.setLayout(new BorderLayout());
		frame.setUndecorated(true);
		frame.setResizable(false);
		panel.setBackground(Color.GRAY);
		panel.registerKeyboardAction(
				panelAction,
				"ESC",
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		fillRightPanel();
		panel.add(rightPanel, BorderLayout.EAST);
		panel.add(drawingPane);
		CommandField.requestFocus();
		panel.setFocusable(true);
		fillDrawingPane();
		initFullscreen(frame);
		initButtonPanel(buttonPanel);
		myRepaintManager = new RepaintManager();
		RepaintManager.setCurrentManager(myRepaintManager);

		//GameLogic.addTask(Task.ACTION_TYPE_ADD, new Item("Example", 12.0, "bialy", "kwadrat"));
		//GameLogic.addTask(Task.ACTION_TYPE_DELETE, Storehouse.getObjects().get(2));
		//GameLogic.addTask(Task.ACTION_TYPE_ADDANYWHERE, new Item("Example",12.0,"bialy","kwadrat"));
//		GameLogic.addTask(Task.ACTION_TYPE_REPLACE, magazyn.getObjects().get(1), magazyn.getObjects().get(3));

		//GameLogic.addTask(Task.ACTION_TYPE_MOVE, Storehouse.getObjects().get(2));
	}

	public Storehouse getStorehouse() {
		return magazyn;
	}


	public Forklift getForklift() {
		return this;
	}

	public void loop() throws InterruptedException {
		while (isRunning) {
			gameLogic.processLogic();
			RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
			RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Forklift wozek = new Forklift();
		Thread.sleep(2000);
		wozek.loop();
	}

	private void initObjects() {
		Item exampleObject = new Item("Słoik", 12.0, "Żółty", "Kwadrat");
		Item exampleObject1 = new Item("Skrzynia", 12.0, "Niebieski", "Prostokąt");
		Item exampleObject2 = new Item("Krzesło", 9.0, "Zielony", "Koło");
		Item exampleObject3 = new Item("Opona", 3.0, "Czerwony", "Koło");

		/*magazyn.addObjectSpecifically(exampleObject, 0, 0);
		magazyn.addObjectSpecifically(exampleObject1, 0, 0);
		magazyn.addObjectSpecifically(exampleObject2, 1, 4);
		magazyn.addObjectSpecifically(exampleObject3, 2, 2);*/

//		System.out.println(magazyn.getRacks()[0].getShelf(0).getBoxes()[exampleObject1.getPlace()].getBoxXY());
	}

	private void fillDrawingPane() {
		drawingPane.setBackground(Color.LIGHT_GRAY);
		drawingPane.add(trash);
		drawingPane.add(forklift);
		/*drawingPane.add(Storehouse.racks[0]);
		drawingPane.add(Storehouse.racks[1]);
		drawingPane.add(Storehouse.racks[2]);
		drawingPane.add(Storehouse.racks[3]);
		drawingPane.add(Storehouse.racks[4]);
		drawingPane.add(Storehouse.racks[5]);*/
		//drawingPane.add(Storehouse.racks[6]);
		initObjects();
		frame.setVisible(true);
//		System.out.println(magazyn.getRacks()[0].getShelf(0).getBoxes()[1].getBoxXY());
		// System.out.println(
		// magazyn.getRacks()[1].getShelf(4).getBoxes()[exampleObject2.getPlace()].getLocationOnScreen()    );
	}

	private void initFullscreen(JFrame myFrame) throws HeadlessException {
		GraphicsDevice graphicsDevice;
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		graphicsDevice.setFullScreenWindow(myFrame);
	}

	private void initButtonPanel(final ButtonPanel buttonPanel) {
		buttonPanel.setLayout(new GridLayout(1, 3, (int) (buttonPanel.getWidth() * 0.15), 0));

		buttonPanel.dodajObiekt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(buttonPanel, BorderLayout.NORTH);
				rightPanel.add(CommandField, BorderLayout.SOUTH);
				rightPanel.add(new AddingForm(), BorderLayout.CENTER);
				rightPanel.revalidate();
			}
		});

		buttonPanel.Pomoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(buttonPanel, BorderLayout.NORTH);
				rightPanel.add(CommandField, BorderLayout.SOUTH);
				rightPanel.add(new TextPanel(), BorderLayout.CENTER);
				rightPanel.revalidate();

			}
		});

		buttonPanel.stanMagazynu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				ItemListPanel objcts = injector.getInstance(ItemListPanel.class);
				rightPanel.add(buttonPanel, BorderLayout.NORTH);
				rightPanel.add(CommandField, BorderLayout.SOUTH);
				rightPanel.add(objcts, BorderLayout.CENTER);
				rightPanel.revalidate();
			}
		});
	}

	private void fillRightPanel() {
		rightPanel = new InformationPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(buttonPanel, BorderLayout.NORTH);
		rightPanel.add(rightTextPanel, BorderLayout.CENTER);
		rightPanel.add(CommandField, BorderLayout.SOUTH);
	}

	public static synchronized void playSound() throws MalformedURLException {
		new Thread(new Runnable() {
			URL url1 = new URL("/res/knob.wav");
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.

			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(url1);

					clip.open(inputStream);
					//clip.
					clip.start();
				} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}

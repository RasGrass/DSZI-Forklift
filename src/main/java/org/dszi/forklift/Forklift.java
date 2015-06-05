package org.dszi.forklift;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.dszi.forklift.logic.GameLogic;
import org.dszi.forklift.ui.InformationPanel;
import org.dszi.forklift.ui.ButtonPanel;
import org.dszi.forklift.ui.TextPanel;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.repository.Storehouse;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.RepaintManager;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.models.GridItem;
import org.dszi.forklift.models.Rack;
import org.dszi.forklift.ui.AddingForm;
import org.dszi.forklift.ui.ItemListPanel;

public class Forklift extends Canvas {

	private JPanel panel;
	private boolean isRunning = true;
	private final Storehouse magazyn;
	private ButtonPanel buttonPanel;
	private InformationPanel rightPanel;
	private TextPanel rightTextPanel;
	private final JPanel drawingPane;
	private RepaintManager myRepaintManager;
	private final GameLogic gameLogic;
	private ActionListener panelAction;
	private JFrame frame;
	private final Cart forklift;
	private final Grid grid;
	private static Injector injector;
	private final Rack[] racks = new Rack[7];

	public Cart getCart() {
		return forklift;
	}

	public static Injector getInjector() {
		return injector;
	}

	public Forklift() {

		Forklift.injector = Guice.createInjector(new RepositoryModule());
		this.magazyn = injector.getInstance(Storehouse.class);
		this.forklift = injector.getInstance(Cart.class);
		this.drawingPane = injector.getInstance(JPanel.class);
		this.frame = injector.getInstance(JFrame.class);
		this.grid = injector.getInstance(Grid.class);
		this.gameLogic = injector.getInstance(GameLogic.class);
		setupComponents();

	}

	private void setupComponents() {
		
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

		this.rightTextPanel = new TextPanel();
		this.buttonPanel = new ButtonPanel();
		frame = new JFrame("Inteligentny Wozek Widlowy");
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
		panel.setFocusable(true);
		initFullscreen(frame);
		initButtonPanel(buttonPanel);
		fillDrawingPane();

		myRepaintManager = new RepaintManager();
		RepaintManager.setCurrentManager(myRepaintManager);
	}

	public Storehouse getStorehouse() {
		return magazyn;
	}

	public Grid getGrid() {
		return grid;
	}

	public Forklift getForklift() {
		return this;
	}

	public void loop() {            
                grid.SetLayoutResolution(drawingPane.getWidth(), drawingPane.getHeight());
		SetRacks();
                
		gameLogic.MoveToPoint(new Point(0, 0), new Point(8, 8));
		while (isRunning) {
			gameLogic.processLogic();
			RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		}
	}
        
        private void SetRacks()
        {
            FlowLayout flow = new FlowLayout();
            
            for (int i = 0; i < 1; i++) {
			racks[i] = new Rack();
			racks[i].setLayout(flow);                        
                        drawingPane.add(racks[i]);
                        grid.SetObject(new GridItem(),60 + racks[i].getX(),60 +  racks[i].getY());
		}      		            
        }

	public static void main(String[] args) throws InterruptedException {
		Forklift wozek = new Forklift();
		Thread.sleep(2000);
		wozek.loop();
	}


	private void fillDrawingPane() {
		drawingPane.setBackground(Color.LIGHT_GRAY);
		frame.setVisible(true);
                drawingPane.add(forklift);
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
				rightPanel.add(new AddingForm(), BorderLayout.CENTER);
				rightPanel.revalidate();
			}
		});

		buttonPanel.Pomoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				rightPanel.add(buttonPanel, BorderLayout.NORTH);
				rightPanel.add(new TextPanel(), BorderLayout.CENTER);
				rightPanel.revalidate();

			}
		});

		buttonPanel.stanMagazynu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.removeAll();
				ItemListPanel objcts = new ItemListPanel();
				injector.injectMembers(objcts);
				rightPanel.add(buttonPanel, BorderLayout.NORTH);
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
	}

}

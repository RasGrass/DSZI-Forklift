package org.dszi.forklift;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.dszi.forklift.logic.GameLogic;
import org.dszi.forklift.ui.InformationPanel;
import org.dszi.forklift.ui.ButtonPanel;
import org.dszi.forklift.ui.TextPanel;
import org.dszi.forklift.models.Cart;
import org.dszi.forklift.models.Storehouse;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
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
import org.dszi.forklift.logic.TreeState;
import org.dszi.forklift.models.ActionTypes;
import org.dszi.forklift.models.Grid;
import org.dszi.forklift.models.GridItem;
import org.dszi.forklift.models.MoveActionTypes;
import org.dszi.forklift.models.TreeItem;
import org.dszi.forklift.ui.AddingForm;
import org.dszi.forklift.ui.ItemListPanel;

public class Forklift extends Canvas {

	private JPanel panel;
	private boolean isRunning = true;
	private final Storehouse magazyn;
	private ButtonPanel buttonPanel;
	private InformationPanel rightPanel;
	private TextPanel rightTextPanel;
	private JPanel drawingPane;
	private  RepaintManager myRepaintManager;
	private GameLogic gameLogic;
	private  ActionListener panelAction;
	private JFrame frame;
	private final Cart forklift;
        private final Grid grid;
	//private final Trash trash;
	private static Injector injector;

	public Cart getCart() {
		return forklift;
	}

	/*public Trash getTrash() {
		return trash;
	}*/
        
        

	public static Injector getInjector() {
		return injector;
	}

	public Forklift() {

		this.injector = Guice.createInjector(new RepositoryModule());
		this.magazyn = injector.getInstance(Storehouse.class);
		//this.trash = injector.getInstance(Trash.class);
		this.forklift = injector.getInstance(Cart.class);
		this.drawingPane = injector.getInstance(JPanel.class);
		this.frame = injector.getInstance(JFrame.class);
                this.grid = new Grid();
                this.gameLogic = injector.getInstance(GameLogic.class);
		setupComponents();
	}
        
        private void setupComponents()
        {
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
		fillDrawingPane();
		initFullscreen(frame);
		initButtonPanel(buttonPanel);
		myRepaintManager = new RepaintManager();
		RepaintManager.setCurrentManager(myRepaintManager);
        }

	public Storehouse getStorehouse() {
		return magazyn;
	}

        public Grid getGrid(){
            return grid;
        }

	public Forklift getForklift() {
		return this;
	}

	public void loop() throws InterruptedException {
            TreeState ts = new TreeState();
            grid.SetObject(new GridItem(), 3, 3);
             grid.SetObject(new GridItem(), 2, 3);
              grid.SetObject(new GridItem(), 1, 3);
            for(MoveActionTypes action : ts.treesearch(grid,  new TreeItem(new Point(0,0), MoveActionTypes.RIGHT), new Point(3,4)))
            {
                gameLogic.AddTask(ActionTypes.ACTION_TYPE_MOVE_CARD, action);
            }
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

	private void fillDrawingPane() {
		drawingPane.setBackground(Color.LIGHT_GRAY);
		//drawingPane.add(trash);
		drawingPane.add(forklift);
                
		frame.setVisible(true);
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
				ItemListPanel objcts = injector.getInstance(ItemListPanel.class);
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

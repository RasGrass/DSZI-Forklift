package org.dszi.forklift.models;

import org.dszi.forklift.repository.Storehouse;
import com.google.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import org.dszi.forklift.repository.ImageRepository;

/**
 *
 * @author RasGrass
 */
public class Shelf extends JComponent {

	private int objectCount = 0;
	private Image scaledImage;
	private boolean left = false;
	private boolean right = false;
	private Box[] boxes = new Box[5];
	private final int spacer;

	@Inject
	private ImageRepository imageRepository;

	@Inject
	private JFrame mainFrame;

	@Inject
	private Storehouse storehouse;

	public class Box extends JComponent {

		public Point getBoxXY() {
			//Box.this.setVisible(true);
			if (mainFrame.isVisible()) {
				return new Point(Box.this.getLocationOnScreen().x, Box.this.getLocationOnScreen().y);
			} else {
				return null;
			}
		}

		public int getYBorder() {
			return getSize().height;
		}

		public Box() {
			super();
			setPreferredSize(new Dimension((int) (Rack.RACK_WIDTH), (int) ((Rack.RACK_HEIGHT) / 25)));
			setBounds(0, 0, (int) (Rack.RACK_WIDTH), (int) ((Rack.RACK_HEIGHT) / 25));
		}
	}

	public Shelf() {
		super();
		// setLayout(new BorderLayout());
		setPreferredSize(new Dimension((int) (Rack.RACK_WIDTH), (int) ((Rack.RACK_HEIGHT) / 5)));
		setBounds(0, 0, (int) Rack.RACK_WIDTH, (int) (Rack.RACK_HEIGHT / 5));
		//ToolTipManager.sharedInstance().registerComponent(this);
		//System.out.println(getPreferredSize());
		ToolTipManager.sharedInstance().setEnabled(true);
		setToolTipText("Shelf");
		if (left) {
			right = true;
			left = false;
		} else {
			right = false;
			left = true;
		}
		spacer = (int) (getSize().height / 5);

		for (int i = 0; i < 5; i++) {
			boxes[i] = new Box();
			boxes[i].setLocation(Shelf.this.getLocation().x, (int) (Shelf.this.getSize().height / 5) + (spacer * (i - 1)));
			add(boxes[i]);
		}

	}

	public int getObjectCount() {
		return objectCount;
	}

	public int getXBorder() {
		//if (isVisible())
		return getLocation().x;
		// else
		//    return 9999;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledImage, 0, 0, null);
	}

	public Box[] getBoxes() {
		return boxes;
	}

	private void scaleImage() {
		scaledImage = imageRepository.getShelfImage().getScaledInstance((int) Rack.RACK_WIDTH - 1, (int) (Rack.RACK_HEIGHT / 5) - 1, Image.SCALE_SMOOTH);
	}
;
}

package org.dszi.forklift;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.RepaintManager;

/**
 *
 * @author RasGrass
 */
public class Shelf extends JComponent {

	private ArrayList<Object> objOnShelf = new ArrayList(5);
	private int objectCount = 0;
	private BufferedImage shelfImage;
	private Image scaledImage;
	private boolean left = false;
	private boolean right = false;
	private Box[] boxes = new Box[5];
	private int spacer;

	public class Box extends JComponent {

		private Object object;// = new Object();

		public void setObject(Object obj) {
			object = obj;
		}

		public void removeObject() {
			object = null;
		}

		public Object getObject() {
			return object;
		}

		public Point getBoxXY() {
			//Box.this.setVisible(true);
			if (Forklift.getFrame().isVisible()) {
				return new Point(Box.this.getLocationOnScreen().x, Box.this.getLocationOnScreen().y);
			} else {
				return null;
			}
		}

		public int getYBorder() {
			return getSize().height;
		}

		public boolean isEmpty() {
			if (object == null) {
				return true;
			} else {
				return false;
			}
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
		ToolTipManager.sharedInstance().registerComponent(this);
		System.out.println(getPreferredSize());
		ToolTipManager.sharedInstance().setEnabled(true);
		setToolTipText("Shelf");
		try {
			URL url = new URL("file://"+System.getProperty("user.dir")+"/res/halfofshelf.png");
			shelfImage = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("sdgdsg");
		}
		scaleImage();
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

	public void assingObject(Object obj) {
		objectCount++;
		objOnShelf.add(obj);

		Storehouse.getObjects().add(obj);
		boxes[obj.getPlace()].setObject(obj);
		boxes[obj.getPlace()].add(obj);
		RepaintManager.currentManager(this).markCompletelyDirty(obj);

	}

	public void removeObject(Object obj) {
		objOnShelf.remove(obj);
		Storehouse.getObjects().remove(obj);
		RepaintManager.currentManager(this).markCompletelyDirty(obj);
		boxes[obj.getPlace()].removeObject();
		boxes[obj.getPlace()].remove(obj);
		objectCount--;

	}

	public Box[] getBoxes() {
		return boxes;
	}

	@Override
	public String toString() {
		String command = "";
		if (objectCount == 0) {
			command += "No objects on this shelf..." + System.lineSeparator();
		} else {
			for (int i = 0; i < objectCount; i++) {

				command += "ID: " + objOnShelf.get(i).getID() + System.lineSeparator()
						+ "Name: " + objOnShelf.get(i).getName() + System.lineSeparator()
						+ "Weight: " + objOnShelf.get(i).getWeight() + System.lineSeparator()
						+ "Color: " + objOnShelf.get(i).getColor() + System.lineSeparator()
						+ "Type: " + objOnShelf.get(i).getType() + System.lineSeparator()
						+ "==============" + System.lineSeparator();
			}
		}

		return command;
	}

	private void scaleImage() {
		scaledImage = shelfImage.getScaledInstance((int) Rack.RACK_WIDTH - 1, (int) (Rack.RACK_HEIGHT / 5) - 1, Image.SCALE_SMOOTH);
	}
;
}

package org.dszi.forklift;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.dszi.forklift.analiza.NaturalLanguage;

/**
 *
 * @author RasGrass
 */
public class Object extends JComponent {

	private double weight;
	private String name;
	private String color;
	private String type;
	private int rackNumber = 0;
	private int shelfNumber = 0;
	private static int counter = 0;
	private final int ID;
	private BufferedImage objectImage;
	private Image scaledImage;
	private static Point lastLocation = new Point();
	Dimension dim = new Dimension(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getPreferredSize());
	private int place = 0;

	public Object(String name, double weight, String color, String type) {
		this.weight = weight;
		this.name = name;
		this.color = color;
		this.type = type;
		this.ID = counter++;
		init();
		//dim = new Dimension(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getPreferredSize());
	}

	public Object(String name, double weight, String color, String type, int place) {
		this.weight = weight;
		this.name = name;
		this.color = color;
		this.type = type;
		this.ID = counter++;
		this.place = place;
		init();
		//dim = new Dimension(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getPreferredSize());
	}

	public Object(Object obj) {

		this.weight = obj.getWeight();
		this.name = obj.getName();
		this.color = obj.getColor();
		this.type = obj.getType();
		this.ID = counter++;
		this.place = obj.getPlace();
		init();
		//dim = new Dimension(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getPreferredSize());

	}

	public int getID() {
		return ID;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
		//setLocation(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getLocation());

	}

	@Override
	public String getName() {
		return name;
	}

	public double getWeight() {
		return weight;
	}

	public String getColor() {
		return color;
	}

	public String getType() {
		return type;
	}

	public int getRackNumber() {
		return rackNumber;
	}

	public int getShelfNumber() {
		return shelfNumber;
	}

	public void setRackNumber(int rack) {
		this.rackNumber = rack;

		System.out.println("Rack: " + rack);
	}

	public void setShelfNumber(int shelf) {
		this.shelfNumber = shelf;
		//setLocation(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getLocation());
       /* if (!(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getObjectCount()==999)) {
		 setLocation((int) (Storehouse.racks[rackNumber].getShelf(shelfNumber).getBounds().width / 2 - this.getBounds().width - horizontalSpan), Storehouse.racks[rackNumber].getShelf(shelfNumber).getObjectCount() * spacer + verticalSpan);
		 lastLocation = getLocation();
		 } else {
		 if (Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getObjectCount() == 1) {
		 setLocation(lastLocation.x + leftToRightSpan, Storehouse.racks[rackNumber].getShelf(shelfNumber).getObjectCount() * additionalSpacer);
		 } else {
		 setLocation(lastLocation.x + leftToRightSpan, lastLocation.y);
		 }
		 }*/
		System.out.println("Shelf: " + shelf);

	}

	@Override
	public String toString() {
		return "ID: " + getID() + System.lineSeparator()
				+ "Nazwa: " + getName() + System.lineSeparator()
				+ "Waga: " + getWeight() + System.lineSeparator()
				+ "Kolor: " + getColor() + System.lineSeparator()
				+ "Typ: " + getType() + System.lineSeparator()
				+ "Półka: " + getRackNumber() + System.lineSeparator()
				+ "Regał: " + getShelfNumber() + System.lineSeparator()
				+ "==============" + System.lineSeparator();

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledImage, 0, 0, null);
	}

	private void scaleImage() {

		scaledImage = objectImage.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);
	}

	private void init() {
		dim.width = (int) (dim.width / 1.925);
		dim.height = (int) (dim.height / 5.814);

		setPreferredSize(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getSize());
		setBounds(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getBounds());
		setLocation(Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getLocation().x + dim.width / 2, Forklift.getStorehouse().getRacks()[rackNumber].getShelf(shelfNumber).getBoxes()[place].getLocation().y + dim.height / 7);

		setToolTipText("<HTML>"
				+ "<BODY>"
				+ "Nazwa: " + name
				+ "<br>ID: " + ID
				+ "<br>Waga: " + weight
				+ "</BODY>"
				+ "</HTML>");

		super.setName(name);
		try {
			URL url = new URL("file://"+System.getProperty("user.dir")+"/res/box2.png");
			objectImage = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Can't load file boximage.gif");
		}
		scaleImage();
		NaturalLanguage.objectAdded(name);
	}
}

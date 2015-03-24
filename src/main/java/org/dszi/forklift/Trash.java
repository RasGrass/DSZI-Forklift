package org.dszi.forklift;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;

public class Trash extends JComponent {

	private BufferedImage trashImage;
	private Image scaledImage_trash;
	private int mm_x;
	private int mm_y;
	private int rescaledX = (int) Toolkit.getDefaultToolkit().getScreenSize().width / 30;
	private int rescaledY = (int) Toolkit.getDefaultToolkit().getScreenSize().height / 20;

	public int getX() {
		return mm_x;
	}

	Trash() {
		super();
		setPreferredSize(new Dimension(rescaledX, rescaledY));
		try {
			URL url = new URL("file://"+System.getProperty("user.dir")+"/res/kosz.png");
			trashImage = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Can't load file trashimage.png");
		}

		mm_x = (int) Toolkit.getDefaultToolkit().getScreenSize().height + 100;
		mm_y = 0;
		setBounds((int) mm_x, (int) mm_y, rescaledX + 200, rescaledY + 200);
		setLocation((int) mm_x, (int) mm_y);
		setToolTipText("Kosz");
		ToolTipManager.sharedInstance().registerComponent(this);
		scaleImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		setLocation(mm_x, mm_y);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(scaledImage_trash, 0, 0, null);
	}

	private void scaleImage() {
		scaledImage_trash = trashImage.getScaledInstance((int) rescaledX, (int) rescaledY, Image.SCALE_SMOOTH);
	}
}

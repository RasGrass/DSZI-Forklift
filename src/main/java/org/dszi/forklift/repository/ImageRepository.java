package org.dszi.forklift.repository;

import com.google.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author RasGrass
 */
@Singleton
public class ImageRepository {

	private BufferedImage forkliftImageLeft;

	private BufferedImage forkliftImageRight;

	private BufferedImage forkliftImageUp;

	private BufferedImage forkliftImageDown;

	private BufferedImage forkliftImageLeftEmpty;

	private BufferedImage forkliftImageRightEmpty;

	private BufferedImage forkliftImageUpEmpty;

	private BufferedImage forkliftImageDownEmpty;

	private BufferedImage shelfImage;

	private BufferedImage trashImage;

	private BufferedImage objectImage;

	public ImageRepository() {
		try {
			forkliftImageRight = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_right.png"));
			forkliftImageRightEmpty = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_empty_right.png"));
			forkliftImageLeft = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_left.png"));
			forkliftImageLeftEmpty = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_empty_left.png"));
			forkliftImageUp = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_top.png"));
			forkliftImageUpEmpty = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_empty_top.png"));
			forkliftImageDown = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_down.png"));
			forkliftImageDownEmpty = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\forklift_empty_down.png"));
			shelfImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\halfofshelf.png"));
			objectImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\box2.png"));
		} catch (IOException ex) {
			Logger.getLogger(ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public BufferedImage getForkliftImageLeft() {
		return forkliftImageLeft;
	}

	public BufferedImage getForkliftImageRight() {
		return forkliftImageRight;
	}

	public BufferedImage getForkliftImageUp() {
		return forkliftImageUp;
	}

	public BufferedImage getForkliftImageDown() {
		return forkliftImageDown;
	}

	public BufferedImage getForkliftImageLeftEmpty() {
		return forkliftImageLeftEmpty;
	}

	public BufferedImage getForkliftImageRightEmpty() {
		return forkliftImageRightEmpty;
	}

	public BufferedImage getForkliftImageUpEmpty() {
		return forkliftImageUpEmpty;
	}

	public BufferedImage getForkliftImageDownEmpty() {
		return forkliftImageDownEmpty;
	}

	public BufferedImage getShelfImage() {
		return shelfImage;
	}

	public BufferedImage getTrashImage() {
		return trashImage;
	}

	public BufferedImage getObjectImage() {
		return objectImage;
	}
}

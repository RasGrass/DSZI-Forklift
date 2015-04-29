package org.dszi.forklift.models;

import com.google.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;
import org.dszi.forklift.repository.ImageRepository;

public class Trash extends JComponent {

	private Image scaledImage_trash;
	private final int mm_x;
	private final int mm_y;
	private final int rescaledX = (int) Toolkit.getDefaultToolkit().getScreenSize().width / 30;
	private final int rescaledY = (int) Toolkit.getDefaultToolkit().getScreenSize().height / 20;

	@Inject
	private ImageRepository imageRepository;

	public Trash() {
		super();
		setPreferredSize(new Dimension(rescaledX, rescaledY));
		mm_x = (int) Toolkit.getDefaultToolkit().getScreenSize().height + 100;
		mm_y = 0;
		setBounds((int) mm_x, (int) mm_y, rescaledX + 200, rescaledY + 200);
		setLocation((int) mm_x, (int) mm_y);
		setToolTipText("Kosz");
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	@Override
	public int getX() {
		

		return mm_x;
	}

	@Override
	public void paintComponent(Graphics g) {
		setLocation(mm_x, mm_y);
		Graphics2D g2d = (Graphics2D) g;
		//scaleImage();
		g2d.drawImage(scaledImage_trash, 0, 0, null);
	}

	private void scaleImage() {
		scaledImage_trash = imageRepository.getTrashImage().getScaledInstance((int) rescaledX, (int) rescaledY, Image.SCALE_SMOOTH);
	}
}

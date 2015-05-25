package org.dszi.forklift.models;

import org.dszi.forklift.logic.MoveActionTypes;
import org.dszi.forklift.repository.Storehouse;
import com.google.inject.Inject;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;

import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import org.dszi.forklift.repository.ImageRepository;

public class Cart extends JComponent {

	private int gridX;
	private int gridY;
	private int m_x;
	private int m_y;
	private final int rescaledX = (int) Toolkit.getDefaultToolkit().getScreenSize().width / 15;
	private final int rescaledY = (int) Toolkit.getDefaultToolkit().getScreenSize().height / 15;
	private boolean haveToMoveX = false;
	private boolean haveToMoveY = false;
	private final Point desiredLocation = new Point();
	private Graphics2D g2d;
	private Rectangle newBounds = new Rectangle();

	private Image scaledImage_right;
	private Image scaledImage_left;
	private Image scaledImage_top;
	private Image scaledImage_down;
	private Image scaledImage_empty_right;
	private Image scaledImage_empty_left;
	private Image scaledImage_empty_top;
	private Image scaledImage_empty_down;

	private boolean empty = true;
	private boolean left = false;
	private boolean right = false;
	private boolean top = false;
	private boolean down = false;
	private boolean empty_left = false;
	private boolean empty_right = true;
	private boolean empty_top = false;
	private boolean empty_down = false;

	private final JPanel drawingPane;

	private final Storehouse storehouse;

	private final ImageRepository imageRepository;

	@Inject
	public Cart(Storehouse storehouse, ImageRepository imageRepository, JPanel drawingPane) {
		super();
		this.drawingPane = drawingPane;
		this.storehouse = storehouse;
		this.imageRepository = imageRepository;
		//this.trash = trash;
		setPreferredSize(new Dimension(rescaledX + 100, rescaledY + 50));
		m_x = 0;
		m_y = 0;
		gridX = 0;
		gridY = 0;
		setBounds((int) m_x, (int) m_y, rescaledX + 200, rescaledY + 200);
		setLocation((int) m_x, (int) m_y);
		setToolTipText("Wozek widłowy");
		ToolTipManager.sharedInstance().registerComponent(this);
		int p;
		newBounds = getBounds();
		p = newBounds.height;
		newBounds.height = newBounds.width;
		newBounds.width = p;
		scaleImage();
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public boolean getEmpty() {
		return empty;
	}

	public void setLeft() {
		left = true;
		right = false;
		top = false;
		down = false;
	}

	public void setTop() {
		left = false;
		right = false;
		top = true;
		down = false;
	}

	public void setDown() {
		left = false;
		right = false;
		top = false;
		down = true;
	}

	public void setRight() {
		left = false;
		right = true;
		top = false;
		down = false;
	}

	public void setEmptyLeft() {
		empty_left = true;
		empty_right = false;
		empty_top = false;
		empty_down = false;
	}

	public void setEmptyRight() {
		empty_left = false;
		empty_right = true;
		empty_top = false;
		empty_down = false;
	}

	public void setEmptyTop() {
		empty_left = false;
		empty_right = false;
		empty_top = true;
		empty_down = false;
	}

	public void setEmptyDown() {
		empty_left = false;
		empty_right = false;
		empty_top = false;
		empty_down = true;
	}

	public void set_coordinates(int x, int y) {
		m_x = x;
		m_y = y;
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		return "Wozek";
	}

	@Override
	public String getToolTipText() {
		return "Wozek";
	}

	@Override
	public void paintComponent(Graphics g) {
		if (haveToMoveX) {
			moveX(desiredLocation.x);
		}
		RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		if (haveToMoveY) {
			moveY(desiredLocation.y);
		}
		setLocation(m_x, m_y);
		Graphics2D g2d = (Graphics2D) g;

		if (!empty) {
			if (left) {
				g2d.drawImage(scaledImage_left, 0, 0, this);
			}

			if (right) {
				g2d.drawImage(scaledImage_right, 0, 0, this);
			}

			if (down) {
				g2d.drawImage(scaledImage_down, 0, 0, this);
			}

			if (top) {
				g2d.drawImage(scaledImage_top, 0, 0, this);
			}
		} else {
			if (empty_left) {
				g2d.drawImage(scaledImage_empty_left, 0, 0, this);
			}

			if (empty_right) {
				g2d.drawImage(scaledImage_empty_right, 0, 0, this);
			}

			if (empty_down) {
				g2d.drawImage(scaledImage_empty_down, 0, 0, this);
			}

			if (empty_top) {
				g2d.drawImage(scaledImage_empty_top, 0, 0, this);
			}
		}
	}

	public boolean moveX(int x) {
		RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		haveToMoveX = true;
		haveToMoveY = false;
		desiredLocation.x = x;

		if (desiredLocation.x < m_x) {
			m_x -= 1;
		} else {
			m_x += 1;
		}

		try {
			Thread.sleep(17);
		} catch (InterruptedException ex) {
			Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
		}
		RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		if (m_x == desiredLocation.x) {
			haveToMoveX = false;
			return true;
		}
		return false;

	}

	public boolean moveY(int y) {
		RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		haveToMoveY = true;
		haveToMoveX = false;
		desiredLocation.y = y;

		if (desiredLocation.y < m_y) {
			m_y -= 1;
		} else {
			m_y += 1;
		}

		try {
			Thread.sleep(17);
		} catch (InterruptedException ex) {
			Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
		}
		RepaintManager.currentManager(drawingPane).markCompletelyDirty(drawingPane);
		if (m_y == desiredLocation.y) {
			haveToMoveY = false;
			return true;
		}
		return false;
	}

	@Override
	public void processEvent(AWTEvent event) {
	}

	public int get_wsp_x() {
		return (int) m_x;
	}

	public int get_wsp_y() {
		return (int) m_y;
	}

	private void scaleImage() {
		scaledImage_right = imageRepository.getForkliftImageRight().getScaledInstance(rescaledX, rescaledY, Image.SCALE_SMOOTH);
		scaledImage_left = imageRepository.getForkliftImageLeft().getScaledInstance(rescaledX, rescaledY, Image.SCALE_SMOOTH);
		scaledImage_top = imageRepository.getForkliftImageUp().getScaledInstance(rescaledY, rescaledX, Image.SCALE_SMOOTH);
		scaledImage_down = imageRepository.getForkliftImageDown().getScaledInstance(rescaledY, rescaledX, Image.SCALE_SMOOTH);
		scaledImage_empty_right = imageRepository.getForkliftImageRightEmpty().getScaledInstance(rescaledX, rescaledY, Image.SCALE_SMOOTH);
		scaledImage_empty_left = imageRepository.getForkliftImageLeftEmpty().getScaledInstance(rescaledX, rescaledY, Image.SCALE_SMOOTH);
		scaledImage_empty_top = imageRepository.getForkliftImageUpEmpty().getScaledInstance(rescaledY, rescaledX, Image.SCALE_SMOOTH);
		scaledImage_empty_down = imageRepository.getForkliftImageDownEmpty().getScaledInstance(rescaledY, rescaledX, Image.SCALE_SMOOTH);
	}

	public void Move(MoveActionTypes moveAction) {
		switch (moveAction) {
			case RIGHT: {
				gridX++;
				setEmptyRight();
				while (!moveX(gridX * rescaledX)) {
				}
				break;
			}
			case LEFT: {
				gridX--;
				setEmptyLeft();
				while (!moveX(gridX * rescaledX)) {
				}
				break;
			}
			case BOTTOM: {
				gridY++;
				setEmptyDown();
				while (!moveY(gridY * rescaledY)) {
				}
				break;
			}
			case TOP: {
				gridY--;
				setEmptyTop();
				while (!moveY(gridY * rescaledY)) {
				}
				break;
			}
		}
	}
}

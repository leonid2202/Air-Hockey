package airHockey;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import commands.Command;

public class Table extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final double HITDISTANCE = Puck.PUCKRADIUS + Mallet.MALLETRADIUS;
	public static final int BORDER = 10;

	private int width = World.FRAMEWIDTH;
	private int height = World.FRAMEHEIGHT;

	private Puck puck;
	private Mallet mallet1;
	private Mallet mallet2;
	private Image tableImg;

	public Table() throws IOException {
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		puck = new Puck();
		mallet1 = new Mallet(height / 8 * 7 - 10);
		mallet2 = new Mallet(height / 8 + 10);
		tableImg = ImageIO.read(getClass().getResource("pics/table1.jpg"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(tableImg, 0, 0, width, height, null);
		mallet1.draw(g);
		mallet2.draw(g);
		puck.draw(g);
		Graphics2D g2 = (Graphics2D) g;
	}

	protected int movePuck() throws IOException {
		int point = puck.move();
		checkHit();
		return point;
	}

	private boolean checkHit() {
		return calcMallet(mallet1) || calcMallet(mallet2);
	}

	private boolean calcMallet(Mallet mallet) {
		double malletX = mallet.posX;
		double malletY = mallet.posY;
		double diff = Math.sqrt(Math.pow(malletX - puck.posX, 2) + Math.pow(malletY - puck.posY, 2));
		if (diff <= HITDISTANCE) {
			puck.setSlope(mallet);
			return true;
		}
		return false;
	}

	protected void updateMalletCoordinates(double x, double y, int malletNum) {
		if (malletNum == 1)
			mallet1.moveToMouse(x, y);
		if (malletNum == 2)
			mallet2.updateCoordinates(x, y);
	}

	protected void updatePuck(double x, double y) {
		puck.update(x, y);
	}

	protected Command getCommand(char positionable) {
		if (positionable == 'p') {
			return puck.getCommand();
		}
		if (positionable == 'm') {
			return mallet1.getCommand();
		}
		return puck.getCommand();
	}

	public void resetPuckAndMallets(int point) {
		puck.reset(point);
		mallet1.setToStartPosition();
		mallet2.setToStartPosition();
	}

	public void stopMallet() {
		mallet1.stop();
	}
}

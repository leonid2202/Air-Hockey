package airHockey;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import commands.MalletCommand;

public class Mallet extends Positionable {
	private static final double LERP = 0.04;
	protected static final int MALLETRADIUS = 32;

	private final int LEFTBOUND = MALLETRADIUS + Table.BORDER;
	private final int RIGHTBOUND = World.FRAMEWIDTH - MALLETRADIUS - Table.BORDER;
	private final int TOPBOUND = World.FRAMEHEIGHT / 2 + MALLETRADIUS;
	private final int BOTTOMBOUND = World.FRAMEHEIGHT - MALLETRADIUS - Table.BORDER;

	private int startX;
	private int startY;

	public Mallet(int startY) throws IOException {
		this.startX = World.FRAMEWIDTH / 2;
		this.startY = startY;
		setToStartPosition();

		image = ImageIO.read(getClass().getResource("pics/mallet.jpg"));
	}

	public void setToStartPosition() {
		posX = startX;
		posY = startY;
		deltaX = 0;
		deltaY = 0;
	}

	public void moveToMouse(double x, double y) {
		Point point = MouseInfo.getPointerInfo().getLocation();
		double moveToX = point.getX() - x;
		double moveToY = point.getY() - y - MALLETRADIUS * 2;
		double previousX = posX;
		double previousY = posY;

		double dirX = Math.signum(moveToX - posX);
		double dirY = Math.signum(moveToY - posY);



		posX += Math.max(Math.abs((moveToX - posX) * LERP), 1) * dirX;
		posY += Math.max(Math.abs((moveToY - posY) * LERP), 1) * dirY;
		posX = clamp((int)posX, LEFTBOUND, RIGHTBOUND);
		posY = clamp((int)posY, TOPBOUND, BOTTOMBOUND);

		deltaX = previousX - posX;
		deltaY = previousY - posY;
	}

	@Override
	protected void updateCoordinates(double x, double y) {
		deltaX = posX - x;
		deltaY = posY - y;
		posX = x;
		posY = y;
	}

	@Override
	protected void draw(Graphics g) {
		g.drawImage(image, (int) posX - MALLETRADIUS, (int) posY - MALLETRADIUS, MALLETRADIUS * 2, MALLETRADIUS * 2, null);
	}

	@Override
	public MalletCommand getCommand() {
		return new MalletCommand(posX, posY);
	}

	public void stop() {
		deltaX = 0;
		deltaY = 0;
	}
}

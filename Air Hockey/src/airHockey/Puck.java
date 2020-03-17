package airHockey;

import java.awt.*;
import java.io.IOException;

import commands.PuckCommand;

import javax.imageio.ImageIO;

public class Puck extends Positionable {
	protected static final int PUCKRADIUS = 15;
	protected static final double FRICTION = 0.01;
	protected final int GOALDISTANCE = 90;
	protected final int MAXSPEED = 15;

	private final Image image;
	private int width = World.FRAMEWIDTH;
	private int height = World.FRAMEHEIGHT;

	private final int LEFTBOUND = PUCKRADIUS + Table.BORDER;
	private final int RIGHTBOUND = World.FRAMEWIDTH - PUCKRADIUS - Table.BORDER;
	private final int TOPBOUND = Table.BORDER + PUCKRADIUS;
	private final int BOTTOMBOUND = World.FRAMEHEIGHT - PUCKRADIUS - Table.BORDER;

	public Puck() throws IOException {
		posX = width / 2;
		posY = height / 2;
		image = ImageIO.read(getClass().getResource("pics/puck.png"));
	}

	protected int move() throws IOException {
		int goal = 0;
		posX += deltaX;
		posY += deltaY;

		if (posX < LEFTBOUND || posX > RIGHTBOUND) {
			deltaX = -deltaX;
			posX = clamp((int)posX, LEFTBOUND, RIGHTBOUND);
		}
		if (posY < TOPBOUND) {
			deltaY = -deltaY;
			posY = clamp((int)posY, TOPBOUND, BOTTOMBOUND);
			goal =  checkGoal(1);
		} else if (posY  > BOTTOMBOUND) {
			deltaY = -deltaY;
			posY = clamp((int)posY, TOPBOUND, BOTTOMBOUND);
			goal =  checkGoal(2);
		}

		slowdown();
		return goal;
	}

	private void slowdown() {
		deltaX *= (1 - FRICTION);
		deltaY *= (1 - FRICTION);
	}

	private int checkGoal(int player) throws  IOException {
		if (posX > GOALDISTANCE && posX < width - GOALDISTANCE) {
			return player;
		}
		return 0;
	}

	public void reset(int point) {
		posX = width / 2;
		if (point == 1) {
			posY = height / 4;
		}
		else  if (point == 2){
			posY = height * 3 / 4;
		} else {
			posY = height / 2;
		}
		deltaX = 0;
		deltaY = 0;
	}

	protected void setSlope(Mallet mallet) {
//			Not working as intended
//			double malletDeltaX = mallet.getDeltaX();
//			double malletDeltaY = mallet.getDeltaY();
//			deltaX = -malletDeltaX - deltaX * 0.6;
//			deltaY = -malletDeltaY - deltaY * 0.6;

			double hypotenuse = Math.sqrt((mallet.posX - posX) * (mallet.posX - posX) + (mallet.posY - posY) * (mallet.posY - posY));
			double totalSpeed = Math.min(this.getSpeed() + mallet.getSpeed(), MAXSPEED);
			deltaX = - totalSpeed * (mallet.posX - posX) / hypotenuse * 0.8;
			deltaY = - totalSpeed * (mallet.posY - posY) / hypotenuse * 0.8;
	}

	protected void update(double x, double y) {
		updateCoordinates(x, y);
	}

	@Override
	protected void draw(Graphics g) {
		g.drawImage(image, (int) posX - PUCKRADIUS, (int) posY - PUCKRADIUS, PUCKRADIUS * 2, PUCKRADIUS * 2, null);
	}

	@Override
	protected PuckCommand getCommand() {
		return new PuckCommand(posX, posY);
	}
}

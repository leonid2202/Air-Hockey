package airHockey;

import java.awt.*;

import commands.Command;

public abstract class Positionable {
	protected double posX;
	protected double posY;
	protected double deltaX;
	protected double deltaY;
	protected Image image;

	protected void updateCoordinates(double x, double y) {
		posX = x;
		posY = y;
	}

	public double getSpeed() { return  Math.sqrt(deltaX * deltaX + deltaY * deltaY); }


	protected int clamp(int num, int min, int max) {
		if (num > max)
			return max;
		if (num < min)
			return min;
		return num;
	}

	protected abstract void draw(Graphics g);

	protected abstract Command getCommand();
}

package commands;

import airHockey.World;

public abstract class PositionCommand implements Command {
	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;

	public PositionCommand(double x, double y) {
		//mirroring
		this.x = x - 2 * (x - World.FRAMEWIDTH / 2);
		this.y = y - 2 * (y - World.FRAMEHEIGHT / 2);
	}
}
package commands;

import airHockey.World;

public class PuckCommand extends PositionCommand {
	private static final long serialVersionUID = 1L;

	public PuckCommand(double x, double y) {
		super(x, y);
	}

	@Override
	public void perform(World world) {
		world.updatePuckCoordinates(x, y);
	}
}

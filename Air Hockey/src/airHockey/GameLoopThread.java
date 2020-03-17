package airHockey;

import java.awt.*;
import java.io.IOException;

public class GameLoopThread extends Thread {
	private World world;
	private boolean running;

	public GameLoopThread(World world) {
		this.world = world;
		running = true;
	}

	@Override
	public void run() {
		while (running) {
			try {
				if (world.isFocused()) {
					Point point = world.getLocation();
					world.moveMallet(point.getX(), point.getY());
				}
				else {
					world.stopMallet();
				}
				world.sendCommand(world.table.getCommand('m'));
				world.movePuck();
				world.syncPuck();
				world.repaintTable();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			try {
				sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

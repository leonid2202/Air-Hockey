package airHockey;

import commands.GoalCommand;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;


public class ServerWorld extends World {
	private static final long serialVersionUID = 1L;

	public ServerWorld(boolean test) throws IOException {
		if (!test) {
			setLocationRelativeTo(null);
		} else {
			Dimension scrsize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((int)scrsize.getWidth()/2 - 520,(int)scrsize.getHeight()/2 - 279);
		}
		ServerSocket serverSocket = new ServerSocket(6261); // port num sent
		socket = serverSocket.accept();
		setUp(3); // 3 = server first
	}

	public void movePuck() throws IOException {
		int point = table.movePuck();
		if (point != 0) {
			goal(point == 2);
			sendCommand(new GoalCommand(point ==1));
		}
	}

	@Override
	public void syncPuck() throws IOException, InterruptedException {
		sendCommand(table.getCommand('p'));
	};
}
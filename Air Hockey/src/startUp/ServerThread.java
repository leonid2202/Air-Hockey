package startUp;

import java.io.IOException;

import airHockey.ServerWorld;

public class ServerThread extends Thread {
	private LoadingFrame frame;

	public ServerThread(LoadingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		try {
			new ServerWorld(false);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		frame.dispose();
	}
}
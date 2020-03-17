package startUp;

public class ServerSetup {

	public ServerSetup()  {
		LoadingFrame frame = new LoadingFrame();
		frame.setVisible(true);
		new ServerThread(frame).start();
	}
}

package airHockey;

import java.io.IOException;
import java.net.Socket;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;

	public ClientWorld(String serverAddress) throws IOException {
		setLocationRelativeTo(null);
		socket = new Socket(serverAddress, 6261);
		setUp(1); // 1 = server second
	}

}

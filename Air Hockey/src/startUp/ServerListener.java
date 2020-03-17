package startUp;

import java.awt.event.ActionEvent;
import java.io.IOException;

import airHockey.AirHockey;

public class ServerListener extends SetUpListener {

	public ServerListener(AirHockey hockey) {
		super(hockey);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ServerSetup();
		hockey.dispose();
	}
}

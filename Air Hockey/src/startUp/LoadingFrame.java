package startUp;

import javax.swing.*;
import java.awt.*;

public class LoadingFrame extends JDialog {
	private static final long serialVersionUID = 1L;

	public LoadingFrame() {
		setSize(550, 100);
		setTitle("Air Hockey");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLabel text = new JLabel("Waiting for another player to connect", SwingConstants.CENTER);
		text.setFont(new Font("Arial",Font.BOLD,24));
		text.setBackground(Color.WHITE);
		add(text);

		setVisible(true);
	}
}

package airHockey;

import java.awt.Cursor;
import java.awt.Font;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.Socket;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import commands.Command;

public class World extends JFrame implements ReaderListener {
	public static final int FRAMEWIDTH = 320;
	public static final int FRAMEHEIGHT = 500;
	private static final long serialVersionUID = 1L;
	
	protected Table table;
	protected Socket socket;

	private JLabel points1;
	private JLabel points2;
	private int totalPlayer;
	private int totalEnemy;

	private Font font;
	private Font fontBold;

	private ObjectOutputStream objOut;

	public World() throws IOException {
		setTitle("Air Hockey");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		font = new Font("Arial", Font.PLAIN, 14);
		fontBold = font.deriveFont(Font.BOLD);
		setFocusable(true);
		setUpMenu();
		setSize(FRAMEWIDTH, FRAMEHEIGHT);
		setResizable(false);

		table = new Table();
		add(table);

		totalPlayer = 0;
		totalEnemy = 0;
		pack();
	}

	protected void setUp(int number) throws IOException {
		new ReaderThread(socket, this).start();
		OutputStream out = socket.getOutputStream();
		objOut = new ObjectOutputStream(out);

		setVisible(true);
		new GameLoopThread(this).start();
	}

	private void setUpMenu()  {
		JMenuBar menu = new JMenuBar();

		JLabel player1 = new JLabel("Me: ");
		player1.setFont(font);
		menu.add(player1);

		points1 = new JLabel(String.valueOf(0));
		points1.setFont(fontBold);
		menu.add(points1);

		menu.add(Box.createHorizontalStrut(35));

		JLabel play2 = new JLabel("Opponent: ");
		play2.setFont(font);
		menu.add(play2);

		points2 = new JLabel(String.valueOf(0));
		points2.setFont(fontBold);
		menu.add(points2);

		setJMenuBar(menu);
	}

	public void movePuck() throws IOException {
	}

	public void goal(boolean isEnemyGoal) {
		if (isEnemyGoal) {
			points2.setText(String.valueOf(++totalEnemy));
			table.resetPuckAndMallets(2);
		}
		else {
			points1.setText(String.valueOf(++totalPlayer));
			table.resetPuckAndMallets(1);
		}
	}

	public void moveMallet(double x, double y) {
		table.updateMalletCoordinates(x, y, 1);
	}

	public synchronized void sendCommand(Command command) throws IOException {
		objOut.writeObject(command);
		objOut.flush();
	}

	public void updateMalletCoordinates(double x, double y) {
		table.updateMalletCoordinates(x, y, 2);
	}

	public void updatePuckCoordinates(double x, double y) {
		table.updatePuck(x, y);
	}

	public void syncPuck() throws IOException, InterruptedException {
	}

	@Override
	public void onObjectRead(Command command) {
		command.perform(this);
	}

	@Override
	public void onCloseSocket(Socket socket) {

	}

	public void repaintTable() {
		table.repaint();
	}

	public void stopMallet() {
		table.stopMallet();
	}
}
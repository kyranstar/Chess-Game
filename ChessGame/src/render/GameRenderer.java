package render;

import game.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Queue;

import javax.swing.JPanel;

public class GameRenderer {
	public static String GAME_TITLE = "Chess Game by Ben Zabback and Kyran Adams";

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	private static final int CHESSBOARD_SIDE_LENGTH = 13;

	private static final int TILE_WIDTH = WIDTH / CHESSBOARD_SIDE_LENGTH;
	private static final int TILE_HEIGHT = HEIGHT / CHESSBOARD_SIDE_LENGTH;

	private final JPanel panel;
	private final Game game;

	public GameRenderer(final JPanel panel) {
		this.panel = panel;
		game = new Game();
	}

	public void processInput(final Queue<KeyEvent> keyEvents,
			final Queue<MouseEvent> mouseEvents,
			final Queue<MouseWheelEvent> mouseWheelEvents) {

	}

	public void update() {

	}

	public void draw() {
		final Graphics2D g = (Graphics2D) panel.getGraphics();
		g.setColor(Color.BLUE);
		g.fillRect(10, 10, 200, 200);
		g.dispose();
	}
}

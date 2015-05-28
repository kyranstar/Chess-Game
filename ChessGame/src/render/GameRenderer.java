package render;

import game.Game;
import helper.Logger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import main.GameLoop.MouseEventType;
import main.GameLoop.MouseEventWithType;

public class GameRenderer {
	public static String GAME_TITLE = "Chess Game by Ben Zabback and Kyran Adams";

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	private static final int CHESSBOARD_SIDE_LENGTH = 8;

	private static final int TILE_WIDTH = WIDTH / CHESSBOARD_SIDE_LENGTH;
	private static final int TILE_HEIGHT = HEIGHT / CHESSBOARD_SIDE_LENGTH;

	private final JPanel panel;
	private final Game game;

	public GameRenderer(final JPanel panel) {
		this.panel = panel;
		game = new Game(); // What should are target fps be? must be passed to
							// game constructor
	}

	public void processInput(final Queue<MouseEventWithType> mouseEvents) {
		for (final MouseEventWithType eventWithType : mouseEvents) {
			final MouseEvent event = eventWithType.event;
			if (SwingUtilities.isLeftMouseButton(event)) {
				handleLeftMouseEvent(eventWithType);
			}
			// Handle right click menu
			if (event.isPopupTrigger()) {
				final JPopupMenu menu = new ChessPopupMenu();
				menu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
	}

	private void handleLeftMouseEvent(final MouseEventWithType eventWithType) {
		Point p1 = null;
		Point p2 = null;
		final MouseEventType type = eventWithType.type;
		final MouseEvent event = eventWithType.event;
		final Point tile = getTileFromScreen(event.getPoint());
		// Return if out of bounds event
		if (tile.x >= CHESSBOARD_SIDE_LENGTH || tile.x < 0
				|| tile.y >= CHESSBOARD_SIDE_LENGTH || tile.y < 0) {
			return;
		}
		Logger.info("Mouse event in tile " + tile + " with type " + type);

		if (type == MouseEventType.PRESS) {
			p1 = event.getPoint();
		} else if (type == MouseEventType.DRAG) {

		} else if (type == MouseEventType.RELEASE) {
			p2 = event.getPoint();
		}
		if (p1 != null && p2 != null) { // if first and last points have been
										// entered, move piece and clear p1 and
										// p2
			if (!game.move(p1, p2)) {
				System.out.println("Invalid move");
			}
		}
	}

	private static Point getTileFromScreen(final Point p) {
		return new Point(p.x / TILE_WIDTH, p.y / TILE_HEIGHT);
	}

	public void update() {

	}

	public void draw() {
		final Graphics2D g = (Graphics2D) panel.getGraphics();
		for (int x = 0; x < CHESSBOARD_SIDE_LENGTH; x++) {
			for (int y = 0; y < CHESSBOARD_SIDE_LENGTH; y++) {
				final boolean white = x % 2 == 1 ^ y % 2 == 1;
				g.setColor(white ? Color.WHITE : Color.BLACK);
				g.fillRect(x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH,
						TILE_HEIGHT);
			}
		}
	}
}

package render;

import game.Game;

import javax.swing.JPanel;

public class GameRenderer {
	public static String GAME_TITLE = "Chess Game by Ben Zabback and Kyran Adams";

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	// The number of tiles wide the chessboard is
	private static final int CHESSBOARD_SIDE_LENGTH = 13;

	private static final int TILE_WIDTH = WIDTH / CHESSBOARD_SIDE_LENGTH;
	private static final int TILE_HEIGHT = HEIGHT / CHESSBOARD_SIDE_LENGTH;

	private final JPanel panel;
	private final Game game;

	public GameRenderer(final JPanel panel) {
		this.panel = panel;
		game = new Game();
	}

	public void update(final float delta) {

	}
}

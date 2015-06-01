package ui;

import game.Game;
import game.Move;
import game.PieceTeam;
import helper.GraphicsConstants;
import helper.GraphicsUtils;
import helper.Logger;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.GameLoop.MouseEventType;
import main.GameLoop.MouseEventWithType;

public class GameUI {
	public static String GAME_TITLE = "Chess Game by Ben Zabback and Kyran Adams";

	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;

	private static final int TILE_WIDTH = WIDTH / Game.SIDE_LENGTH;
	private static final int TILE_HEIGHT = HEIGHT / Game.SIDE_LENGTH;

	private final JPanel panel;
	private final Game game;

	private BufferedImage drawingBuffer;

	public GameUI(final JPanel panel) {
		this.panel = panel;
		game = new Game();
	}

	public void initialize() {
		drawingBuffer = GraphicsUtils.createImage(panel.getWidth(), panel.getHeight(), Transparency.OPAQUE);
	}

	public void processInput(final Queue<MouseEventWithType> mouseEvents) {
		for (final MouseEventWithType eventWithType : mouseEvents) {
			final MouseEvent event = eventWithType.event;

			// Handle left click events
			if (SwingUtilities.isLeftMouseButton(event)) {
				handleLeftMouseEvent(eventWithType);
			}
		}
	}

	private Point pressTile = null;
	private Point dragPoint = null;
	private Point releaseTile = null;

	private void handleLeftMouseEvent(final MouseEventWithType eventWithType) {
		final MouseEventType type = eventWithType.type;
		final MouseEvent event = eventWithType.event;
		final Point tile = getTileFromScreen(event.getPoint());
		// Return if out of bounds event
		if (tile.x >= Game.SIDE_LENGTH || tile.x < 0 || tile.y >= Game.SIDE_LENGTH || tile.y < 0) {
			return;
		}

		if (type == MouseEventType.PRESS) {
			pressTile = tile;
			dragPoint = event.getPoint();
		} else if (type == MouseEventType.DRAG) {
			dragPoint = event.getPoint();
		} else if (type == MouseEventType.RELEASE) {
			releaseTile = tile;
			dragPoint = null;
		}
		if (pressTile != null && releaseTile != null) {
			// Don't move if they didn't pick another tile
			if (getGame().getPiece(pressTile) != null && !pressTile.equals(releaseTile)) {
				final boolean moved = getGame().move(new Move(pressTile, releaseTile));
				if (!moved) {
					Logger.info("Invalid move");
				}
			}
			pressTile = releaseTile = null;
		}
	}

	private static Point getTileFromScreen(final Point p) {
		return new Point(p.x / TILE_WIDTH, p.y / TILE_HEIGHT);
	}

	public void update() {
		if(getGame().isAI() && getGame().getAiAlgorithm() != null && getGame().getCurrentTeam() == PieceTeam.BLACK){
			boolean valid = getGame().move(getGame().getAiAlgorithm().getNextMove(getGame()));
			if(!valid){
				Logger.error("AI failed! Move generated was not valid.");
			}
		}
	}

	public void draw() {
		final Graphics2D g = (Graphics2D) drawingBuffer.getGraphics();
		// Draw background
		for (int x = 0; x < Game.SIDE_LENGTH; x++) {
			for (int y = 0; y < Game.SIDE_LENGTH; y++) {
				final boolean white = x % 2 == 0 ^ y % 2 == 1;
				g.setColor(white ? Color.WHITE : new Color(100, 100, 100));
				g.fillRect(x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);
			}
		}
		// Highlight available moves
		final Color highlightColor = new Color(190, 160, 50, 150);
		if (pressTile != null && getGame().getPiece(pressTile) != null) {
			for (int x = 0; x < Game.SIDE_LENGTH; x++) {
				for (int y = 0; y < Game.SIDE_LENGTH; y++) {
					// If it's a legal move, draw the highlight color
					final Move move = new Move(pressTile, new Point(x, y));

					if (getGame().getPiece(pressTile).isLegalMove(move, getGame().getPiece(x, y)) && getGame().getPiece(pressTile).getTeam() == getGame().getCurrentTeam()) {
						g.setColor(highlightColor);
						g.fillRect(x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT);
					}

				}
			}
		}
		// Draw pieces
		for (int x = 0; x < Game.SIDE_LENGTH; x++) {
			for (int y = 0; y < Game.SIDE_LENGTH; y++) {
				final BufferedImage image = GraphicsConstants.getImage(getGame().getPiece(x, y));
				// Draw drag point instead of original point
				if (new Point(x, y).equals(pressTile) && getGame().getPiece(pressTile) != null && getGame().getPiece(pressTile).getTeam() == getGame().getCurrentTeam()) {
					g.drawImage(image, null, dragPoint.x - image.getWidth() / 2, dragPoint.y - image.getHeight() / 2);
					continue;
				}

				g.drawImage(image, null, x * TILE_WIDTH, y * TILE_WIDTH);
			}
		}

		panel.getGraphics().drawImage(drawingBuffer, 0, 0, null);
	}

	public Game getGame() {
		return game;
	}
}

package ui;

import game.Game;
import helper.Logger;

import java.awt.event.ActionEvent;

/**
 * Call methods in this class when changes in the ui happen
 *
 * @author Kyran Adams
 *
 */
public class ChessUIResponder {
	private final Game game;

	public ChessUIResponder(final Game game) {
		this.game = game;
	}

	public void newGameClicked(final ActionEvent event) {
		Logger.info("New game clicked");
	}

	public void saveClicked(final ActionEvent event) {
		Logger.info("Save game clicked");
	}

	public void loadClicked(final ActionEvent event) {
		Logger.info("Load game clicked");
	}

	public void undoClicked(final ActionEvent event) {
		Logger.info("Undo move clicked");
	}

	public void aiSet(final boolean ai) {
		Logger.info("AI set to " + ai);
	}
}

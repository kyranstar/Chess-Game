package ui;

import game.Game;
import helper.Logger;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Call methods in this class when changes in the ui happen
 *
 * @author Kyran Adams
 *
 */
public class ChessUIResponder {
	private final Game game;
	private final JPanel parent;

	public ChessUIResponder(final Game game, final JPanel parent) {
		this.game = game;
		this.parent = parent;
	}

	public void newGameClicked(final ActionEvent event) {
		Logger.info("New game clicked");
		if (JOptionPane.showConfirmDialog(parent, "Are you sure?") == JOptionPane.OK_OPTION) {
			game.reset();
		}
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

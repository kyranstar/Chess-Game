package ui;

import game.Game;
import game.GameSerializer;
import helper.Logger;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
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
	private ChessPanel chessPanel;

	public ChessUIResponder(final Game game, final JPanel parent) {
		this.game = game;
		this.parent = parent;
	}

	public void setChessPanel(final ChessPanel chessPanel) {
		this.chessPanel = chessPanel;
	}

	public void newGameClicked(final ActionEvent event) {
		Logger.info("New game clicked");
		if (JOptionPane.showConfirmDialog(parent, "Are you sure?") == JOptionPane.OK_OPTION) {
			game.reset();
		}
	}

	public void saveClicked(final ActionEvent event) {
		Logger.info("Save game clicked");

		try {
			final File file = getUserSelectedFile();
			if (file == null) {
				return;
			}
			GameSerializer.save(game, new FileOutputStream(file));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "File not found!");
		}
	}

	public void loadClicked(final ActionEvent event) {
		Logger.info("Load game clicked");

		try {
			final File userFile = getUserSelectedFile();
			if (userFile == null) {
				return;
			}
			final Game newGame = GameSerializer.load(userFile);
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					game.setPiece(x, y, newGame.getPiece(x, y));
				}
			}
			game.setCurrentTeam(newGame.getCurrentTeam());
			game.setAI(newGame.isAI());

			chessPanel.aiToggle.setSelected(game.isAI());
		} catch (final IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Error while loading!");
		}
	}

	public void undoClicked(final ActionEvent event) {
		Logger.info("Undo move clicked");
		game.undo();
	}

	public void aiSet(final boolean ai) {
		Logger.info("AI set to " + ai);
		game.setAI(ai);
	}

	/**
	 *
	 * @return user selected file or null if none selected
	 */
	private File getUserSelectedFile() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		final int result = fileChooser.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
}

package game;

import java.util.Stack;

/**
 * Represents the previously done moves to facilitate the undo feature
 * 
 * @author s-KADAMS
 *
 */
public class UndoStack {

	private final Stack<GamePiece[][]> previous = new Stack<>();

	public void doMove(Game game) {
		previous.push(Game.copyBoard(game.getBoard()));
	}

	public void undo(Game game) {
		if (previous.isEmpty()) {
			return;
		}

		game.setBoard(previous.pop());
		game.swapTeams();
	}
}

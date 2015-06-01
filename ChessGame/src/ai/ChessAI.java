package ai;

import game.Game;
import game.Move;

public interface ChessAI {
	/**
	 *
	 * @param game
	 * @return the next move to carry out
	 */
	public Move getNextMove(Game game);
}

package ai;

import game.Game;
import game.GamePiece;
import game.Move;
import game.PieceTeam;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class SmartAI extends ChessAI {
	/**
	 *
	 * @param game
	 * @return Smart version of ChessAI, copied to allow saving of ChessAI in
	 *         github
	 */
	@Override
	public Move getNextMove(Move mostRecent, Game game, int plies, PieceTeam team) {
		// all legal black moves
		// eval top 10
		// for all 10, get all legal white moves and top 10
		// get top 10 black moves for each of those
		// eval board

		// recursive function that runs plies times and returns best move

		ArrayList<Move> tenBest = tenBestMoves(getLegalMoves(PieceTeam.BLACK, game.getBoard()));
		if (plies == 0) {
			return tenBest.get(0);
		}
		return null; // TODO: IMPLEMENT

	}

	// returns sorted list of 10 best moves based on criteria (sorted best to
	// worst
	private ArrayList<Move> tenBestMoves(ArrayList<Move> moves) {
		Collections.sort(moves); // moves score themselves and are sorted
		return new ArrayList<Move>(moves.subList(0, 10));
	}

	// returns all legal moves doable by the specified team
	private ArrayList<Move> getLegalMoves(PieceTeam team, GamePiece[][] board) {
		ArrayList<GamePiece> teamPieces = new ArrayList<>();
		ArrayList<Point> piecePoses = new ArrayList<>();
		ArrayList<Move> out = new ArrayList<>();
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (board[y][x].getTeam() == team) {
					teamPieces.add(board[y][x]);
					piecePoses.add(new Point(x, y));
				}
			}
		}
		for (int i = 0; i < teamPieces.size(); i++) {
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (teamPieces.get(i).isLegalMove(new Move(piecePoses.get(i), new Point(x, y)), board)) {
						out.add(new Move(piecePoses.get(i), new Point(x, y), board));
					}
				}
			}
		}

		return out;
	}
}

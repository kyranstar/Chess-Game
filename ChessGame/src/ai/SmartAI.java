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

	public Move getNextMove(Move mostRecent, Game game) {
		// all legal black moves
		// eval top 10
		// for all 10, get all legal white moves and top 10
		// get top 10 black moves for each of those
		// eval board

		Move[] tenBestBlack = tenBestMoves(getLegalMoves(PieceTeam.BLACK, game.getBoard()));
		Move[][][] tenBestWhites = new Move[10][10][10];
		for(int i = 0; i < 10; i++){
			tenBestWhites[i][] = tenBestMoves(getLegalMoves(PieceTeam.WHITE, tenBestBlack[i].getFinalBoard()));
		}
	}

	// returns sorted list of 10 best moves based on criteria (sorted best to
	// worst
	private Move[] tenBestMoves(ArrayList<Move> moves) {
		Collections.sort(moves); // moves score themselves and are sorted
		return new ArrayList<Move>(moves.subList(0, 10)).toArray(new Move[10]);
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

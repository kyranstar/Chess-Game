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

		Move[] moves = tenBestMoves(getLegalMoves(PieceTeam.BLACK, game.getBoard()));
		for (int i = 0; i < 10; i++) {
			moves[i].nextMoves = tenBestMoves(getLegalMoves(PieceTeam.WHITE, moves[i].getFinalBoard()));
			for (int j = 0; j < 10; j++) {
				moves[i].nextMoves[j].nextMoves = tenBestMoves(getLegalMoves(PieceTeam.WHITE, moves[i].nextMoves[j].getFinalBoard()));
			}
		}
		for (Move m : moves) {
			for (Move n : m.nextMoves) {
				n.cumulativeScore = n.nextMoves[0].getScore() + n.getScore();
			}
			int maxScore = m.nextMoves[0].getScore();
			int ind = 0;
			for (int i = 1; i < m.nextMoves.length; i++) {
				if (m.nextMoves[i].getScore() > maxScore) {
					maxScore = m.nextMoves[i].getFullScore();
					ind = i;
				}
			}
			m.cumulativeScore = m.getScore() + m.nextMoves[ind].getFullScore();
		}
		int maxCumScore = moves[0].getScore();
		int ind = 0;
		for (int i = 1; i < moves.length; i++) {
			if (moves[i].cumulativeScore > maxCumScore) {
				maxCumScore = moves[i].cumulativeScore;
				ind = i;
			}
		}
		return moves[ind];
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

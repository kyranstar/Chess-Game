package game;

import java.awt.Point;

/**
 * Represents a move from one square to another.
 *
 * @author Kyran Adams & his cool bro Ben Zabback
 *
 */
public class Move implements Comparable {
	public final Point start;
	public final Point end;
	private GamePiece[][] initialBoard;
	private GamePiece[][] finalBoard;
	private int cumulativeScore = 0; // score of this move and its resulting
										// moves in each consecutive ply (used
										// by AI)

	public Move(final Point start, final Point end) {
		this.start = start;
		this.end = end;
	}

	public Move(final Point start, final Point end, GamePiece[][] board) {
		this.start = start;
		this.end = end;
		initialBoard = board;
	}

	public int getdx() {
		return end.x - start.x;
	}

	public int getdy() {
		return end.y - start.y;
	}

	public GamePiece[][] getInitialBoard() {
		return initialBoard;
	}

	public GamePiece[][] getFinalBoard() {
		if (finalBoard != null) {
			finalBoard = initialBoard;
			GamePiece temp = finalBoard[end.y][end.x];
			finalBoard[end.y][end.x] = finalBoard[start.y][start.x];
			finalBoard[start.y][start.x] = temp;
		}
		return finalBoard;
	}

	public int compareTo(Move m) {
		return m.getScore() < getScore() ? 1 : m.getScore() == getScore() ? 0 : -1;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	public int getScore() { // evaluate score
		// move criteria:
		// -gets out of check - required
		// -Kills enemy piece pawn: +3, bishop: +7, rook: +6, knight: +7, Queen:
		// +9
		// -puts enemy in check +5
		// -checkmates enemy +100
		// -moves to center +1
		// -lose piece pawn: -3, bishop: -7, rook: -6, knight: -7, Queen: -9
		return 0;
	}
}

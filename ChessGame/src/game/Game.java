package game;

import java.awt.Point;
import java.util.Arrays;
import java.util.Stack;

public class Game {
	private final GamePiece[][] board = new GamePiece[8][8];
	private PieceTeam currentTeam;
	private boolean isAI;
	private Stack<Move> moveStack;

	public Game() {
		reset();
	}

	public void clearBoard() {
		for (final GamePiece[] arr : board) {
			Arrays.fill(arr, null);
		}
	}

	public void reset() {
		moveStack = new Stack<>();
		setCurrentTeam(PieceTeam.WHITE);
		clearBoard();
		isAI = false;

		board[0][0] = new GamePiece(PieceType.ROOK, PieceTeam.BLACK);
		board[0][1] = new GamePiece(PieceType.KNIGHT, PieceTeam.BLACK);
		board[0][2] = new GamePiece(PieceType.BISHOP, PieceTeam.BLACK);
		board[0][3] = new GamePiece(PieceType.QUEEN, PieceTeam.BLACK);
		board[0][4] = new GamePiece(PieceType.KING, PieceTeam.BLACK);
		board[0][5] = new GamePiece(PieceType.BISHOP, PieceTeam.BLACK);
		board[0][6] = new GamePiece(PieceType.KNIGHT, PieceTeam.BLACK);
		board[0][7] = new GamePiece(PieceType.ROOK, PieceTeam.BLACK);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new GamePiece(PieceType.PAWN, PieceTeam.BLACK);
		}

		// initializes white pieces by mirroring array
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[3 - r][c] != null) {
					board[4 + r][c] = new GamePiece(board[3 - r][c].getType(), PieceTeam.WHITE);
				}
			}
		}
	}

	/**
	 * is the other spot empty, if not is the piece there of the opposite team is the move legal if all true, piece is
	 * moved and true is returned
	 *
	 * @param move
	 * @return whether the move was successful
	 */
	public boolean move(final Move move) {
		final Point start = move.start;
		final Point end = move.end;

		// Make sure it's the moved piece's turn
		if (!(board[start.y][start.x].getTeam() == getCurrentTeam())) {
			return false;
		}

		if (board[start.y][start.x].isLegalMove(new Move(start, end), getPiece(end))) {
			board[end.y][end.x] = board[start.y][start.x];
			board[start.y][start.x] = null;
			swapTeams();

			moveStack.push(move);
			return true;
		}
		return false;
	}

	public void undo() {
		if (moveStack.isEmpty()) {
			return;
		}
		final Move move = moveStack.pop().getInverse();
		final Point end = move.end;
		final Point start = move.start;
		board[end.y][end.x] = board[start.y][start.x];
		board[start.y][start.x] = null;
		swapTeams();
	}

	private void swapTeams() {
		if (getCurrentTeam() == PieceTeam.WHITE) {
			setCurrentTeam(PieceTeam.BLACK);
		} else if (getCurrentTeam() == PieceTeam.BLACK) {
			setCurrentTeam(PieceTeam.WHITE);
		}
	}

	@Override
	public String toString() {
		return boardToString() + "\nCurrent team: " + getCurrentTeam();
	}

	public String boardToString() {
		final StringBuilder out = new StringBuilder();
		for (final GamePiece[] row : board) {
			for (final GamePiece g : row) {
				if (g == null) {
					out.append('X');
				} else {
					// Print lower case 'k' for knight so it doesn't conflict
					// with 'K'ing
					out.append(g.getType() == PieceType.KNIGHT ? 'k' : g.getType().toString().charAt(0));
				}
			}
			out.append('\n');
		}
		return out.toString();
	}

	public GamePiece getPiece(final int x, final int y) {
		return board[y][x];
	}

	public GamePiece getPiece(final Point tile) {
		return getPiece(tile.x, tile.y);
	}

	public PieceTeam getCurrentTeam() {
		return currentTeam;
	}

	public void setPiece(final int x, final int y, final GamePiece object) {
		board[y][x] = object;
	}

	public void setCurrentTeam(final PieceTeam currentTeam) {
		this.currentTeam = currentTeam;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(final boolean isAI) {
		this.isAI = isAI;
	}
}

package game;

import java.awt.Point;

public class Game {
	private final GamePiece[][] board = new GamePiece[8][8];
	private PieceTeam currentTeam = PieceTeam.WHITE;
	private final int scoreP1 = 0; // player 1 score
	private final int scoreP2 = 0; // player 2 score

	public Game() {
		initializeBoard();
		System.out.println(boardToString().replace("|", "\n"));
	}

	private void initializeBoard() {
		board[0][0] = new GamePiece(PieceType.ROOK, PieceTeam.WHITE);
		board[0][1] = new GamePiece(PieceType.KNIGHT, PieceTeam.WHITE);
		board[0][2] = new GamePiece(PieceType.BISHOP, PieceTeam.WHITE);
		board[0][3] = new GamePiece(PieceType.QUEEN, PieceTeam.WHITE);
		board[0][4] = new GamePiece(PieceType.KING, PieceTeam.WHITE);
		board[0][5] = new GamePiece(PieceType.BISHOP, PieceTeam.WHITE);
		board[0][6] = new GamePiece(PieceType.KNIGHT, PieceTeam.WHITE);
		board[0][7] = new GamePiece(PieceType.ROOK, PieceTeam.WHITE);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new GamePiece(PieceType.PAWN, PieceTeam.WHITE); // white
			// pawns
		}

		// initializes black pieces by mirroring array
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[3 - r][c] != null) {
					board[4 + r][c] = new GamePiece(board[3 - r][c].getType(),
							PieceTeam.BLACK);
				}
			}
		}
		// switch black king/queen
		final GamePiece temp = board[7][3];
		board[7][3] = board[7][4];
		board[7][4] = temp;
	}

	/**
	 * is the other spot empty, if not is the piece there of the opposite team
	 * is the move legal if all true, piece is moved and true is returned
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean move(final Point p1, final Point p2) {
		// Make sure it's the moved piece's turn
		if (!(board[p1.y][p1.x].getTeam() == currentTeam)) {
			return false;
		}
		final boolean isKillingPiece = board[p2.y][p2.x] != null
				&& board[p2.y][p2.x].getTeam() != board[p1.y][p1.x].getTeam();
		if (board[p1.y][p1.x].isLegalMove(p1, p2, isKillingPiece)) {
			board[p2.y][p2.x] = board[p1.y][p1.x];
			board[p1.y][p1.x] = null;
			// Swap teams
			if (currentTeam == PieceTeam.WHITE) {
				currentTeam = PieceTeam.BLACK;
			} else if (currentTeam == PieceTeam.BLACK) {
				currentTeam = PieceTeam.WHITE;
			}
			return true;
		}
		return false;
	}

	public String boardToString() {
		String out = "";
		for (final GamePiece[] row : board) {
			for (final GamePiece g : row) {
				if (g == null) {
					out += "X";
				} else {
					// Print lowercase 'k' for knight so it doesn't conflict
					// with 'K'ing
					out += g.getType() == PieceType.KNIGHT ? 'k' : g.getType()
							.toString().charAt(0);
				}
			}
			out += "|";
		}
		return out;
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
}

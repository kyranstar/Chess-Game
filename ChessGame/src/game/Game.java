package game;

import helper.GameHelper;
import helper.Logger;

import java.awt.Point;
import java.util.Arrays;
//yo

import ai.ChessAI;

public class Game {
	public static final int SIDE_LENGTH = 8;

	private GamePiece[][] board = new GamePiece[SIDE_LENGTH][SIDE_LENGTH];
	private PieceTeam currentTeam;
	private boolean isAI;
	private ChessAI ai = new ChessAI();
	private UndoStack undoStack;
	public Move mostRecentMove;

	public Game() {
		reset();
	}

	public void clearBoard() {
		for (final GamePiece[] arr : board) {
			Arrays.fill(arr, null);
		}
	}

	public void reset() {
		undoStack = new UndoStack();
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
		for (int i = 0; i < SIDE_LENGTH; i++) {
			board[1][i] = new GamePiece(PieceType.PAWN, PieceTeam.BLACK);
		}

		// initializes white pieces by mirroring array
		for (int r = 0; r < SIDE_LENGTH / 2; r++) {
			for (int c = 0; c < SIDE_LENGTH; c++) {
				if (board[3 - r][c] != null) {
					board[4 + r][c] = new GamePiece(board[3 - r][c].getType(), PieceTeam.WHITE);
				}
			}
		}
	}

	/**
	 * is the other spot empty, if not is the piece there of the opposite team
	 * is the move legal if all true, piece is moved and true is returned
	 *
	 * @param move
	 * @return whether the move was successful
	 */
	public boolean move(final Move move) {
		final Point start = move.start;
		final Point end = move.end;

		// Make sure it's the moved piece's turn
		if (board[start.y][start.x].getTeam() != getCurrentTeam()) {
			return false;
		}
		if (!board[start.y][start.x].isLegalMove(new Move(start, end), board)) {
			return false;
		}
		if (isCheck(move, currentTeam)) {
			return false;
		}

		undoStack.doMove(this);

		getPiece(start).setHasBeenMoved(true);
		board[end.y][end.x] = board[start.y][start.x];
		board[start.y][start.x] = null;
		// Promote pawns
		if (getPiece(end).getType() == PieceType.PAWN && (end.y == 0 || end.y == SIDE_LENGTH - 1)) {
			getPiece(end).promoteToQueen();
		}

		swapTeams();
		if (isCheckMate(currentTeam)) {
			Logger.info("Check mate! Team " + currentTeam + " loses!");
		} else if (isCheck(currentTeam, board)) {
			Logger.info("Team " + currentTeam + " is in check!");
		}
		return true;
	}

	public void undo() {
		undoStack.undo(this);
	}

	final void swapTeams() {
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
					out.append('*');
				} else {
					out.append(g.getType().representation);
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

	public void setPiece(final int x, final int y, final GamePiece object) {
		board[y][x] = object;
	}

	public PieceTeam getCurrentTeam() {
		return currentTeam;
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

	public ChessAI getAiAlgorithm() {
		return ai;
	}

	public void setAiAlgorithm(final ChessAI ai) {
		this.ai = ai;
	}

	public void setBoard(final GamePiece[][] board) {
		this.board = board;
	}

	public GamePiece[][] getBoard() {
		return board;
	}

	public boolean isCheckMate(PieceTeam team) {
		for (int x = 0; x < SIDE_LENGTH; x++) {
			for (int y = 0; y < SIDE_LENGTH; y++) {
				if (board[y][x] == null) {
					continue;
				}
				if (board[y][x].getTeam() != team) {
					continue;
				}

				// For each piece on team, check if they have any legal moves
				for (int x1 = 0; x1 < SIDE_LENGTH; x1++) {
					for (int y1 = 0; y1 < SIDE_LENGTH; y1++) {
						Move move = new Move(new Point(x, y), new Point(x1, y1));
						if (board[y][x].isLegalMove(move, board)) {
							// If this move is legal, check if it gets our team
							// out of check
							if (!isCheck(move, team)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Tells whether the team would be in check after the move is done
	 *
	 * @param move
	 * @param currentTeam
	 *            the team to check if is in check
	 * @return
	 */
	public boolean isCheck(final Move move, final PieceTeam currentTeam) {
		final GamePiece[][] newBoard = GameHelper.copyBoard(getBoard());
		final Point start = move.start;
		final Point end = move.end;

		// Do move
		newBoard[start.y][start.x].setHasBeenMoved(true);
		newBoard[end.y][end.x] = board[start.y][start.x];
		newBoard[start.y][start.x] = null;

		return isCheck(currentTeam, newBoard);
	}

	private static boolean isCheck(final PieceTeam currentTeam, final GamePiece[][] newBoard) {
		final Point currentTeamKing = getKingPosition(currentTeam, newBoard);
		// see if any enemy can attack the king at the new position
		for (int x = 0; x < SIDE_LENGTH; x++) {
			for (int y = 0; y < SIDE_LENGTH; y++) {
				if (newBoard[y][x] == null || newBoard[y][x].getTeam() == currentTeam) {
					continue;
				}

				if (newBoard[y][x].isLegalMove(new Move(new Point(x, y), currentTeamKing), newBoard)) {
					return true;
				}
			}
		}

		return false;
	}

	private static Point getKingPosition(final PieceTeam team, final GamePiece[][] board) {
		for (int x = 0; x < SIDE_LENGTH; x++) {
			for (int y = 0; y < SIDE_LENGTH; y++) {
				if (board[y][x] != null && board[y][x].getTeam() == team && board[y][x].getType() == PieceType.KING) {
					return new Point(x, y);
				}
			}
		}
		throw new RuntimeException("No king found for team " + team);
	}

}

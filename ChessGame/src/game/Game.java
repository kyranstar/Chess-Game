package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.ChessAI;

public class Game {
	public static final int SIDE_LENGTH = 8;

	private GamePiece[][] board = new GamePiece[SIDE_LENGTH][SIDE_LENGTH];
	private PieceTeam currentTeam;
	private boolean isAI;
	private ChessAI ai;
	private UndoStack undoStack;

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
		if(isCheck(move, currentTeam)){
			return false;
		}
		
		if (board[start.y][start.x].isLegalMove(new Move(start, end), getPiece(end), board)) {
			undoStack.doMove(this);

			getPiece(start).setHasBeenMoved(true);
			board[end.y][end.x] = board[start.y][start.x];
			board[start.y][start.x] = null;
			swapTeams();
			return true;
		}
		return false;
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

	public void setAiAlgorithm(ChessAI ai) {
		this.ai = ai;
	}

	public void setBoard(GamePiece[][] board) {
		this.board = board;
	}

	public GamePiece[][] getBoard() {
		return board;
	}

	public boolean isCheckM8(PieceTeam team) {
		MoveSet defendingMoves = new MoveSet();
		List<MoveSet> attackingMoves = new ArrayList<>();

		for (int y1 = 0; y1 < 8; y1++) {
			for (int x1 = 0; x1 < 8; x1++) {
				for (int y2 = 0; y2 < 8; y2++) {
					for (int x2 = 0; x2 < 8; x2++) {
						if (board[y1][x1].isLegalMove(new Move(new Point(x1, y1), new Point(x2, y2)), getPiece(x2, y2), board)) {
							defendingMoves.moves.add(new Move(new Point(x1, y1), new Point(x2, y2)));
							GamePiece[][] movedBoard = Game.copyBoard(board);
							movedBoard[y2][x2] = movedBoard[y1][x1];
							movedBoard[y1][x1] = null;
							defendingMoves.boards.add(movedBoard);
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Tells whether the team would be in check after the move is done
	 * @param move
	 * @param currentTeam the team to check if is in check
	 * @return
	 */
	public boolean isCheck(Move move, PieceTeam currentTeam){
		GamePiece[][] newBoard = copyBoard(getBoard());
		Point start = move.start;
		Point end = move.end;
		
		// Do move
		newBoard[start.y][start.x].setHasBeenMoved(true);
		newBoard[end.y][end.x] = board[start.y][start.x];
		newBoard[start.y][start.x] = null;
		
		Point currentTeamKing = getKingPosition(currentTeam, newBoard);
		// see if any enemy can attack the king at the new position
		for(int x = 0; x < SIDE_LENGTH; x++){
			for(int y = 0; y < SIDE_LENGTH; y++){
				if(newBoard[y][x] == null || newBoard[y][x].getTeam() == currentTeam) continue;
				
				if(newBoard[y][x].isLegalMove(new Move(new Point(x,y), currentTeamKing), newBoard[currentTeamKing.y][currentTeamKing.x], newBoard)){
					return true;
				}
			}
		}
		
		return false;
	}

	private static Point getKingPosition(PieceTeam team, GamePiece[][] board) {
		for(int x = 0; x < SIDE_LENGTH; x++){
			for(int y = 0; y < SIDE_LENGTH; y++){
				if(board[y][x] != null && board[y][x].getTeam() == team && board[y][x].getType() == PieceType.KING){
					return new Point(x,y);
				}
			}
		}
		throw new RuntimeException("No king found for team " + team);
	}
	public static final GamePiece[][] copyBoard(GamePiece[][] board){
		GamePiece[][] arr = new GamePiece[board.length][board[0].length];
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = board[i][j] == null ? null : board[i][j].copy();
			}
		}
		return arr;
	}

}

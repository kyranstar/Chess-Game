package ai;

import game.Game;
import game.GamePiece;
import game.Move;
import game.PieceTeam;
import game.PieceType;
import helper.GameHelper;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessAI {

	@SuppressWarnings("serial")
	private static final Map<PieceType, Integer> TYPE_TO_SCORE = Collections.unmodifiableMap(new HashMap<PieceType, Integer>() {
		{
			put(PieceType.PAWN, 100);
			put(PieceType.KNIGHT, 320);
			put(PieceType.BISHOP, 330);
			put(PieceType.ROOK, 500);
			put(PieceType.QUEEN, 900);
			put(PieceType.KING, 20000);
		}
	});

	// Tables from https://chessprogramming.wikispaces.com/Simplified+evaluation+function
	//@formatter:off
	private static Integer[] PAWN_TABLE = new Integer[]
			{
		0,  0,  0,  0,  0,  0,  0,  0,
		5, 10, 10,-20,-20, 10, 10,  5,
		5, -5,-10,  0,  0,-10, -5,  5,
		0,  0,  0, 20, 20,  0,  0,  0,
		5,  5, 10, 25, 25, 10,  5,  5,
		10, 10, 20, 30, 30, 20, 10, 10,
		50, 50, 50, 50, 50, 50, 50, 50,
		0,  0,  0,  0,  0,  0,  0,  0
			};
	private static Integer[] KNIGHT_TABLE = new Integer[]
			{
		-50,-40,-30,-30,-30,-30,-40,-50,
		-40,-20,  0,  5,  5,  0,-20,-40,
		-30,  5, 10, 15, 15, 10,  5,-30,
		-30,  0, 15, 20, 20, 15,  0,-30,
		-30,  5, 15, 20, 20, 15,  5,-30,
		-30,  0, 10, 15, 15, 10,  0,-30,
		-40,-20,  0,  0,  0,  0,-20,-40,
		-50,-40,-30,-30,-30,-30,-40,-50,
			};

	private static Integer[] BISHOP_TABLE = new Integer[]
			{
		-20,-10,-10,-10,-10,-10,-10,-20,
		-10,  5,  0,  0,  0,  0,  5,-10,
		-10, 10, 10, 10, 10, 10, 10,-10,
		-10,  0, 10, 10, 10, 10,  0,-10,
		-10,  5,  5, 10, 10,  5,  5,-10,
		-10,  0,  5, 10, 10,  5,  0,-10,
		-10,  0,  0,  0,  0,  0,  0,-10,
		-20,-10,-10,-10,-10,-10,-10,-20,
			};
	private static Integer[] ROOK_TABLE = new Integer[]
			{
		0,  0,  0,  5,  5,  0,  0,  0,
		-5,  0,  0,  0,  0,  0,  0, -5,
		-5,  0,  0,  0,  0,  0,  0, -5,
		-5,  0,  0,  0,  0,  0,  0, -5,
		-5,  0,  0,  0,  0,  0,  0, -5,
		-5,  0,  0,  0,  0,  0,  0, -5,
		5, 10, 10, 10, 10, 10, 10,  5,
		0,  0,  0,  0,  0,  0,  0,  0,
			};
	private static Integer[] QUEEN_TABLE = new Integer[]
			{
		-20,-10,-10, -5, -5,-10,-10,-20,
		-10,  0,  5,  0,  0,  0,  0,-10,
		-10,  5,  5,  5,  5,  5,  0,-10,
		0,  0,  5,  5,  5,  5,  0, -5,
		-5,  0,  5,  5,  5,  5,  0, -5,
		-10,  0,  5,  5,  5,  5,  0,-10,
		-10,  0,  0,  0,  0,  0,  0,-10,
		-20,-10,-10, -5, -5,-10,-10,-20,
			};

	private static Integer[] KING_MIDDLE_GAME_TABLE = new Integer[]
			{
		20, 30, 10,  0,  0, 10, 30, 20,
		20, 20,  0,  0,  0,  0, 20, 20,
		-10,-20,-20,-20,-20,-20,-20,-10,
		-20,-30,-30,-40,-40,-30,-30,-20,
		-30,-40,-40,-50,-50,-40,-40,-30,
		-30,-40,-40,-50,-50,-40,-40,-30,
		-30,-40,-40,-50,-50,-40,-40,-30,
		-30,-40,-40,-50,-50,-40,-40,-30,
			};

	private static Integer[] KING_END_GAME_TABLE = new Integer[]
			{
		-50,-30,-30,-30,-30,-30,-30,-50,
		-30,-30,  0,  0,  0,  0,-30,-30,
		-30,-10, 20, 30, 30, 20,-10,-30,
		-30,-10, 30, 40, 40, 30,-10,-30,
		-30,-10, 30, 40, 40, 30,-10,-30,
		-30,-10, 20, 30, 30, 20,-10,-30,
		-30,-20,-10,  0,  0,-10,-20,-30,
		-50,-40,-30,-20,-20,-30,-40,-50,
			};
	//@formatter:on
	@SuppressWarnings("serial")
	private static final Map<PieceType, Integer[]> TYPE_TO_TABLE = Collections.unmodifiableMap(new HashMap<PieceType, Integer[]>() {
		{
			put(PieceType.PAWN, PAWN_TABLE);
			put(PieceType.KNIGHT, /* Round ;) */KNIGHT_TABLE);
			put(PieceType.BISHOP, BISHOP_TABLE);
			put(PieceType.ROOK, ROOK_TABLE);
			put(PieceType.QUEEN, QUEEN_TABLE);
			// TODO End game too
			put(PieceType.KING, KING_MIDDLE_GAME_TABLE);
		}
	});

	/**
	 *
	 * @param game
	 * @return the next move to carry out
	 */
	public Move getNextMove(final Game game) {
		return getNextMove(game, 3);
	}

	private Move getNextMove(final Game game, final int ply) {
		final List<Move> validMoves = GameHelper.generateMoves(game, PieceTeam.BLACK);
		validMoves.sort((o1, o2) -> {
			final GamePiece[][] before = game.getBoard();
			final GamePiece[][] first = GameHelper.copyBoard(game.getBoard());
			final GamePiece[][] second = GameHelper.copyBoard(game.getBoard());

			doMove(first, o1);
			doMove(second, o2);

			// best moves lowest
			return evaluate(before, second) - evaluate(before, first);
		});
		return validMoves.get(0);
	}

	private static void doMove(final GamePiece[][] board, final Move move) {
		final Point start = move.start;
		final Point end = move.end;

		board[start.y][start.x].setHasBeenMoved(true);
		board[end.y][end.x] = board[start.y][start.x];
		board[start.y][start.x] = null;
		// Promote pawns
		if (board[end.y][end.x].getType() == PieceType.PAWN && (end.y == 0 || end.y == Game.SIDE_LENGTH - 1)) {
			board[end.y][end.x].promoteToQueen();
		}
	}

	/**
	 * Returns an evaluation score of how the second board is relative to the first. Higher is better, lower is worse.
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	private static int evaluate(final GamePiece[][] first, final GamePiece[][] second) {
		return material(first, second) + pieceSquareTables(first, second);
	}

	private static int material(final GamePiece[][] first, final GamePiece[][] second) {
		return material(second) - material(first);
	}

	private static int material(final GamePiece[][] board) {
		int score = 0;
		for (final GamePiece[] col : board) {
			for (final GamePiece piece : col) {
				if (piece == null) {
					continue;
				}
				if (piece.getTeam() == PieceTeam.BLACK) {
					score += TYPE_TO_SCORE.get(piece.getType());
				} else {
					score -= TYPE_TO_SCORE.get(piece.getType());
				}
			}
		}
		return score;
	}

	private static int pieceSquareTables(final GamePiece[][] first, final GamePiece[][] second) {
		return pieceSquareTables(second) - pieceSquareTables(first);
	}

	private static int pieceSquareTables(final GamePiece[][] board) {
		int score = 0;
		for (int x = 0; x < Game.SIDE_LENGTH; x++) {
			for (int y = 0; y < Game.SIDE_LENGTH; y++) {
				final GamePiece piece = board[y][x];
				if (piece == null) {
					continue;
				}
				if (piece.getTeam() == PieceTeam.BLACK) {
					score += TYPE_TO_TABLE.get(piece.getType())[y * 8 + x];
				} else {
					score -= TYPE_TO_TABLE.get(piece.getType())[y * 8 + x];
				}
			}
		}
		return score;
	}
}

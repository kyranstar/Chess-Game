package game;

import static java.lang.Math.abs;

public enum PieceType {
	PAWN('p', (piece, pieceOnEndTile, board, move) -> {
		final PieceTeam team = piece.getTeam();
		final int dx = move.getdx();
		int dy = move.getdy();
		final boolean willKill = pieceOnEndTile != null && pieceOnEndTile.getTeam() != team;
		int direction = -1; // to distinguish between the dy of black and white
			if (team == PieceTeam.BLACK) {
				direction = 1; // for black pawns, dy is positive, for white
			// it's
			// negative
		}
		dy = dy * direction;
		final boolean isKilling = willKill && dy == 1 && abs(dx) == 1;
		final boolean isMoving1 = dy == 1 && dx == 0 && pieceOnEndTile == null;
		final boolean isMoving2 = dy == 2 && dx == 0 && pieceOnEndTile == null && !piece.hasBeenMoved();

		return isKilling || isMoving1 || isMoving2;

	}),
	BISHOP('b', (piece, pieceOnEndTile, board, move) -> {
		final PieceTeam team = piece.getTeam();
		final int dx = move.getdx();
		final int dy = move.getdy();
		return abs(dy) == abs(dx) && tileIsEmptyOrEnemy(pieceOnEndTile, team) && !pieceInWay(board, move);
	}),
	KNIGHT('k', (piece, pieceOnEndTile, board, move) -> {
		final PieceTeam team = piece.getTeam();
		final int dx = move.getdx();
		final int dy = move.getdy();
		final boolean isCorrectMove = (abs(dy) == abs(2 * dx) || abs(dx) == abs(2 * dy)) && abs(dy) <= 2 && abs(dx) <= 2;

		return isCorrectMove && tileIsEmptyOrEnemy(pieceOnEndTile, team);
	}),
	ROOK('r',
			(piece, pieceOnEndTile, board, move) -> {
				final PieceTeam team = piece.getTeam();
				final int dx = move.getdx();
				final int dy = move.getdy();
				return (abs(dy) >= 1 && abs(dx) == 0 || abs(dx) >= 1 && abs(dy) == 0) && tileIsEmptyOrEnemy(pieceOnEndTile, team)
						&& !pieceInWay(board, move);
			}),
	QUEEN('Q', (piece, pieceOnEndTile, board, move) -> {
		final PieceTeam team = piece.getTeam();
		final int dx = move.getdx();
		final int dy = move.getdy();
		return (abs(dy) == abs(dx) || abs(dy) >= 1 && abs(dx) == 0 || abs(dx) >= 1 && abs(dy) == 0) && tileIsEmptyOrEnemy(pieceOnEndTile, team)
				&& !pieceInWay(board, move);
	}),
	KING('K', (piece, pieceOnEndTile, board, move) -> {
		final PieceTeam team = piece.getTeam();
		final int dx = move.getdx();
		final int dy = move.getdy();
		return abs(dy) <= 1 && abs(dx) <= 1 && tileIsEmptyOrEnemy(pieceOnEndTile, team);
	});

	public final IsLegalMove isLegalMove;
	public final char representation;

	private PieceType(final char representation, final IsLegalMove isLegalMove) {
		this.isLegalMove = isLegalMove;
		this.representation = representation;
	}

	private static boolean tileIsEmptyOrEnemy(final GamePiece piece, final PieceTeam currentTeam) {
		return piece == null || piece.getTeam() != currentTeam;
	}

	public static boolean pieceInWay(final GamePiece[][] board, final Move move) {
		final int changerx = (int) Math.signum(move.getdx());
		final int changery = (int) Math.signum(move.getdy());
		switch (Direction.getFromDelta(changerx, changery)) {
		case DOWN:
			for (int y = move.start.y + 1; y < move.end.y; y++) {
				if (board[y][move.start.x] != null) {
					return true;
				}
			}
			break;
		case DOWN_LEFT:
			for (int y = move.start.y + 1, x = move.start.x - 1; y < move.end.y; y++, x--) {
				if (board[y][x] != null) {
					return true;
				}
			}
			break;
		case DOWN_RIGHT:
			for (int y = move.start.y + 1, x = move.start.x + 1; y < move.end.y; y++, x++) {
				if (board[y][x] != null) {
					return true;
				}
			}
			break;
		case LEFT:
			for (int x = move.start.x - 1; x > move.end.x; x--) {
				if (board[move.start.y][x] != null) {
					return true;
				}
			}
			break;
		case RIGHT:
			for (int x = move.start.x + 1; x < move.end.x; x++) {
				if (board[move.start.y][x] != null) {
					return true;
				}
			}
			break;
		case UP:
			for (int y = move.start.y - 1; y > move.end.y; y--) {
				if (board[y][move.start.x] != null) {
					return true;
				}
			}
			break;
		case UP_LEFT:
			for (int y = move.start.y - 1, x = move.start.x - 1; y > move.end.y; y--, x--) {
				if (board[y][x] != null) {
					return true;
				}
			}
			break;
		case UP_RIGHT:
			for (int y = move.start.y - 1, x = move.start.x + 1; y > move.end.y; y--, x++) {
				if (board[y][x] != null) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	@FunctionalInterface
	public static interface IsLegalMove {
		public boolean call(GamePiece piece, GamePiece pieceOnEndTile, GamePiece[][] board, Move move);
	}

	private static enum Direction {
		UP(0, -1),
		UP_RIGHT(1, -1),
		RIGHT(1, 0),
		DOWN_RIGHT(1, 1),
		DOWN(0, 1),
		DOWN_LEFT(-1, 1),
		LEFT(-1, 0),
		UP_LEFT(-1, -1);

		public final int dx;
		public final int dy;

		private Direction(final int dx, final int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		public static Direction getFromDelta(final int dx, final int dy) {
			for (final Direction d : values()) {
				if (d.dx == dx && d.dy == dy) {
					return d;
				}
			}
			return null;
		}
	}
}

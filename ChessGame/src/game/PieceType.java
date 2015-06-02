package game;

import static java.lang.Math.abs;

public enum PieceType {
	PAWN('p', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		final boolean willKill = pieceOnEndTile != null && pieceOnEndTile.getTeam() != team;
		int direction = -1; // to distinguish between the dy of black and white
		if (team == PieceTeam.BLACK) {
			direction = 1; // for black pawns, dy is positive, for white it's
							// negative
		}
		dy = dy * direction;
		boolean isKilling = willKill && dy == 1 && abs(dx) == 1;
		boolean isMoving1 = dy == 1 && dx == 0 && pieceOnEndTile == null;
		boolean isMoving2 = dy == 2 && dx == 0 && pieceOnEndTile == null && !piece.hasBeenMoved();

		return isKilling || isMoving1 || isMoving2;

	}), BISHOP('b', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		return abs(dy) == abs(dx) && tileIsEmptyOrEnemy(pieceOnEndTile, team) && !pieceInWay(board, move, team);
	}), KNIGHT('k', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		boolean isCorrectMove = (abs(dy) == abs(2 * dx) || (abs(dx) == abs(2 * dy))) && abs(dy) <= 2 && abs(dx) <= 2;

		return isCorrectMove && tileIsEmptyOrEnemy(pieceOnEndTile, team);
	}), ROOK('r', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		return (abs(dy) >= 1 && abs(dx) == 0 || abs(dx) >= 1 && abs(dy) == 0) && tileIsEmptyOrEnemy(pieceOnEndTile, team) && !pieceInWay(board, move, team);
	}), QUEEN('Q', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		return (abs(dy) == abs(dx) || abs(dy) >= 1 && abs(dx) == 0 || abs(dx) >= 1 && abs(dy) == 0) && tileIsEmptyOrEnemy(pieceOnEndTile, team) && !pieceInWay(board, move, team);
	}), KING('K', (piece, pieceOnEndTile, board, move) -> {
		PieceTeam team = piece.getTeam();
		int dx = move.getdx();
		int dy = move.getdy();
		return abs(dy) <= 1 && abs(dx) <= 1 && tileIsEmptyOrEnemy(pieceOnEndTile, team);
	});

	public final IsLegalMove isLegalMove;
	public final char representation;

	private PieceType(final char representation, final IsLegalMove isLegalMove) {
		this.isLegalMove = isLegalMove;
		this.representation = representation;
	}

	private static boolean tileIsEmptyOrEnemy(GamePiece piece, PieceTeam currentTeam) {
		return piece == null || piece.getTeam() != currentTeam;
	}

	// This function doesn't work????
	private static boolean pieceInWay(GamePiece[][] board, Move move, PieceTeam team) {
		int changerx = (int) Math.signum(move.getdx());
		int changery = (int) Math.signum(move.getdy());
		for (int x = move.start.x + changerx, y = move.start.y + changery; x < move.end.x; x += changerx, y += changery) {
			if (board[y][x] != null) {
				return true;
			}
		}
		return false;
	}

	@FunctionalInterface
	public static interface IsLegalMove {
		public boolean call(GamePiece piece, GamePiece pieceOnEndTile, GamePiece[][] board, Move move);
	}

}

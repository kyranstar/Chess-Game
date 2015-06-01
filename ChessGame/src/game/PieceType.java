package game;

public enum PieceType {
	PAWN('p', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		final boolean canKill = pieceOnEndTile != null
				&& pieceOnEndTile.getTeam() != team;
		return dx == 1 && dy == 1 && canKill || dx <= 1 && dy <= 1;
	}), BISHOP('b', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		return dy == dx && pieceInWay(board, move, team);
	}), KNIGHT('k', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		return (dy == 2 * dx || dx == 2 * dy) && dy <= 2 && dx <= 2;
	}), ROOK('r', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		return (dy >= 1 && dx == 0 || dx >= 1 && dy == 0)
				&& pieceInWay(board, move, team);
	}), QUEEN('Q', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		return (dy == dx || dy >= 1 && dx == 0 || dx >= 1 && dy == 0)
				&& pieceInWay(board, move, team);
	}), KING('K', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		return dy <= 1 && dx <= 1;
	});

	public final IsLegalMove isLegalMove;
	public final char representation;

	private PieceType(final char representation, final IsLegalMove isLegalMove) {
		this.isLegalMove = isLegalMove;
		this.representation = representation;
	}

	private static boolean pieceInWay(GamePiece[][] board, Move move,
			PieceTeam team) {
		int dx = move.end.x - move.start.x;
		int dy = move.end.y - move.start.y;
		int changerx = (int) Math.signum(dx);
		int changery = (int) Math.signum(dy);
		for (int x = move.start.x + changerx, y = move.start.y + changery; x < move.end.x; x += changerx, y += changery) {
			if (board[x][y] != null) {
				return false;
			}
		}
		return true;
	}

	@FunctionalInterface
	public static interface IsLegalMove {
		public boolean call(PieceTeam team, GamePiece pieceOnEndTile,
				GamePiece[][] board, Move move);
	}

}

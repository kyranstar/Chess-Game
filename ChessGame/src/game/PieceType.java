package game;

public enum PieceType {
	PAWN('p', (dx, dy, team, pieceOnEndTile) -> {
		final boolean canKill = pieceOnEndTile != null && pieceOnEndTile.getTeam() != team;
		return dx == 1 && dy == 1 && canKill || dx <= 1 && dy <= 1;
	}),
	BISHOP('b', (dx, dy, team, pieceOnEndTile) -> {
		return dy == dx;
	}),
	KNIGHT('k', (dx, dy, team, pieceOnEndTile) -> {
		return (dy == 2 * dx || dx == 2 * dy) && dy <= 2 && dx <= 2;
	}),
	ROOK('r', (dx, dy, team, pieceOnEndTile) -> {
		return dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
	}),
	QUEEN('Q', (dx, dy, team, pieceOnEndTile) -> {
		return dy == dx || dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
	}),
	KING('K', (dx, dy, team, pieceOnEndTile) -> {
		return dy <= 1 && dx <= 1;
	});

	public final IsLegalMove isLegalMove;
	public final char representation;

	private PieceType(final char representation, final IsLegalMove isLegalMove) {
		this.isLegalMove = isLegalMove;
		this.representation = representation;
	}

	@FunctionalInterface
	public static interface IsLegalMove {
		public boolean call(int dx, int dy, PieceTeam team, GamePiece pieceOnEndTile);
	}

}

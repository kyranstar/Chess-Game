package game;

public enum PieceType {
	PAWN((dx, dy, team, isKillingOtherPiece) -> {
		return dx == 1 && dy == 1 && isKillingOtherPiece || dx <= 1 && dy <= 1;
	}),
	BISHOP((dx, dy, team, isKillingOtherPiece) -> {
		return dy == dx;
	}),
	KNIGHT((dx, dy, team, isKillingOtherPiece) -> {
		return (dy == 2 * dx || dx == 2 * dy) && dy <= 2 && dx <= 2;
	}),
	ROOK((dx, dy, team, isKillingOtherPiece) -> {
		return dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
	}),
	QUEEN((dx, dy, team, isKillingOtherPiece) -> {
		return dy == dx || dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
	}),
	KING((dx, dy, team, isKillingOtherPiece) -> {
		return dy <= 1 && dx <= 1;
	});

	public final IsLegalMove isLegalMove;

	private PieceType(final IsLegalMove isLegalMove) {
		this.isLegalMove = isLegalMove;
	}

	@FunctionalInterface
	public static interface IsLegalMove {
		public boolean call(int dx, int dy, PieceTeam team, boolean isKillingPiece);
	}

}

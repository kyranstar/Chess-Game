package game;

import java.awt.Point;

public class GamePiece {
	private final PieceType type; // 0-pawn, 1-bishop, 2-knight, 3-rook, 4-queen, 5-king
	private final PieceTeam team; // 0 = white, 1 = black

	public GamePiece(final PieceType type, final PieceTeam team) {
		this.type = type;
		this.team = team;
	}

	public boolean isLegalMove(final Point start, final Point end, final boolean isKillingOtherPiece) {
		final int dx = end.y - start.y;
		final int dy = end.x - start.x;

		switch (type) {
		case PAWN: // pawn
			return dx == 1 && dy == 1 && isKillingOtherPiece || dx <= 1 && dy <= 1;
		case BISHOP: // bishop
			return dy == dx;
		case KNIGHT: // knight
			return (dy == 2 * dx || dx == 2 * dy) && dy <= 2 && dx <= 2;
		case ROOK: // rook
			return dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
		case QUEEN: // queen
			return dy == dx || dy >= 1 && dx == 0 || dx >= 1 && dy == 0;
		case KING:
			return dy <= 1 && dx <= 1;
		default:
			return false;
		}
	}

	public PieceType getType() {
		return type;
	}

	public PieceTeam getTeam() {
		return team;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (team == null ? 0 : team.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GamePiece other = (GamePiece) obj;
		if (team != other.team) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

}

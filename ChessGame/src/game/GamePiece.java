package game;


public class GamePiece {
	private final PieceType type;
	private final PieceTeam team;

	public GamePiece(final PieceType type, final PieceTeam team) {
		this.type = type;
		this.team = team;
	}

	public boolean isLegalMove(final Move move, final GamePiece pieceOnEndTile) {
		return type.isLegalMove.call(move.end.x - move.start.x, move.end.y - move.start.y, team, pieceOnEndTile);
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

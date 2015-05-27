package game;

import java.awt.Point;

public class GamePiece {
	private int type; // 0-pawn, 1-bishop, 2-knight, 3-rook, 4-queen, 5-king
	private boolean team; // 0 = white, 1 = black

	public GamePiece(int type, boolean team) {
		this.type = type;
		this.team = team;
	}

	public boolean isLegalMove(Point start, Point end,
			boolean isKillingOtherPiece) {
		int dx = end.y - start.y;
		int dy = end.x - start.x;

		switch (type) {
		case 0: // pawn
			return (dx == 1 && dy == 1 && isKillingOtherPiece)
					|| (dx <= 1 && dy <= 1);
		case 1: // bishop
			return dy == dx;
		case 2: // knight
			return (dy == 2 * dx || dx == 2 * dy) && dy <= 2 && dx <= 2;
		case 3: // rook
			return (dy >= 1 && dx == 0) || (dx >= 1 && dy == 0);
		case 4: // queen
			return (dy == dx) || ((dy >= 1 && dx == 0) || (dx >= 1 && dy == 0));
		case 5:
			return (dy <= 1 && dx <= 1);
		default:
			return false;
		}
	}

	public int getType() {
		return type;
	}

	public boolean getTeam() {
		return team;
	}
}

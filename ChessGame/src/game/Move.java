package game;

import java.awt.Point;

/**
 * Represents a move from one square to another.
 *
 * @author Kyran Adams
 *
 */
public class Move {
	public Point start;
	public Point end;

	public Move(final Point start, final Point end) {
		this.start = start;
		this.end = end;
	}

	public Move getInverse() {
		return new Move(end, start);
	}
}

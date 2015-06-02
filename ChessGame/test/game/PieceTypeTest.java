package game;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

public class PieceTypeTest {

	@Test
	public void test() {
		Game g = new Game();
		assertTrue(PieceType.pieceInWay(g.getBoard(), new Move(new Point(0, 7), new Point(3, 7))));
	}

}

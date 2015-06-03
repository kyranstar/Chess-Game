package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PieceTypeTest {

	private static String fileTail = "white\ntrue";

	@Test
	public void testPawn() throws IOException {
		final String board = //
		"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"**rB************\n" + //
				"**pW************\n" + //
				"****************\n" + fileTail;//
		assertIsValid(false, board, new Point(1, 6), Arrays.asList(new Point(0, 7), new Point(0, 6), new Point(0, 5), new Point(0, 4),
				new Point(1, 7), new Point(1, 6), new Point(1, 5), new Point(0, 4), new Point(2, 7), new Point(2, 6), new Point(2, 5),
				new Point(0, 4)));
	}

	@Test
	public void testRook() throws IOException {
		final String board = "****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + fileTail;
	}

	@Test
	public void testKnight() throws IOException {
		final String board = "****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + fileTail;
	}

	@Test
	public void testBishop() throws IOException {
		final String board = "****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + fileTail;
	}

	@Test
	public void testQueen() throws IOException {
		final String board = "****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + fileTail;
	}

	@Test
	public void testKing() throws IOException {
		final String board = "****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + //
				"****************\n" + fileTail;
	}

	private void assertIsValid(final boolean assertTrue, final String board, final Point piece, final List<Point> places) throws IOException {
		final Game g = GameSerializer.load(new ByteArrayInputStream(board.getBytes()));
		for (final Point place : places) {
			final boolean isLegal = g.getPiece(piece).isLegalMove(new Move(new Point(piece), new Point(place)), g.getPiece(place), g.getBoard());
			if (assertTrue) {
				assertTrue(isLegal);
			} else {
				assertFalse(isLegal);
			}
		}
	}
}

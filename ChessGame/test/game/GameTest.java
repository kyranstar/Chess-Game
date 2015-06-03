package game;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class GameTest {

	@Test
	public void testWhiteTeamFirst() {
		final Game game = new Game();
		assertEquals(PieceTeam.WHITE, game.getCurrentTeam());
	}

	@Test
	public void testBoardInitialization() {
		final Game game = new Game();
		assertEquals(PieceType.ROOK, game.getPiece(0, 0).getType());
		assertEquals(PieceType.KNIGHT, game.getPiece(1, 0).getType());
		assertEquals(PieceType.BISHOP, game.getPiece(2, 0).getType());
		assertEquals(PieceType.QUEEN, game.getPiece(3, 0).getType());
		assertEquals(PieceType.KING, game.getPiece(4, 0).getType());
		assertEquals(PieceType.BISHOP, game.getPiece(5, 0).getType());
		assertEquals(PieceType.KNIGHT, game.getPiece(6, 0).getType());
		assertEquals(PieceType.ROOK, game.getPiece(7, 0).getType());

		assertEquals(PieceType.ROOK, game.getPiece(0, 7).getType());
		assertEquals(PieceType.KNIGHT, game.getPiece(1, 7).getType());
		assertEquals(PieceType.BISHOP, game.getPiece(2, 7).getType());
		assertEquals(PieceType.QUEEN, game.getPiece(3, 7).getType());
		assertEquals(PieceType.KING, game.getPiece(4, 7).getType());
		assertEquals(PieceType.BISHOP, game.getPiece(5, 7).getType());
		assertEquals(PieceType.KNIGHT, game.getPiece(6, 7).getType());
		assertEquals(PieceType.ROOK, game.getPiece(7, 7).getType());

		for (int i = 0; i < 8; i++) {
			assertEquals(PieceType.PAWN, game.getPiece(i, 1).getType());
			assertEquals(PieceType.PAWN, game.getPiece(i, 6).getType());
		}
	}

	@Test
	public void checkMateTest() throws IOException {
		final Game g = GameSerializer.load(new ByteArrayInputStream(//
				("rBkBbBQBKBbBkBrB\n" + //
				"pBpBpBpBpB******\n" + //
				"************QWpB\n" + //
				"**********pB****\n" + //
				"********pW******\n" + //
				"**********kW****\n" + //
				"pWpWpWpW**pWpWpW\n" + //
				"rWkWbW**KWbW**rW\n" + //
				"BLACK\n" + //
				"false").getBytes()));
		assertTrue(g.isCheckMate(PieceTeam.BLACK));
	}
}

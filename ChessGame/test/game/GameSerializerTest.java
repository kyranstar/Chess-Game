package game;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class GameSerializerTest extends GameSerializer {

	@Test
	public void testLoad() throws IOException {
		final String file = "pWQBrWNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"NNNNNNNNNNNNNNNN\n" + //
				"white\n" + // white team currently
				"true"; // ai true
		final Game game = GameSerializer.load(new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8)));
		assertEquals(new GamePiece(PieceType.PAWN, PieceTeam.WHITE), game.getPiece(0, 0));
		assertEquals(new GamePiece(PieceType.QUEEN, PieceTeam.BLACK), game.getPiece(1, 0));
		assertEquals(new GamePiece(PieceType.ROOK, PieceTeam.WHITE), game.getPiece(2, 0));
	}

	@Test
	public void testSave() throws UnsupportedEncodingException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);

		final Game game = new Game();
		GameSerializer.save(game, ps);

		final String[] content = baos.toString("UTF-8").replaceAll("\r", "").split("\n");
		assertEquals(10, content.length);
		assertEquals("rBkBbBQBKBbBkBrB", content[0]);
		assertEquals("pBpBpBpBpBpBpBpB", content[1]);

		for (int i = 2; i < 6; i++) {
			assertEquals("NNNNNNNNNNNNNNNN", content[i]);
		}

		assertEquals("pWpWpWpWpWpWpWpW", content[6]);
		assertEquals("rWkWbWQWKWbWkWrW", content[7]);

		assertEquals("WHITE", content[8]);
		assertEquals("false", content[9]);

	}

	@Test
	public void testBasicSaveAndLoad() throws IOException {
		testSaveAndLoad(new Game());
	}

	@Test
	public void testMovedSaveAndLoad() throws IOException {
		final Game game = new Game();
		game.move(new Move(new Point(0, 6), new Point(0, 5)));
		testSaveAndLoad(game);
	}

	private static void testSaveAndLoad(final Game game) throws IOException {
		final OutputStream output = new ByteArrayOutputStream();

		GameSerializer.save(game, output);

		final InputStream input = new ByteArrayInputStream(output.toString().getBytes(StandardCharsets.UTF_8));
		final Game newGame = GameSerializer.load(input);

		for (int y = 0; y < Game.SIDE_LENGTH; y++) {
			for (int x = 0; x < Game.SIDE_LENGTH; x++) {
				assertEquals(game.getPiece(x, y), newGame.getPiece(x, y));
			}
		}
		assertEquals(game.getCurrentTeam(), newGame.getCurrentTeam());
		assertEquals(game.isAI(), newGame.isAI());
	}

}

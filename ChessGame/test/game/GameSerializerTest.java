package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class GameSerializerTest extends GameSerializer {

	@Test
	public void testLoad() {
		fail("Not yet implemented");
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
}

package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class GameSerializer {

	private static final char NULL = 'N';

	// N = null

	public static void save(final Game game, final OutputStream outputStream) {
		final PrintWriter out = new PrintWriter(outputStream);

		for (int i = 0; i < Game.SIDE_LENGTH; i++) {
			for (int j = 0; j < Game.SIDE_LENGTH; j++) {
				if (game.getPiece(j, i) == null) {
					// NN
					out.print(String.valueOf(NULL) + NULL);
					continue;
				}
				final char type = game.getPiece(j, i).getType().representation;
				final char team = game.getPiece(j, i).getTeam().toString().charAt(0);

				out.print(String.valueOf(type) + String.valueOf(team));
			}
			out.print('\n');
		}
		out.println(game.getCurrentTeam());
		out.print(game.isAI());
		out.close();
	}

	public static Game load(final File file) throws IOException {
		final List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		assert lines.size() == 10;
		return load(lines);
	}

	public static Game load(final InputStream input) throws IOException {

		final List<String> lines = Arrays.asList(getStringFromInputStream(input).split("\n"));
		assert lines.size() == 10;
		return load(lines);
	}

	private static Game load(final List<String> lines) {
		final Game game = new Game();
		game.clearBoard();
		// load board
		for (int i = 0; i < Game.SIDE_LENGTH; i++) {
			for (int j = 0; j < Game.SIDE_LENGTH * 2; j += 2) {
				// type then team
				final PieceTeam team = Character.toLowerCase(lines.get(i).charAt(j + 1)) == 'w' ? PieceTeam.WHITE : PieceTeam.BLACK;
				final char pieceChar = lines.get(i).charAt(j);
				if (pieceChar == NULL) {
					continue;
				}

				GamePiece piece = null;
				for (final PieceType type : PieceType.values()) {
					if (type.representation == pieceChar) {
						piece = new GamePiece(type, team);
					}
				}
				if (piece == null) {
					throw new RuntimeException("Unrecognized character found: " + pieceChar);
				}
				game.setPiece(j / 2, i, piece);
			}
		}
		final char currentTeam = Character.toLowerCase(lines.get(8).charAt(0));
		game.setCurrentTeam(currentTeam == 'w' ? PieceTeam.WHITE : PieceTeam.BLACK);
		game.setAI(Boolean.parseBoolean(lines.get(9)));

		return game;
	}

	/**
	 * Lossy way to convert an input stream to a string(changes any type of line ending to \n)
	 *
	 * @param input
	 * @return string form of input
	 */
	private static String getStringFromInputStream(final InputStream input) {

		BufferedReader br = null;
		final StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(input));
			while ((line = br.readLine()) != null) {
				sb.append(line + '\n');
			}

		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}

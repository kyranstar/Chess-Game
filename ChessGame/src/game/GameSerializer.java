package game;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class GameSerializer {

	private static final char NULL = 'N';

	// N = null

	public static void save(final Game game, final OutputStream outputStream) {
		final PrintWriter out = new PrintWriter(outputStream);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
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

	private static Game load(final List<String> lines) {
		final Game game = new Game();
		game.clearBoard();
		// load board
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 16; j += 2) {
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
				game.setPiece(j, i, piece);
			}
		}
		final char currentTeam = Character.toLowerCase(lines.get(8).charAt(0));
		game.setCurrentTeam(currentTeam == 'w' ? PieceTeam.WHITE : PieceTeam.BLACK);
		game.setAI(Boolean.parseBoolean(lines.get(9)));

		return game;
	}
}

package helper;

import game.GamePiece;
import game.PieceTeam;
import game.PieceType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class GraphicsConstants. Holds the IDE default colors and fonts.
 */
public final class GraphicsConstants {
	// Constants class, can't be instantiated
	private GraphicsConstants() {
	}

	private static final Map<GamePiece, BufferedImage> pieceToImage = new HashMap<GamePiece, BufferedImage>() {
		{
			put(new GamePiece(PieceType.PAWN, PieceTeam.WHITE), loadImage("white_pawn"));
			put(new GamePiece(PieceType.ROOK, PieceTeam.WHITE), loadImage("white_rook"));
			put(new GamePiece(PieceType.KNIGHT, PieceTeam.WHITE), loadImage("white_knight"));
			put(new GamePiece(PieceType.BISHOP, PieceTeam.WHITE), loadImage("white_bishop"));
			put(new GamePiece(PieceType.QUEEN, PieceTeam.WHITE), loadImage("white_queen"));
			put(new GamePiece(PieceType.KING, PieceTeam.WHITE), loadImage("white_king"));

			put(new GamePiece(PieceType.PAWN, PieceTeam.BLACK), loadImage("black_pawn"));
			put(new GamePiece(PieceType.ROOK, PieceTeam.BLACK), loadImage("black_rook"));
			put(new GamePiece(PieceType.KNIGHT, PieceTeam.BLACK), loadImage("black_knight"));
			put(new GamePiece(PieceType.BISHOP, PieceTeam.BLACK), loadImage("black_bishop"));
			put(new GamePiece(PieceType.QUEEN, PieceTeam.BLACK), loadImage("black_queen"));
			put(new GamePiece(PieceType.KING, PieceTeam.BLACK), loadImage("black_king"));
		}
	};

	public static final BufferedImage getImage(final GamePiece piece) {
		return pieceToImage.get(piece);
	}

	public static final BufferedImage getImage(final PieceType type, final PieceTeam team) {
		return pieceToImage.get(new GamePiece(type, team));
	}

	private static BufferedImage loadImage(final String s) {
		try {
			return GraphicsUtils.loadImage('/' + s + ".png");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("IO EXCEPTION ON FILE: " + s);
	}
}

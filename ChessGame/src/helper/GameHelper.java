package helper;

import game.Game;
import game.GamePiece;
import game.Move;
import game.PieceTeam;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class GameHelper {
	private GameHelper() {
	}

	public static List<Move> generateMoves(final Game game, final PieceTeam team) {
		final List<Move> moves = new ArrayList<>();
		for (int x = 0; x < Game.SIDE_LENGTH; x++) {
			for (int y = 0; y < Game.SIDE_LENGTH; y++) {
				if (game.getPiece(x, y) == null) {
					continue;
				}
				if (game.getPiece(x, y).getTeam() != team) {
					continue;
				}

				// For each piece on team, check if they have any legal moves
				for (int x1 = 0; x1 < Game.SIDE_LENGTH; x1++) {
					for (int y1 = 0; y1 < Game.SIDE_LENGTH; y1++) {
						final Move move = new Move(new Point(x, y), new Point(x1, y1));
						if (game.getPiece(x, y).isLegalMove(move, game.getPiece(x1, y1), game.getBoard())) {
							// If this move is legal, check if it gets our team
							// out of check
							if (!game.isCheck(move, team)) {
								moves.add(move);
							}
						}
					}
				}
			}
		}
		return moves;
	}

	public static GamePiece[][] copyBoard(final GamePiece[][] board) {
		final GamePiece[][] arr = new GamePiece[board.length][board[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = board[i][j] == null ? null : board[i][j].copy();
			}
		}
		return arr;
	}
}

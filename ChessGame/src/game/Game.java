package game;

import java.awt.Point;

public class Game {
	private GamePiece[][] board = new GamePiece[8][8];
	private int Scorep1 = 0; // player 1 score
	private int Scorep2 = 0; // player 2 score

	public Game() {
		// initialize pieces on board
		board[0][0] = new GamePiece(3, false); // white rook
		board[0][1] = new GamePiece(2, false); // white knight
		board[0][2] = new GamePiece(1, false); // white bishop
		board[0][3] = new GamePiece(4, false); // white queen

	}

	public boolean move(final Point p1, final Point p2) {
		return false;
	}
}

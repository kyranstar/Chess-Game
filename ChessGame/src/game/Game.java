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
		board[0][4] = new GamePiece(5, false); // white king
		board[0][5] = new GamePiece(1, false); // white bishop
		board[0][6] = new GamePiece(2, false); // white knight
		board[0][7] = new GamePiece(3, false); // white rook
		for(int i = 0; i < 8; i++){
			board[1][i] = new GamePiece(0, false); //white pawns
		}
		
		//initializes black pieces by mirroring array
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < 8; c++){
				if(board[3-r][c] != null){
					board[4+r][c] = new GamePiece(board[3-r][c].getType(), true);
				}
			}
		}
		//switch black king/queen
		GamePiece temp = board[7][3];
		board[7][3] = board[7][4];
		board[7][4] = temp;
		
		printBoard();
	}

	public boolean move(final Point p1, final Point p2, final boolean isKillingAPiece) {
		if(board[p1.y][p1.x].isLegalMove(p1, p2, isKillingAPiece)){
			
		}
	}
	
	public void printBoard(){
		for(GamePiece[] row : board){
			for(GamePiece g : row){
				if(g == null){
					System.out.print("X ");
				}else{
					System.out.print(g.getType() + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}

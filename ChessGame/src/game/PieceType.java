package game;
 
import static java.lang.Math.abs;
import helper.Logger;
 
public enum PieceType {
   	PAWN('p', (team, pieceOnEndTile, board, move) -> {
		int dx = move.getdx();
		int dy = move.getdy();
		final boolean canKill = pieceOnEndTile != null
				&& pieceOnEndTile.getTeam() != team;
        int direction = -1; //to distinguish between the dy of black and white
        			if(team == PieceTeam.BLACK){
        					direction = 1; //for black pawns, dy is positive, for white it's negative
                    }
                       	dy = dy*direction;
                       	return (canKill && dy == 1 && abs(dx) == 1) || 
                       		   (dy == 2 && dx == 0 && (pieceOnEndTile == null || pieceOnEndTile
                                    	.getTeam() != team)) ||
                       		   (dy == 1 && dx == 0 && (pieceOnEndTile == null || pieceOnEndTile
                                    	.getTeam() != team));
     	
                 	}), BISHOP('b', (team, pieceOnEndTile, board, move) -> {
						int dx = move.getdx();
						int dy = move.getdy();
                       	return abs(dy) == abs(dx) && (pieceOnEndTile == null || pieceOnEndTile
                             	.getTeam() != team) && (pieceInWay(board, move, team));
                 	}), KNIGHT('k', (team, pieceOnEndTile, board, move) -> {
							int dx = move.getdx();
							int dy = move.getdy();
                       	return ((abs(dy) == abs(2 * dx) || (abs(dx) == abs(2 * dy)))
                                     	&& abs(dy) <= 2 && abs(dx) <= 2) && (pieceOnEndTile == null || pieceOnEndTile
                                             	.getTeam() != team);
                 	}), ROOK('r', (team, pieceOnEndTile, board, move) -> {
						int dx = move.getdx();
						int dy = move.getdy();
                       	return (abs(dy) >= 1 && abs(dx) == 0 || abs(dx) >= 1 && abs(dy) == 0) && (pieceOnEndTile == null || pieceOnEndTile
                             	.getTeam() != team) && pieceInWay(board, move, team);
                 	}),  QUEEN('Q', (team, pieceOnEndTile, board, move) -> {
						int dx = move.getdx();
						int dy = move.getdy();
                       	return (abs(dy) == abs(dx) || abs(dy) >= 1 && abs(dx) == 0
                                     	|| abs(dx) >= 1 && abs(dy) == 0) && (pieceOnEndTile == null || pieceOnEndTile
                                     	.getTeam() != team) && pieceInWay(board, move, team);
                 	}), KING('K', (team, pieceOnEndTile, board, move) -> {
						int dx = move.getdx();
						int dy = move.getdy();
                       	return abs(dy) <= 1 && abs(dx) <= 1 && (pieceOnEndTile == null || pieceOnEndTile
                             	.getTeam() != team);
                 	});


                       	public final IsLegalMove isLegalMove;
                    	public final char representation;

                    	private PieceType(final char representation, final IsLegalMove isLegalMove) {
                    		this.isLegalMove = isLegalMove;
                    		this.representation = representation;
                    	}

                    	private static boolean pieceInWay(GamePiece[][] board, Move move,
                    			PieceTeam team) {
                    		int dx = move.end.x - move.start.x;
                    		int dy = move.end.y - move.start.y;
                    		int changerx = (int) Math.signum(dx);
                    		int changery = (int) Math.signum(dy);
                    		for (int x = move.start.x + changerx, y = move.start.y + changery; x < move.end.x; x += changerx, y += changery) {
                    			if (board[x][y] != null) {
                    				return false;
                    			}
                    		}
                    		return true;
                    	}
                    	
                    	@FunctionalInterface
                    	public static interface IsLegalMove {
                    		public boolean call(PieceTeam team, GamePiece pieceOnEndTile,
                    				GamePiece[][] board, Move move);
                    	}

}

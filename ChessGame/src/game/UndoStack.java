package game;

import java.util.Stack;

/**
 * Represents the previously done moves to facilitate the undo feature
 * @author s-KADAMS
 *
 */
public class UndoStack {
	
	private final Stack<GamePiece[][]> previous = new Stack<>();
	
	public void doMove(Game game){
		GamePiece[][] arr = new GamePiece[game.getBoard().length][game.getBoard()[0].length];
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = game.getBoard()[i][j] == null ? null : game.getBoard()[i][j].copy();
			}
		}
		
		previous.push(arr);
	}
	public void undo(Game game){
		if(previous.isEmpty()) return;
		
		game.setBoard(previous.pop());
		game.swapTeams();
	}
}

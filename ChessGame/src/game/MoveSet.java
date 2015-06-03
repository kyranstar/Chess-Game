package game;

import java.util.ArrayList;
import java.util.List;

public class MoveSet {
	public List<Move> moves = new ArrayList<>();
	public List<GamePiece[][]> boards = new ArrayList<>();

	public MoveSet() {

	}

	public MoveSet(List<Move> moves, List<GamePiece[][]> boards) {
		this.moves = moves;
		this.boards = boards;
	}
}

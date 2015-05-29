package game;

public enum PieceTeam {
	WHITE(true), BLACK(false);

	public final boolean isPlayerOne;

	private PieceTeam(final boolean isPlayerOne) {
		this.isPlayerOne = isPlayerOne;
	}

}

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import render.GameUI;

public class MainGame extends GameLoop {

	public static final int FPS = 60;
	public static final int UPS = 60;
	private final GameUI renderer;

	protected MainGame(final int fps, final int ups, final GameUI gameRenderer) {
		super(fps, ups);
		renderer = gameRenderer;
	}

	public static void main(final String[] args) {
		final MainGame game = initialize();
		game.run();
	}

	private static MainGame initialize() {
		final JFrame frame = new JFrame(GameUI.GAME_TITLE);
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(GameUI.WIDTH, GameUI.HEIGHT));
		panel.setBackground(Color.PINK);

		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

		final MainGame game = new MainGame(FPS, UPS, new GameUI(panel));
		panel.addMouseListener(game);
		panel.addMouseMotionListener(game);

		return game;
	}

	@Override
	public void processInput(final Queue<MouseEventWithType> mouseEvents) {
		renderer.processInput(mouseEvents);
	}

	@Override
	public void update() {
		renderer.update();
	}

	@Override
	public void draw() {
		renderer.draw();
	}
}

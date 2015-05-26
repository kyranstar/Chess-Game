package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import render.GameRenderer;

public class MainGame {

	private static GameRenderer renderer;

	public static void main(final String[] args) {
		initialize();
		run();
	}

	private static void initialize() {
		final JFrame frame = new JFrame(GameRenderer.GAME_TITLE);
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(GameRenderer.WIDTH,
				GameRenderer.HEIGHT));

		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		renderer = new GameRenderer(panel);
	}

	private static void run() {

	}
}

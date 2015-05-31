package ui;

import java.awt.event.ItemEvent;

import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kyran Adams
 */
public class ChessPanel extends javax.swing.JPanel {

	/**
	 * Creates new form ChessPanel
	 *
	 * @param gamePanel
	 */
	public ChessPanel(final JPanel gamePanel, final ChessUIResponder uiResponder) {
		initComponents(gamePanel, uiResponder);
		uiResponder.setChessPanel(this);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 *
	 * @param uiResponder
	 */
	private void initComponents(final JPanel panel, final ChessUIResponder uiResponder) {

		jSplitPane1 = new javax.swing.JSplitPane();
		menuPanel = new javax.swing.JPanel();
		newGameButton = new javax.swing.JButton();
		titleLabel = new javax.swing.JLabel();
		undoButton = new javax.swing.JButton();
		loadButton = new javax.swing.JButton();
		saveButton = new javax.swing.JButton();
		aiToggle = new javax.swing.JCheckBox();
		gamePanel = panel;

		newGameButton.setText("New Game");
		newGameButton.addActionListener(uiResponder::newGameClicked);

		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titleLabel.setText("Java Chess");

		undoButton.setText("Undo");
		undoButton.addActionListener(uiResponder::undoClicked);

		loadButton.setText("Load");
		loadButton.addActionListener(uiResponder::loadClicked);

		saveButton.setText("Save");
		saveButton.addActionListener(uiResponder::saveClicked);

		aiToggle.setText("AI");
		aiToggle.addItemListener((r) -> uiResponder.aiSet(r.getStateChange() == ItemEvent.SELECTED));

		final javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
		menuPanel.setLayout(menuPanelLayout);
		menuPanelLayout.setHorizontalGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				menuPanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								menuPanelLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(titleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(newGameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(undoButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(loadButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(saveButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(aiToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)).addContainerGap()));
		menuPanelLayout.setVerticalGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				menuPanelLayout.createSequentialGroup().addContainerGap().addComponent(titleLabel)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE).addComponent(newGameButton)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(saveButton)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(loadButton)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(undoButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(aiToggle).addGap(10, 10, 10)));

		jSplitPane1.setLeftComponent(menuPanel);

		final javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
		gamePanel.setLayout(gamePanelLayout);
		gamePanelLayout.setHorizontalGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 293,
				Short.MAX_VALUE));
		gamePanelLayout.setVerticalGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 298,
				Short.MAX_VALUE));

		jSplitPane1.setRightComponent(gamePanel);
		jSplitPane1.setEnabled(false);
		add(jSplitPane1);
	}

	// Variables declaration - do not modify
	public javax.swing.JCheckBox aiToggle;
	public javax.swing.JPanel gamePanel;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JButton loadButton;
	private javax.swing.JPanel menuPanel;
	private javax.swing.JButton newGameButton;
	private javax.swing.JButton saveButton;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JButton undoButton;
	// End of variables declaration
}

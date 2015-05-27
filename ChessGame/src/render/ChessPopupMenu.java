package render;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ChessPopupMenu extends JPopupMenu {
	JMenuItem anItem;

	public ChessPopupMenu() {
		anItem = new JMenuItem("Reset");
		add(anItem);
	}
}

package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import game.*;

@SuppressWarnings("serial")
/**
 * CardPanel which contains all the infomation about a players Cards which gets
 * displayed during that players turn
 * 
 * @author wangding1
 *
 */
public class CardPanel extends JPanel {
	private final int PAD = 10;
	private JPanel[] grid = new JPanel[6];

	/**
	 * Sets up the CardPanel with a empty 2 by 3 grid
	 */
	public CardPanel() {
		for (int i = 0; i < grid.length; i++) {
			JPanel p = new JPanel();
			p.add(new JLabel());
			grid[i] = p;
			add(p);
		}
		GridLayout g = new GridLayout(2, 3);
		g.setHgap(PAD);
		g.setVgap(PAD);
		setLayout(g);
	}

	/**
	 * Helper method for resetting all the cards
	 */
	private void resetCards() {
		for (int i = 0; i < grid.length; i++) {
			grid[i].removeAll();
			grid[i].setBackground(null);
		}
	}
	// ============================= Public methods================

	/**
	 * Sets up the current players cards
	 * 
	 * @param t
	 */
	public void setCards(Turn t) {
		List<CardType> c = t.getPlayer().getCards();
		resetCards();
		for (int i = 0; i < c.size(); i++) {
			JLabel j = new JLabel(c.get(i).toString());
			JPanel p = grid[i];
			p.add(j);
			p.setBackground(Color.gray);
		}
	}

}

package game;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gui.GUI;

/**
 * Making a Accusaion, wins the game if player is correct, losses if the player
 * is wrong
 * 
 * @author wangding1
 *
 */
public class Accuse {
	private Player player;
	private Solution solution;
	private Solution accusation;
	private GUI gui;

	/**
	 * Constructor for making an accusion cycle
	 * 
	 * @param player Player 
	 * @param solution Solution
	 * @param gui GUI
	 */
	public Accuse(Player player, Solution solution, GUI gui) {
		this.gui = gui;
		this.player = player;
		this.solution = solution;
		makeAccuse();
		getResult();

	}

	/**
	 * Allows the user to select a chanracer, weapon and a room to make a accusion
	 */
	private void makeAccuse() {
		JPanel pan = makePanel();
		JOptionPane.showOptionDialog(null, pan, "Suggesting", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);
		CardType[] accuse = updateArray(pan);

		accusation = new Solution((Characters) accuse[0], (Weapons) accuse[1], (Rooms) accuse[2]);
	}

	/**
	 * Creats and returns an array of selected accusions
	 * 
	 * @param pan
	 * @return
	 */
	private CardType[] updateArray(JPanel pan) {
		CardType[] ct = new CardType[3];
		Box b1 = (Box) pan.getComponent(1);
		Box b2 = (Box) pan.getComponent(2);
		Box b3 = (Box) pan.getComponent(3);

		for (Component j : b1.getComponents()) {
			JRadioButton b = (JRadioButton) j;
			if (b.isSelected()) {
				ct[0] = Characters.valueOf(b.getText());
			}
		}
		for (Component j : b2.getComponents()) {
			JRadioButton b = (JRadioButton) j;
			if (b.isSelected()) {
				ct[1] = Weapons.valueOf(b.getText());
			}
		}
		for (Component j : b3.getComponents()) {
			JRadioButton b = (JRadioButton) j;
			if (b.isSelected()) {
				ct[2] = Rooms.valueOf(b.getText());
			}
		}
		return ct;
	}

	/**
	 * Makes the panel which the player will select thier accusions from
	 * 
	 * @return p Panel
	 */
	private JPanel makePanel() {
		JPanel p = new JPanel(new BorderLayout());
		JLabel l = new JLabel("Please select an Character and a Weapon.");
		Box b1 = Box.createVerticalBox();
		Box b2 = Box.createVerticalBox();
		Box b3 = Box.createVerticalBox();

		ButtonGroup g1 = new ButtonGroup();
		for (Characters c : Characters.values()) {
			JRadioButton j = new JRadioButton(c.toString(), true);
			b1.add(j);
			g1.add(j);
		}

		ButtonGroup g2 = new ButtonGroup();
		for (Weapons w : Weapons.values()) {
			JRadioButton j = new JRadioButton(w.toString(), true);
			b2.add(j);
			g2.add(j);
		}

		ButtonGroup g3 = new ButtonGroup();
		for (Rooms r : Rooms.values()) {
			JRadioButton j = new JRadioButton(r.toString(), true);
			b3.add(j);
			g3.add(j);
		}

		p.add(l, BorderLayout.NORTH);
		p.add(b1, BorderLayout.WEST);
		p.add(b2, BorderLayout.CENTER);
		p.add(b3, BorderLayout.EAST);
		return p;

	}

	/*
	 * Updates the result of the player recordingly
	 */
	public void getResult() {
		boolean lost = !(accusation.equals(solution));
		player.setHasLost(lost);
		if (lost) {
			Location l = player.getLocation();
			if (l != null)
				l.setPlayer(null);
			JOptionPane.showMessageDialog(gui, "The Accusion is incorrect. You have been removed from the game.",
					"You Lost", JOptionPane.ERROR_MESSAGE);
		}
	}

}

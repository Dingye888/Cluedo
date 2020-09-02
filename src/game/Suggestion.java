package game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gui.GUI;

/**
 * Suggestion class which let player do the suggest
 * 
 * @author syl
 */
public class Suggestion {
	private Queue<Player> playerTurn;
	private Board board;
	private Player p;
	private CardType[] suggest;
	private GUI gui;
	private JPanel pan;

	/**
	 * constructor for constructing an suggestion
	 * 
	 * @param p
	 * @param board
	 * @param playerTurn
	 * @param gui
	 */
	public Suggestion(Player p, Board board, Queue<Player> playerTurn, GUI gui) {
		this.suggest = new CardType[] { null, null, p.getRoomIn().getRoom() };
		this.playerTurn = new ArrayDeque<>(playerTurn);
		this.board = board;
		this.p = p;
		this.gui = gui;
		makeSuggestion();
	}

	/**
	 * algorithum for a single suggestion cycle
	 * 
	 */
	public void makeSuggestion() {
		pan = makePanel();
		JOptionPane.showOptionDialog(null, pan, "Suggesting", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);
		updateArray();
		moveChar();
		moveWeap();
		gui.updateGamePanel();
		makeSuggest();

	}

	/**
	 * Main suggestion method
	 */
	public void makeSuggest() {

		// find the players after iterating through the queue
		for (Player nextP : playerTurn) {
			List<CardType> cards = new ArrayList<CardType>(); // list of matching cards

			for (CardType c : nextP.getCards()) {

				// Check if next player has the cards which this player selected
				if (c == suggest[0] || c == suggest[1] || c == suggest[2])
					cards.add(c);
			}
			// Has something to refute
			if (!cards.isEmpty()) {

				// Check if there is only one refutation card match then print it out straightly
				if (cards.size() == 1) {
					// displayInfo();
					refute(nextP.getCharacter(), cards.get(0));
					return;
				}
				// Selects an card to refute you
				else {
					CardType c = null;
					pan = selectRefute(nextP.getCharacter(), cards);
					JOptionPane.showOptionDialog(null, pan, "Suggesting", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					Box b1 = (Box) pan.getComponent(1);
					int i = 0;
					for (Component j : b1.getComponents()) {
						JRadioButton b = (JRadioButton) j;
						if (b.isSelected()) {
							c = cards.get(i);
						}
						i++;
					}
					// Let this player pick one refutatioin card from next player
					refute(nextP.getCharacter(), c);
				}
				return;
			}
		}
		// Check if there are not refutation cards matched until
		refute(null, null);
	}

	/**
	 * constructs a panel for a player to select an refutation card
	 * 
	 * @param characters Characters
	 * @param cards      CardType
	 * @return p Panel
	 */
	private JPanel selectRefute(Characters characters, List<CardType> cards) {
		JPanel p = new JPanel(new BorderLayout());
		JLabel l = new JLabel(characters + ", please pick a refutation card:");
		Box b = Box.createVerticalBox();

		ButtonGroup bg = new ButtonGroup();
		for (CardType c : cards) {
			JRadioButton j = new JRadioButton(c.toString(), true);
			b.add(j);
			bg.add(j);
		}
		p.add(l, BorderLayout.NORTH);
		p.add(b, BorderLayout.CENTER);
		return p;

	}

	/**
	 * Refute the player from another player with a Card
	 * 
	 * @param characters Enum
	 * @param cardType   Enum
	 */
	private void refute(Characters characters, CardType cardType) {
		if (characters == null)
			JOptionPane.showMessageDialog(gui, "There are no refutation cards matched", "No refutes",
					JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(gui, characters + " has refuted you with card: [" + cardType + "]", "Refuted",
					JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Updates the array which stores the suggestion infomation
	 */
	private void updateArray() {
		Box b1 = (Box) pan.getComponent(1);
		Box b2 = (Box) pan.getComponent(2);

		for (Component j : b1.getComponents()) {
			JRadioButton b = (JRadioButton) j;
			if (b.isSelected()) {
				suggest[0] = Characters.valueOf(b.getText());
			}
		}
		for (Component j : b2.getComponents()) {
			JRadioButton b = (JRadioButton) j;
			if (b.isSelected()) {
				suggest[1] = Weapons.valueOf(b.getText());
			}
		}
	}

	/**
	 * Makes the panel which we will pick our suggestion
	 * 
	 * @return
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
		JRadioButton j = new JRadioButton(suggest[2].toString(), true);
		b3.add(j);
		g3.add(j);

		p.add(l, BorderLayout.NORTH);
		p.add(b1, BorderLayout.WEST);
		p.add(b2, BorderLayout.CENTER);
		p.add(b3, BorderLayout.EAST);
		return p;

	}

	// moves the weapon into the current room
	private void moveWeap() {
		// If the current room alredy has the weapon
		if (p.getRoomIn().getItems().contains(suggest[1])) {
			return;
		}
		// The weapon is in some other room
		else {
			for (Room r : board.getRooms().values()) {
				// if the weapon is in this room, remove it and add it to the suggested room
				if (r.getItems().contains(suggest[1])) {
					r.removeItem(suggest[1]);
					p.getRoomIn().addItem(suggest[1]);
					return;
				}
			}
		}
	}

	/**
	 * Moves the character into the room if its moveable
	 */
	private void moveChar() {
		Room newRoom = p.getRoomIn();
		for (Player player : playerTurn) {
			Room oldRoom = player.getRoomIn();

			// if the suggested character is a player
			if (player.getCharacter() == suggest[0]) {

				// If the player is in a room
				if (oldRoom != null) {
					// if the room is the same as the sugegsted room
					if (oldRoom.getRoom() == newRoom.getRoom()) {
						return;
					}
					// The room is not the same as the suggested room
					else {
						player.setRoomIn(newRoom);
						newRoom.addItem(player.getCharacter());
						oldRoom.removeItem(player.getCharacter());
						player.setLastRoom(null);
						return;
					}
				}
				// The player is not in a room
				else {
					player.setRoomIn(newRoom);
					newRoom.addItem(player.getCharacter());
					Location l = player.getLocation();
					l.setPlayer(null);
					player.setLocation(null);
					player.setLastRoom(null);
					return;
				}
			}
		}
		// add the enum
		newRoom.addItem(suggest[0]);
		return;
	}

}

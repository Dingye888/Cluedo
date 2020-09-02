package gui;

import game.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * ControlerPanel which contains all the buttons which aids the players
 * movements around the board.
 * 
 * @author wangding1
 *
 */
public class ControlerPanel extends JPanel {

	// Format Constant
	private final int ROOM_WIDTH = 75;
	private final int ROOM_HEIGHT = 25;
	private final int CONTROLS_SIZE = 80;

	// Control buttons
	private final String[] CONTROLS = new String[] { "Up", "Left", "Down", "Right", "Door 1", "Door 2", "Door 3",
			"Door 4", "Enter", "Stop" };
	private final String[] KEY_MAP = new String[] { "w", "a", "s", "d", "1", "2", "3", "4", "5", "6" };

	// fields
	private JLabel moves = new JLabel();
	private Map<String, JButton> controlButtons;
	private Turn t;

	// Constructor which creats all the panels and Buttons
	public ControlerPanel() {
		controlButtons = makeButtons();
		arrangeButtons();
		setLayout(null);
		// setBackground(Color.blue);
	}

	/**
	 * Adds all the buttons from the hash maps into each allocated panel
	 */
	private void arrangeButtons() {
		// Layout for the 4 room buttons
		controlButtons.get("1").setBounds(0, 10, ROOM_WIDTH, ROOM_HEIGHT);
		controlButtons.get("2").setBounds(0, 40, ROOM_WIDTH, ROOM_HEIGHT);
		controlButtons.get("3").setBounds(0, 70, ROOM_WIDTH, ROOM_HEIGHT);
		controlButtons.get("4").setBounds(0, 100, ROOM_WIDTH, ROOM_HEIGHT);

		// Layout for enter and quit button
		controlButtons.get("5").setBounds(185, 40, ROOM_WIDTH, ROOM_HEIGHT);
		controlButtons.get("6").setBounds(185, 70, ROOM_WIDTH, ROOM_HEIGHT);

		// Layout for w, a, s, d.
		controlButtons.get("w").setBounds(90, 60, CONTROLS_SIZE, CONTROLS_SIZE);
		controlButtons.get("a").setBounds(0, 150, CONTROLS_SIZE, CONTROLS_SIZE);
		controlButtons.get("s").setBounds(90, 150, CONTROLS_SIZE, CONTROLS_SIZE);
		controlButtons.get("d").setBounds(180, 150, CONTROLS_SIZE, CONTROLS_SIZE);

		// Layout for moves indication
		moves.setBounds(90, 30, 85, ROOM_HEIGHT);
		add(moves);
	}

	/**
	 * Helper method which Makes all the JButtons from a given array of Strings
	 * along with Key mapping and assign a responce for each button clicked.
	 * 
	 * @param map
	 */
	private Map<String, JButton> makeButtons() {
		Map<String, JButton> map = new LinkedHashMap<>();

		// For all the button names in the given array
		for (int i = 0; i < CONTROLS.length; i++) {

			// Each new JButton
			int j = i;
			JButton b = new JButton(CONTROLS[i]);
			b.setEnabled(false);
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					t.getMove().selectActions(KEY_MAP[j]);
				}
			});
			map.put(KEY_MAP[i], b);
			add(b);
		}
		return map;
	}

	/**
	 * Disables all the buttons
	 */
	private void disableButtons() {
		for (JButton b : controlButtons.values()) {
			b.setEnabled(false);
		}
	}
	// ================================== Public Methods ========================

	/**
	 * Updates the button enable state based on the avalible options
	 * 
	 * @param options <Name,key mapping>
	 */
	public void updateControlButtons(Map<String, String> controls) {
		if (controls == null) {
			disableButtons();
			moves.setText(null);
		} else {
			// options is not null
			int i = t.getMove().getSteps();
			moves.setText("Moves left: " + i);
			for (String s : controlButtons.keySet()) {
				JButton b = controlButtons.get(s);
				if (controls.values().contains(s))
					b.setEnabled(true);
				else
					b.setEnabled(false);
			}
		}
	}

	public void setTurn(Turn t) {
		this.t = t;
	}

}

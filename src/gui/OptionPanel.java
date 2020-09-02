package gui;
import game.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * Creats the Options panel which includes all the options button with allocated
 * formatting
 * 
 * @author wangding1
 *
 */
public class OptionPanel extends JPanel {

	// Format constant
	private final int XPAD = 10;
	private final int YPAD = 10;
	private final int ALLIGNMENT = FlowLayout.CENTER;

	// Button names and mapping
	private final String[] OPTIONS = new String[] { "Roll/Move", "Suggest", "End turn", "Accuse" };
	private final String[] KEYMAP = new String[] { "0", "1", "2", "3" };

	// Fields
	private JLabel tag = new JLabel();
	private Map<String, JButton> optionButtons;
	private Turn t;

	/**
	 * Constructor which creates all the options buttons and layouts
	 */
	public OptionPanel() {
		add(tag);
		optionButtons = makeButtons();
		setLayout(new FlowLayout(ALLIGNMENT, XPAD, YPAD));

	}

	/**
	 * Helper method to make the buttons and stores them in a linked hash map
	 * 
	 * @return map
	 */
	private Map<String, JButton> makeButtons() {
		Map<String, JButton> map = new LinkedHashMap<>();

		// For each button name in array
		for (int i = 0; i < OPTIONS.length; i++) {

			// Each JButton
			int j = i;
			JButton b = new JButton(OPTIONS[i]);
			b.setEnabled(false);
			b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					t.selectActions(KEYMAP[j]);
				}
			});
			map.put(KEYMAP[i], b);
			add(b);
		}
		return map;
	}

	/**
	 * Disables all the buttons
	 */
	private void disableButtons() {
		for (JButton b : optionButtons.values()) {
			b.setEnabled(false);
		}
	}
	// ================================== Public Methods ========================

	/**
	 * Updates the button enable state based on the avalible options
	 * 
	 * @param options <Name,key mapping>
	 */
	public void updateOptionButtons(Map<String, String> options) {
		if (options == null)
			disableButtons();

		// options is not null
		else {
			for (String s : optionButtons.keySet()) {
				JButton b = optionButtons.get(s);
				if (options.values().contains(s))
					b.setEnabled(true);
				else
					b.setEnabled(false);
			}
		}
	}

	/**
	 * Sets up the name tag with the current players name
	 * 
	 * @param t
	 */
	public void setTurn(Turn t) {
		this.t = t;
		tag.setText(t.getPlayer().getCharacter().toString());
	}

}

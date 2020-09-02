package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.*;
import game.*;

/**
 * GUI class which creates the MVC design which includes all the visual outputs
 * the player will need.
 *
 * @author wangding1
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	// Frame constant
	private final int WIDTH = 800;
	private final int HEIGHT = 820;
	private final int PAD = 10;

	// Board constant
	private final int BOARD_SIZE = 500;

	// Controler constant
	private final int C_WIDTH = WIDTH - BOARD_SIZE - PAD * 4;
	private final int C_HEIGHT = WIDTH - BOARD_SIZE - PAD * 6;

	// Options constant
	private final int O_WIDTH = WIDTH - C_WIDTH - PAD * 4;
	private final int O_HEIGHT = 50;

	// GUI feilds
	private GamePanel gamePanel;
	private JMenuBar menuBar;
	private CardPanel cardPanel;
	private OptionPanel optionPanel;
	private ControlerPanel controlerPanel;

	/**
	 * GUI constructor which creates all the visual output objects
	 * 
	 * @param board Board
	 */
	public GUI(Board board) {
		this.gamePanel = new GamePanel(board, BOARD_SIZE);
		this.menuBar = new JMenuBar();
		this.cardPanel = new CardPanel();
		this.optionPanel = new OptionPanel();
		this.controlerPanel = new ControlerPanel();

		// Setting menu bar
		setJMenuBar(menuBar);
		menuBar.add(new JMenu("New Game"));
		menuBar.add(new JMenu("Save"));

		// Game Board
		gamePanel.setBounds(PAD, PAD, BOARD_SIZE, BOARD_SIZE);
		add(gamePanel);

		// Player Cards
		cardPanel.setBounds(C_WIDTH + PAD * 4, BOARD_SIZE + PAD * 3 + 40, O_WIDTH - 40, O_HEIGHT * 3 + 30);
		add(cardPanel);

		// Controler buttons
		controlerPanel.setBounds(PAD, BOARD_SIZE + PAD * 2, C_WIDTH, C_HEIGHT);
		add(controlerPanel);

		// Options buttons
		optionPanel.setBounds(C_WIDTH + PAD * 2, BOARD_SIZE + PAD * 2, O_WIDTH, O_HEIGHT);
		add(optionPanel);

		// Sets the frame
		setframe();
	}

	/**
	 * Sets the frame settings
	 */
	private void setframe() {
		setTitle("Cluedo 1.0");
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setVisible(true);

		// Adding exit operations
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// default icon, custom title
				int n = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to exit the program?",
						"Warning", JOptionPane.YES_NO_OPTION);
				// Yes
				if (n == 0)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// No
				else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});

	}

	// ================================== Public Methods ========================

	/**
	 * Updates the current players avalible options. if the either of the maps are
	 * null, then deselects all the null buttons.
	 * 
	 * @param options <Name,key mapping>
	 */
	public void updateButtons(Map<String, String> options, Map<String, String> controls) {
		optionPanel.updateOptionButtons(options);
		controlerPanel.updateControlButtons(controls);
	}

	/**
	 * Setts the current GUI turn
	 * 
	 * @param t
	 */
	public void setTurn(Turn t) {
		updateGamePanel();
		optionPanel.setTurn(t);
		controlerPanel.setTurn(t);
		cardPanel.setCards(t);
		validate();

	}

	/**
	 * Updates the game panel
	 */
	public void updateGamePanel() {
		gamePanel.redraw();
	}

//	private void ad dLegend() {
//		JLabel l = new JLabel("Legend:");
//		Dimension d = l.getMinimumSize();
//		l.setBounds(PAD * 2 + BOARD_SIZE, PAD, d.width, d.height);
//		// key.setBorder(BorderFactory.createLineBorder(Color.black, BOARDER));
//		add(l);
//
//	}

}
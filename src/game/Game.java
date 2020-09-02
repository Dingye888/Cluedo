package game;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

import javax.swing.JOptionPane;

import gui.GUI;

/**
 * Main Cludo game class, which has the infomation about all the players, board
 * and win condition. It also uses the Setup helper class to Initialize the game
 * by creating key objects.
 * 
 * @author wangding1
 *
 */
public class Game {

	private GUI gui;

	private Map<Characters, Player> players; // Active players: linkedHashMap
	private Queue<Player> playerTurn;
	private Queue<Player> playerOrder;

	private Solution solution; // Win condition
	private Board board; // game board
	private boolean finished; // game state

	// place holder
	Player p = null;
	Player x = null;

	/**
	 * Main game constructor that sets up eveything the game needs
	 */
	public Game() {
		board = new Board(); // Constructs the board, setting a 2d loaction array
		gui = new GUI(board);
		finished = false;
		setUp(); // sets up the starting process
		gameStart();

		// testing();

	}

	/**
	 * Dose the initial setup for the game to a post playing state
	 */
	private void setUp() {
		Setup setup = new Setup(gui);
		solution = setup.getSolution();
		players = setup.getPlayers();
		board.setPlayers(players); // Sets the players on the board
		playerTurn = new ArrayDeque<>(players.values());
		playerOrder = new ArrayDeque<>(players.values());

	}

	/**
	 * Runs the game: rounds, rules, etc..
	 */
	private void gameStart() {
		nextPlayer();
	}

	private void gameEnd(Player p) {
		// if the game if finished from a player winning
		if (finished) {
			JOptionPane.showMessageDialog(gui, "Congratulations, you are the winner!\n " + p.getCharacter(),
					"Winner Winner Chiken Dinner", JOptionPane.INFORMATION_MESSAGE);
			// the game is finished from eveyone has lost
		} else if (playerTurn.isEmpty()) {
			JOptionPane.showMessageDialog(gui,
					"Game Over, you all have made the wrong accusation.\n\n The final solution is... \n"
							+ solution.toString(),
					"GG", JOptionPane.ERROR_MESSAGE);
		}
		System.exit(0);
	}

// ====================================== Public Methods ========================
	/**
	 * Changes to the next player
	 */
	public void nextPlayer() {
		// while no one has won nor everyone failed the accusion. Starts a new turn
		if (!finished && !playerTurn.isEmpty()) {
			p = playerTurn.poll();
			playerOrder.remove(p);
			new Turn(p, board, solution, playerOrder, gui, this); // new turn for this player
		} else {
			gameEnd(p);
		}
	}

	/**
	 * Adds the player back to the queue, if it can still play
	 */
	public void addPlayer() {
		// if player havent lost put his turn at the back
		if (!p.getHasLost())
			playerTurn.offer(p);
		playerOrder.offer(p);
	}

	/**
	 * Sets the state of the game to see weither it has finished
	 * 
	 * @param finished boolean
	 */
	public void setfinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * main game function
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		new Game();
	}

}

package game;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

import gui.*;

/**
 * 
 * Handles a players turn process. Rolling , sugesting and accusing.
 * 
 * @author wangding1
 *
 */
public class Turn {

	// Collections
	private Map<String, String> actions;
	private Queue<Player> playerOrder;

	// Flags
	private boolean moved, suggested, finished;

	// Classes
	private Game game;
	private Solution solution;
	private Board board;
	private Player p;
	private GUI gui;
	private Move m;

	/**
	 * Constructs a players Turn
	 * 
	 * @param p           Player
	 * @param board       Board
	 * @param solution    Solution
	 * @param playerOrder Queue of players
	 * @param gui         GUI for display
	 * @param game        Game
	 */
	public Turn(Player p, Board board, Solution solution, Queue<Player> playerOrder, GUI gui, Game game) {
		this.game = game;
		this.playerOrder = playerOrder;
		this.board = board;
		this.solution = solution;
		this.gui = gui;
		this.p = p;
		gui.setTurn(this);
		updateActions();
	}

	// =========================== Public Methods =========================

	/**
	 * Puts any valid actions the Player can currently perform
	 */
	public void updateActions() {
		actions = new LinkedHashMap<>();
		// Have not moved and have not suggested
		if (!moved && !suggested)
			actions.put("Move Player", 0 + "");
		// In a room, havent suggested, not in a room as last turns
		if (!suggested && p.getRoomIn() != null && p.getRoomIn() != p.getLastRoom())
			actions.put("Make an Suggestion", 1 + "");

		// Always adds the accuse and end turn
		actions.put("End turn", 2 + "");
		actions.put("Make an Accusation", 3 + "");
		gui.updateButtons(actions, null);
	}

	/**
	 * Selects an action based of a given string key
	 * 
	 * @param s
	 */
	public void selectActions(String s) {
		switch (s) {
		// Accusion
		case "3":
			new Accuse(p, solution, gui);
			game.setfinished(!(p.getHasLost())); // see if a player has alredy won
			game.addPlayer();
			game.nextPlayer();
			break;
		// Move
		case "0":
			moved = true;
			m = new Move(p, board, gui, this);
			m.updateActions();

			break;
		// Suggestion
		case "1":
			suggested = true;
			p.setLastRoom(p.getRoomIn());// Setting the last room the player suggested to its current room
			new Suggestion(p, board, playerOrder, gui);
			updateActions();
			break;
		// Next turn
		case "2":
			game.addPlayer();
			game.nextPlayer();
			break;
		}
	}

	/**
	 * Returns the state of the turnto check if a player has won the game
	 * 
	 * @return boolean finished
	 */
	public boolean getFinished() {
		return finished;
	}

	/**
	 * Returns the current players turn
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return p;
	}

	/**
	 * Returns the current Move class for the players turn if it has chosen to move
	 * 
	 * @return Move
	 */
	public Move getMove() {
		return m;
	}
}

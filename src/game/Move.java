package game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gui.GUI;

/**
 * Move class which process a player movement on the Cluedo board
 * 
 * @author birulin
 *
 */
public class Move {
	// Processing fields
	private int max, min; // bounds of the random steps player will get
	private int steps;
	boolean endTurn;

	// Game infomation
	private Player player;
	private Board board;

	// Map of action the player can perform
	private Map<String, String> actions;

	// List of locations the player has alredy walked
	private List<Location> path;

	private GUI gui;
	private Turn t;

	public Move(Player p, Board b, GUI gui, Turn t) {
		this.t = t;
		this.max = 13;
		this.min = 2;
		this.player = p;
		this.endTurn = false;
		this.board = b;
		this.path = new ArrayList<>();
		this.gui = gui;
		steps = rollDice(max, min);
	}

	/**
	 * Puts any valid actions the Player can currently perform
	 */
	public void updateActions() {
		if (endTurn || steps <= 0) {
			for (Location l : path) {
				l.setEmpty(true);
			}
			t.updateActions();
			return;
		}
		actions = new LinkedHashMap<>();
		Room ri = player.getRoomIn();
		// if playet is in a room
		if (ri != null) {
			for (Location l : ri.getLocations().values()) {
				if (l.getPlayer() == null)
					actions.put("Leave door <" + l.getDoor() + ">", l.getDoor() + "");
			}
		}
		// if player is not in a room
		else {
			// up
			if (validBound(0, -1))
				actions.put("Move Up", "w");
			// left
			if (validBound(-1, 0))
				actions.put("Move Left", "a");
			// down
			if (validBound(0, 1))
				actions.put("Move Down", "s");
			// right
			if (validBound(1, 0))
				actions.put("Move Right", "d");
			// if the player is on an entrance
			if (player.getLocation().getRoom() != null)
				actions.put("Enter Room", "5");
		}
		actions.put("End Turn", "6");
		gui.updateButtons(null, actions);
		// displayInfo();
	}

	/**
	 * Boundary checking if the player can move into this area
	 */
	private boolean validBound(int x, int y) {
		Location loc = player.getLocation(); // current locaiton
		int col = loc.getX() + x;
		int row = loc.getY() + y;
		if (col >= 0 && col < 24 && row >= 0 && row < 25) {
			Location l = board.getMap()[row][col];
			// if location is not null and location dosent have other player && location is
			// not a door sigh
			if (l != null && l.isEmpty() && !(l.getRoom() == null && l.getDoor() != 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Moves the player in the givin direction
	 * 
	 * @param x int col
	 * @param y int rows
	 */
	private void movePlayer(int x, int y) {
		Location oldLoc = player.getLocation(); // current locaiton
		int col = oldLoc.getX() + x;
		int row = oldLoc.getY() + y;
		Location newLoc = board.getMap()[row][col];
		newLoc.setPlayer(player);
		oldLoc.setPlayer(null);
		player.setLocation(newLoc);
		// list of location that the player has been passed
		oldLoc.setEmpty(false);
		path.add(oldLoc);

	}

	/**
	 * Leaving from a specific door
	 * 
	 * @param i int
	 */
	private void leaveRoom(int i) {
		Room r = player.getRoomIn();
		Location l = r.getLocations().get(i);
		l.setPlayer(player);
		player.setLocation(l);
		player.setRoomIn(null);
		r.removeItem(player.getCharacter());
	}

	/**
	 * Enters the room that the player is currently on
	 */
	private void enterRoom() {
		Location l = player.getLocation();
		Room r = l.getRoom();
		r.addItem(player.getCharacter()); // adding player character to the room
		l.setPlayer(null); // setting the players to null in the old location
		player.setRoomIn(r); // setting the room for the player
		player.setLocation(null); // Setting the location for the player to null
	}

	/**
	 * Let the player roll dices to decide how many step they are going to move.
	 *
	 */
	private int rollDice(int max, int min) {
		return (int) ((Math.random() * (max - min) + min));
	}

	// =========================================Public Methods==========

	/**
	 * Allows the GUI buttons to select an action to perform
	 * 
	 * @param s String for button mapping
	 */
	public void selectActions(String s) {
		switch (s) {
		// move up
		case "w":
			movePlayer(0, -1); // x,y 2d array position
			break;
		// move left
		case "a":
			movePlayer(-1, 0); // x,y 2d array position
			break;
		// Move down
		case "s":
			movePlayer(0, 1); // x,y 2d array position
			break;
		// move right
		case "d":
			movePlayer(1, 0); // x,y 2d array position
			break;
		case "1":
			leaveRoom(1);
			break;
		case "2":
			leaveRoom(2);
			break;
		case "3":
			leaveRoom(3);
			break;
		case "4":
			leaveRoom(4);
			break;
		// Enter room
		case "5":
			enterRoom();
			// cant make a suggestion if a player made a suggestion here
			if (player.getLastRoom() != null && player.getLastRoom().getRoom() == player.getRoomIn().getRoom())
				break;
			endTurn = true;
			break;
		// End turn
		case "6":
			endTurn = true;
			// resetting players last room suggestion if its ended on blank
			if (player.getRoomIn() == null)
				player.setLastRoom(null);
			break;
		}
		gui.updateGamePanel();
		steps--;
		updateActions();
	}

	public int getSteps() {
		return steps;
	}

}

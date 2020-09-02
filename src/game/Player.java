package game;
import java.util.ArrayList;
import java.util.List;

/**
 * The Player class which holds the infomation about each player such as thier
 * Character, hand of cards and their current location
 * 
 * @author wangding1
 *
 */
public class Player {
	private List<CardType> cards; // List of the cards the player has
	private Characters character; // The character the player represents
	private Location location; // current location of the player
	private Room roomIn; // the room the player is currently in
	private boolean hasLost; // player state if they have lost the game

	private Room lastRoom;

	/**
	 * Constructor which sets up the player class
	 * 
	 * @param character
	 */
	public Player(Characters character) {
		this.cards = new ArrayList<>();
		this.character = character;
		this.hasLost = false;
		this.lastRoom = null;
		this.roomIn = null;
	}

	/**
	 * Adds a card to the players hand
	 * 
	 * @param card
	 */
	public void addCard(CardType card) {
		cards.add(card);
	}

	/**
	 * Sets the player location on the board
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Returns the Location object
	 * 
	 * @return location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets a room for a player
	 * 
	 * @param room
	 */
	public void setRoomIn(Room roomIn) {
		this.roomIn = roomIn;
	}

	/**
	 * Gets the room that the player is in
	 * 
	 * @return Room
	 */
	public Room getRoomIn() {
		return this.roomIn;
	}

	/**
	 * Returns the last room the player ended turn on
	 * 
	 * @return Room
	 */
	public Room getLastRoom() {
		return lastRoom;
	}

	/**
	 *  Returns the last room the player suggested on 
	 * @param lastRoom
	 */
	public void setLastRoom(Room lastRoom) {
		this.lastRoom = lastRoom;
	}

	/**
	 * Returns the player state if they has lost
	 * 
	 * @return boolean
	 */
	public boolean getHasLost() {
		return hasLost;
	}

	/**
	 * Returns the player state if they has lost
	 * 
	 * @return boolean
	 */
	public void setHasLost(boolean state) {
		this.hasLost = state;
	}

	/**
	 * Getter for the player character
	 * 
	 * @return enum
	 */
	public Characters getCharacter() {
		return this.character;
	}

	/**
	 * Getter for the pleyer cards
	 * 
	 * @return List
	 */
	public List<CardType> getCards() {
		return this.cards;
	}

	/**
	 * Custom toString method
	 */
	public String printCards() {
		String s = character.toString() + ": ";
		for (CardType c : cards) {
			s += "[" + c.toString() + "] ";
		}
		s += "\n";
		return s;

	}

	/**
	 * Returns the character representation on the board
	 */
	public String toString() {
		switch (character) {
		case Miss_Scarlett:
			return "S";
		case Col_Mustard:
			return "M";
		case Mrs_White:
			return "W";
		case Mr_Green:
			return "G";
		case Mrs_Peacock:
			return "C";
		case Prof_Plum:
			return "P";
		default:
			return null;
		}
	}

}

package game;

/**
 * Marks the tiles of the game for players to move on.
 * 
 * @author wangding1
 *
 */
public class Location {
	private Room room;
	private int door;
	private Player player;
	private boolean empty;
	private int x;
	private int y;

	/**
	 * Default constructor
	 * 
	 * @param x int
	 * @param y int
	 */
	public Location(int y, int x) {
		this.player = null;
		this.empty = true;
		this.x = x;
		this.y = y;
	}

	/**
	 * Room constructor
	 * 
	 * @param x    int
	 * @param y    int
	 * @param room object
	 * @param door int
	 */
	public Location(int y, int x, Room room, int door) {
		this.room = room;
		this.door = door;
		this.empty = true;
		this.x = x;
		this.y = y;
	}

//////////////////////////////////////////////////		Getters and Setters		////////////////////////////////////////////////////////////

	/**
	 * Sets the player onto the location marking it full
	 * 
	 * @param player Player
	 */
	public void setPlayer(Player player) {
		this.player = player;
		this.empty = (player == null) ? true : false;
	}

	/**
	 * returns the player object
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return player;

	}

	/**
	 * returns the room object
	 * 
	 * @return Room
	 */
	public Room getRoom() {
		return room;

	}

	/**
	 * returns the door plate
	 * 
	 * @return int
	 */
	public int getDoor() {
		return door;
	}

	/**
	 * Get col index
	 * 
	 * @return x int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get rol index
	 * 
	 * @return y int
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the location as empty
	 * 
	 * @param empty boolean
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * Return wheather this location is empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return this.empty;
	}

	/**
	 * Resturns the room type symbols thats marked on the board
	 */
	public String toString() {
		// showing location representaion
		if (empty) {

			// Printing corredor
			if (door == 0)
				return "[ ]";
			// showing door number
			else if (room == null)
				return "<" + door + ">";
			// showing door entrance
			else
				return "[*]";

		}
		// showing player
		else {
			if (player != null)
				return "{" + player.toString() + "}";
			else {
				return "[x]";
			}
		}
	}

//	/**	dont need this for now
//	 * Gets the door number
//	 * 
//	 * @return door int
//	 */
//	public Integer getDoor() {
//		return this.door;
//	}
}
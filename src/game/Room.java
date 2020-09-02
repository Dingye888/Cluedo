package game;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Room class that represents each of the 9 room type.
 * 
 * @author wangding1
 *
 */
public class Room {
	private Rooms room;
	private Set<CardType> items;
	private Map<Integer, Location> locations;

	/**
	 * Room constructor that takes in a room enum
	 * 
	 * @param room
	 */
	public Room(Rooms room) {
		this.room = room;
		this.items = new HashSet<>();
		this.locations = new HashMap<>();
	}

	/**
	 * Adding a location "entrance" to the room
	 * 
	 * @param door
	 * 
	 * @param l    Location
	 */
	public void addLocation(int door, Location l) {
		locations.put(door, l);
	}

	/**
	 * Adding an item such as player or weapons to the room
	 * 
	 * @param item
	 */
	public void addItem(CardType item) {
		this.items.add(item);
	}

	/**
	 * Removing an item from this room
	 * 
	 * @param item CardType
	 */
	public void removeItem(CardType item) {
		this.items.remove(item);
	}

	/**
	 * Returns the map of locations
	 * 
	 * @return map of locationd
	 */
	public Map<Integer, Location> getLocations() {
		return locations;
	}

	/**
	 * Returns the current room enum
	 * 
	 * @return
	 */
	public Rooms getRoom() {
		return room;
	}

	public Set<CardType> getItems() {
		return items;
	}

	public String toString() {
		String s = "";
		for (CardType c : items) {
			s += "[" + c + "] ";
		}
		return s;
	}

}

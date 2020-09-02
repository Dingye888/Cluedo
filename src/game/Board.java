package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Board class which holds infomation to player locations, grids and room
 * locations.
 * 
 * @author wangding1
 *
 */
public class Board {
	private Map<Characters, Location> startingLocations; // map to player locations
	private Map<Weapons, Room> weapons; // Map of weapons currently on the board
	private Map<Rooms, Room> rooms; // Map of each room collection // mabe room objects
	private Location[][] map; // needs a parser to make the map

	public Board() {
		weapons = new HashMap<>();
		setUp();
		setWeapons(); // adds the random weapons to different rooms
	}

	// Sets the the init board
	private void setUp() {
		Pass p = new Pass();
		map = p.getMap();
		rooms = p.getRooms();
		startingLocations = p.getStartingLocations();
	}

	/**
	 * Put each weapon into a new random room.
	 */
	private void setWeapons() {
		List<Rooms> tempRoom = new ArrayList<>(rooms.keySet());
		Collections.shuffle(tempRoom);
		// for each weapon that needs to be added
		for (Weapons w : Weapons.values()) {
			Room r = rooms.get(tempRoom.remove(0));
			weapons.put(w, r);
			r.addItem(w);
		}
	}

//////////////////////////////////////////////////		Getters and Setters		////////////////////////////////////////////////////////////
	/**
	 * Sets how manny players are in this game
	 * 
	 * @param players list
	 */
	public void setPlayers(Map<Characters, Player> players) {
		for (Player p : players.values()) {
			Characters c = p.getCharacter(); // the character enum
			startingLocations.get(c).setPlayer(p); // Setting player to a location
			p.setLocation(startingLocations.get(c)); // Setting location for player

		}
	}

	/**
	 * Returns the current map
	 * 
	 * @return map 2D array
	 */
	public Location[][] getMap() {
		return map;
	}

	/**
	 * Returns the map of roomd
	 * 
	 * @return Map with Rooms enum mapped to Room object
	 */
	public Map<Rooms, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Prints out the map infomation, representing with symbols
	 */
	public String toString() {
		PrintMap pm = new PrintMap(map, rooms);
		return pm.toString();
	}

}

package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Parser class to load in a file with the game board infomation
 * 
 * @author wangding1
 * 
 */
public class Pass {
	Map<Characters, Location> startingLocations;
	Map<Rooms, Room> rooms;
	Location[][] map;
	String board = "File key representation, \"-\" = null, \"*\" = Hallway,  \"1-9\" = 9 room types, \".1-.4\" room door, \r\n"
			+ "Character representation: S - Miss_Scarlett, M - Col_Mustard, W - Mrs_White, G -Mr_Green, C - Mrs_Peacock, P - Prof_Plum \r\n"
			+ "24 25\r\n" + "- - - - - -	- - - W - -	- - G - - -	- - - - - - \r\n"
			+ "- - - - - -	- * * * - -	- - * * * -	- - - - - - \r\n"
			+ "- - - - - -	* * - - - -	- - - - * *	- - - - - - \r\n"
			+ "- - - - - -	* * - - - -	- - - - * *	- - - - - - \r\n"
			+ "- - - - - -	* * - - - -	- - - - * *	0.1 - - - - - \r\n"
			+ "- - - - - -	* 2.1 0.1 - - -	- - - 0.4 2.4 *	3.1 - - - - - \r\n"
			+ "- - - - 0.1 -	* * - - - -	- - - - * *	* * * * * C \r\n"
			+ "* * * * 1.1 *	* * - 0.2 - - - - 0.3 - * *	* * * * * - \r\n"
			+ "- * * * * *	* * * 2.2 * *	* * 2.3 * * * - - - - - - \r\n"
			+ "- - - - - * * * * * * *	* * * * * 4.1 0.1 - - - - - \r\n"
			+ "- - - - - -	- - * * - -	- - - * * *	- - - - - - \r\n"
			+ "- - - - - -	- - * * - -	- - - * * *	- - - - - - \r\n"
			+ "- - - - - -	- 0.1 9.1 * - -	- - - * * *	- - - - 0.2 - \r\n"
			+ "- - - - - -	- - * * - -	- - - * * *	* * 5.2 * 4.2 - \r\n"
			+ "- - - - - -	- - * * - -	- - - * * *	- - 0.2 - - - \r\n"
			+ "- - - - - -	0.2 - * * - -	- - - * * -	- - - - - - \r\n"
			+ "- * * * * *	9.2 * * * - -	- - - * 5.1 0.1	- - - - - - \r\n"
			+ "M * * * * *	* * * * * 7.1 7.2 * * * * -	- - - - - - \r\n"
			+ "- * * * * *	8.1 * * - - 0.1 0.2 - - * * *	- - - - - - \r\n"
			+ "- - - - - -	0.1 * * - - -	- - - * * *	* * * * * P \r\n"
			+ "- - - - - -	- * * - - -	- - 0.3 7.3 * 6.1	* * * * * - \r\n"
			+ "- - - - - -	- * * - - -	- - - * * 0.1	- - - - - - \r\n"
			+ "- - - - - -	- * * - - -	- - - * * -	- - - - - - \r\n"
			+ "- - - - - -	- * * - - -	- - - * * -	- - - - - - \r\n"
			+ "- - - - - -	- S - - - -	- - - - * -	- - - - - - \r\n" + "\r\n" + "";

	public Pass() {
		startingLocations = new HashMap<>();
		rooms = new HashMap<>();
		load();
	}

	/**
	 * Scanning the board file to generate a 2d Loaction array. Adding game objects
	 * such as Rooms and locations on the go.
	 */
	private void load() {
		Scanner s = new Scanner(board);
		String mapKey = s.nextLine(); // notes
		String playerKey = s.nextLine();
		int col = s.nextInt(); // 24
		int row = s.nextInt(); // 25

		map = new Location[row][col];

		// Each row
		for (int i = 0; i < row; i++) {
			// Each col
			for (int j = 0; j < col; j++) {
				String symbol = s.next(); // file symbol
				switch (symbol) {
				// Empty
				case "-":
					continue;
				// Hallway
				case "*":
					map[i][j] = new Location(i,j);
					break;
				// Setting player locations
				case "S":
					startingLocations.put(Characters.Miss_Scarlett, map[i][j] = new Location(i,j));
					break;
				case "M":
					startingLocations.put(Characters.Col_Mustard, map[i][j] = new Location(i,j));
					break;

				case "W":
					startingLocations.put(Characters.Mrs_White, map[i][j] = new Location(i,j));
					break;

				case "G":
					startingLocations.put(Characters.Mr_Green, map[i][j] = new Location(i,j));
					break;

				case "C":
					startingLocations.put(Characters.Mrs_Peacock, map[i][j] = new Location(i,j));
					break;

				case "P":
					startingLocations.put(Characters.Prof_Plum, map[i][j] = new Location(i,j));
					break;

				// exsisting room
				default:
					float roomDescript = Float.parseFloat(symbol);
					// Need to round since floats are not precise
					int roomNum = (int) roomDescript; // 1-9
					int door = (int) (Math.round((roomDescript - roomNum) * 10));// 1-4

					// not a room, but a indication
					if (roomNum == 0) {
						map[i][j] = new Location(i,j,null, door);

					}
					// makes an acutal room and adds it to the location
					else {
						Rooms room = Rooms.values()[roomNum - 1]; // gets the room enum
						// if room map dosent have this room yet
						if (!rooms.containsKey(room))
							rooms.put(room, new Room(room));

						Room r = rooms.get(room);
						Location l = new Location(i, j, r, door); // new location with room and door
						r.addLocation(door,l); // adds the location to the room
						map[i][j] = l;

					}

				}
			}
		}
		s.close();
	}
//////////////////////////////////////////////////		Getters and Setters		////////////////////////////////////////////////////////////


	// Returns the 2d array map
	public Location[][] getMap() {
		return map;
	}

	// Returns the map of rooms
	public Map<Rooms, Room> getRooms() {
		return rooms;
	}

	// Returns the playerLocations
	public Map<Characters, Location> getStartingLocations() {
		return startingLocations;
	}

}
package game;
import java.util.Map;
import java.util.Scanner;

/**
 * Helper class to print the infomations of the current board class
 * 
 * @author wangding1
 *
 */
public class PrintMap {
	String display[];
	Location map[][];
	Map<Rooms, Room> rooms;
	String key;

	/*
	 * Constructor which sets up the infomation that needs to be printed
	 */
	public PrintMap(Location[][] map, Map<Rooms, Room> rooms) {
		this.display = new String[map.length];
		this.rooms = rooms;
		this.map = map;
		makeDisplay();
	}

	/**
	 * Constructs the display array which shows whats inside the array..
	 */
	private void makeDisplay() {
		String s = "	Key:\r\n" + "\r\n" + "	[ ] = Empty Area\r\n" + "	[*] = Room Entrance\r\n"
				+ "	<1-9> = Door Number\r\n" + "	{A-Z} = Character Piece\r\n" + "	\r\n" + "	\r\n"
				+ "	Kitchen: " + rooms.get(Rooms.Kitchen).getItems() + "\r\n" + "	\r\n" + "	Ball_Room: "
				+ rooms.get(Rooms.Ball_Room).getItems() + "\r\n" + "	\r\n" + "	Conservatory: "
				+ rooms.get(Rooms.Conservatory).getItems() + "\r\n" + "	\r\n" + "	Billiard_Room: "
				+ rooms.get(Rooms.Billiard_Room).getItems() + "\r\n" + "	\r\n" + "	Library: "
				+ rooms.get(Rooms.Library).getItems() + "\r\n" + "	\r\n" + "	Study: "
				+ rooms.get(Rooms.Study).getItems() + "\r\n" + "	\r\n" + "	Hall: "
				+ rooms.get(Rooms.Hall).getItems() + "\r\n" + "	\r\n" + "	Lounge: "
				+ rooms.get(Rooms.Lounge).getItems() + "\r\n" + "	\r\n" + "	Dining_Room: "
				+ rooms.get(Rooms.Dinning_Room).getItems() + "";

		Scanner scan = new Scanner(s);

		for (int i = 0; i < display.length; i++) {
			if (scan.hasNext())
				display[i] = scan.nextLine();
		}

	}

	/*
	 * Returns the constructed board infomation
	 */
	public String toString() {
		String s = "\n";
		// for row
		for (int i = 0; i < map.length; i++) {

			// for col
			s += "|";
			for (int j = 0; j < map[0].length; j++) {

				// Kitchen
				if (i == 4 && j == 2) {
					s += "Kitchen  ";
					j += 2;
				}

				// Ball_Room
				else if (i == 5 && j == 10) {
					s += "Ball_Room";
					j += 2;
				}

				// Ball_Room
				else if (i == 5 && j == 10) {
					s += "Ball_Room";
					j += 2;
				}

				// Conservetory
				else if (i == 3 && j == 19) {
					s += "Conservatory";
					j += 3;
				}
				// Billiard_Room
				else if (i == 10 && j == 19) {
					s += "Billiard_Room  ";
					j += 4;
				}
				// Library
				else if (i == 16 && j == 19) {
					s += "Library  ";
					j += 2;
				}
				// Study
				else if (i == 22 && j == 19) {
					s += "Study ";
					j += 1;
				}
				// Hall
				else if (i == 20 && j == 11) {
					s += "Hall  ";
					j += 1;
				}
				// Lounge
				else if (i == 21 && j == 3) {
					s += "Lounge";
					j += 1;
				}
				// Dinning_Room
				else if (i == 12 && j == 2) {
					s += "Dinning_Room";
					j += 3;
				}

				// if out of bound
				else if (map[i][j] == null)
					s += "   ";
				// if exsists
				else
					s += map[i][j].toString();
			}
			s += "|" + display[i] + "\n";
		}
		return s;
	}

}

package game;

/**
 * The player has to match the solutions (character, weapon, room) in order to
 * win.
 * 
 * @author wangding1
 *
 */
public class Solution {

	private Characters character;
	private Weapons weapon;
	private Rooms room;

	/**
	 * Takes in a character c weapon w and room r enum to create a solution.
	 * 
	 * @param c
	 * @param w
	 * @param r
	 */
	public Solution(Characters character, Weapons weapon, Rooms room) {
		this.character = character;
		this.weapon = weapon;
		this.room = room;
	}

	/**
	 * Custom equals method to compare solutions and see if it matches the other
	 * 
	 * @param other Solution
	 * @return boolean
	 */
	public boolean equals(Solution other) {
		return (character == other.character && weapon == other.weapon && room == other.room);
	}

	/**
	 * toString method
	 */
	public String toString() {
		return ("Character: [" + character + "] Weapon:  [" + weapon + "] Room: [" + room + "]");
	}

}

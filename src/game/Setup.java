package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import gui.*;

/**
 * This class sets up the initial cards of the game(Characters, Weapons, Room)
 * and creats a random solution out of the avalible cards.
 * 
 * @author wangding1
 *
 */
public class Setup {

	// GUI which displays game infomation and gets user controls
	private GUI gui;

	// Win condidtion of the game
	private Solution solution;

	// List of players and a list of player cards
	private Map<Characters, Player> players = new LinkedHashMap<>();
	private List<CardType> playerCards = new ArrayList<>();

	/**
	 * Setup constructor which calls all the setup methods to initlize the game
	 * 
	 * @param gui
	 */
	public Setup(GUI gui) {
		this.gui = gui;
		setup();
		makePlayers();
		dealCards();
	}

	/**
	 * Creates the game cards which the player will hold and produce a random
	 * solution to the game.
	 */
	private void setup() {

		// Temp lists for player cards
		List<Characters> tempCharacters = new ArrayList<>(Arrays.asList(Characters.values()));
		List<Weapons> tempWeapons = new ArrayList<>(Arrays.asList(Weapons.values()));
		List<Rooms> tempRooms = new ArrayList<>(Arrays.asList(Rooms.values()));

		// Generating random indexs
		int randomCharacter = (int) (Math.random() * tempCharacters.size());
		int randomWeapon = (int) (Math.random() * tempWeapons.size());
		int randomRoom = (int) (Math.random() * tempRooms.size());

		// Obtaining enums based on random indexes
		Characters c = tempCharacters.remove(randomCharacter);
		Weapons w = tempWeapons.remove(randomWeapon);
		Rooms r = tempRooms.remove(randomRoom);

		// Adding all the temp lists to final List
		playerCards.addAll(tempCharacters);
		playerCards.addAll(tempWeapons);
		playerCards.addAll(tempRooms);

		// randomizing the playerCards for dealing later on
		Collections.shuffle(playerCards);

		// Creating the random Solution
		solution = new Solution(c, w, r);
	}

	/**
	 * Asks the user for inputs to set up the players in the game.
	 */
	private void makePlayers() {

		// Selection of players
		List<Characters> selection = new ArrayList<>(Arrays.asList(Characters.values()));

		// variable to hold the amount of players
		int players = 0;
		boolean fail; // if player imput has failed

		System.out.println("   _____ _                _       \r\n" + "  / ____| |              | |      \r\n"
				+ " | |    | |_   _  ___  __| | ___  \r\n" + " | |    | | | | |/ _ \\/ _` |/ _ \\ \r\n"
				+ " | |____| | |_| |  __/ (_| | (_) |\r\n" + "  \\_____|_|\\__,_|\\___|\\__,_|\\___/ \n");
		do {
			Scanner sc = new Scanner(System.in); // scanner takes in user input
			fail = false;
			System.out.println("How manny players do we want to have? (3-6)");
			try {
				players = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Please enter a number between 3 - 6");
				fail = true;
			}
		} while (fail || players < 3 || players > 6);

		int playerNum = 1;
		do {
			Scanner sc = new Scanner(System.in); // scanner takes in user input
			fail = false;
			// For every player registerd
			for (int i = playerNum; playerNum <= players; playerNum++) {
				Characters character;
				System.out.println("\nPlayer " + playerNum + ": Please select your character using numbers 1 - "
						+ selection.size() + "");

				// For all the possible selection of characters to play
				for (int j = 1; j <= selection.size(); j++) {
					System.out.println(j + ") " + selection.get(j - 1));
				}
				try {
					// Creats the player object with a chosen character and removing the character
					// it from the selection
					int index = sc.nextInt(); // User slection
					character = selection.remove(index - 1);
					this.players.put(character, new Player(character));
				} catch (Exception e) {
					System.out.println("Please enter a number between 1 - " + selection.size());
					fail = true;
					break;
				}
			}

		} while (fail);
		// s.close(); // it closes the System.in
	}

	/**
	 * Splitting all the playerCards randomly to all the players thats been
	 * constructed
	 */
	private void dealCards() {
		Collections.shuffle(playerCards);
		// for all constructed players
		while (!playerCards.isEmpty()) {
			for (Player p : players.values()) {

				// Assign a card each while cards list is not empty
				if (!playerCards.isEmpty()) {
					p.addCard(playerCards.remove(0));
				}
			}
		}
	}
//////////////////////////////////////////////////		Getters and Setters		////////////////////////////////////////////////////////////

	/**
	 * Getter for the final solution
	 * 
	 * @return winning solution
	 */
	public Solution getSolution() {
		return this.solution;
	}

	/**
	 * Getter for all the exsisting players
	 * 
	 * @return list of players
	 */
	public Map<Characters, Player> getPlayers() {
		return this.players;
	}

}

package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.*;

/*
 * Game Panel to show the game.
 * 
 *  @author Biru
 *   
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private final Color HALL = new Color(255, 255, 204);
	private final Color DOOR = new Color(255, 204, 0);
	private final Color DOOR_PLATE = Color.lightGray;
	private final Color PLAYER = Color.pink;

	private Board board;
	private Location[][] map;
	private int rowlength;
	private int collength;
	private JPanel boardPanel;
	private JPanel[][] pns;

	/*
	 * Create the game panel based on the given game
	 * 
	 * @param game
	 */
	public GamePanel(Board board, int WIDTH) {
		this.board = board;
		this.map = board.getMap();
		this.rowlength = map.length;
		this.collength = map[0].length;
		this.pns = new JPanel[rowlength][collength];

		// no layout for the basic canvas
		setLayout(null);

		// draw room name
		roomName();

		// draw panels
		drawPanels();

		// draw initial board
		redraw();
	}

	/*
	 * name the rooms on the board with JLabel
	 */
	public void roomName() {
		// sign Kitchen name on the Board
		JLabel kitch = new JLabel("KITCHEN");
		kitch.setFont(new Font("Castellar", 1, 16));
		kitch.setBounds(20, 40, 90, 25);
		kitch.setOpaque(true);
		add(kitch);

		// sign Ball room name on the Board
		JLabel ballRoom = new JLabel("Ball Room");
		ballRoom.setFont(new Font("Castellar", 1, 16));
		ballRoom.setBounds(190, 60, 120, 25);
		ballRoom.setOpaque(true);
		add(ballRoom);

		// sign conservatory name on the Board
		JLabel Conserv = new JLabel("Conservatory");
		Conserv.setFont(new Font("Castellar", 1, 12));
		Conserv.setBounds(360, 5, 125, 25);
		Conserv.setOpaque(true);
		add(Conserv);

		// sign Dinning room name on the Board
		JLabel dinni = new JLabel("Dinning Room");
		dinni.setFont(new Font("Castellar", 1, 14));
		dinni.setBounds(10, 240, 140, 25);
		dinni.setOpaque(true);
		add(dinni);

		// sign Billiard room name on the Board
		JLabel billi = new JLabel("Billiard Room");
		billi.setFont(new Font("Castellar", 1, 12));
		billi.setBounds(370, 200, 122, 25);
		billi.setOpaque(true);
		add(billi);

		// sign library name on the Board
		JLabel libr = new JLabel("Library");
		libr.setFont(new Font("Castellar", 1, 15));
		libr.setBounds(390, 320, 80, 25);
		libr.setOpaque(true);
		add(libr);

		// sign Lounge name on the Board
		JLabel lounge = new JLabel("Lounge");
		lounge.setFont(new Font("Castellar", 1, 15));
		lounge.setBounds(30, 420, 80, 25);
		lounge.setOpaque(true);
		add(lounge);

		// sign Hall name on the Board
		JLabel hall = new JLabel("Hall");
		hall.setFont(new Font("Castellar", 1, 15));
		hall.setBounds(220, 420, 50, 25);
		hall.setOpaque(true);
		add(hall);

		// sign Study name on the Board
		JLabel study = new JLabel("Study");
		study.setFont(new Font("Castellar", 1, 15));
		study.setBounds(395, 440, 62, 25);
		study.setOpaque(true);
		add(study);
	}

	/*
	 * set location panels
	 */
	private void drawPanels() {
		this.boardPanel = new JPanel(new GridLayout(rowlength, collength, 1, 1));
		for (int i = 0; i < rowlength; i++) {
			for (int j = 0; j < collength; j++) {
				// each panel is an grid(Location)
				JPanel panel = drawTinyPanel();
				pns[i][j] = panel;
				boardPanel.add(panel);
			}
		}
		// add the drawn board panels to the basic canvas
		boardPanel.setBounds(0, 0, 500, 500);
		add(boardPanel);
	}

	/**
	 * Make the each small JPanel
	 * 
	 * @return
	 */
	private JPanel drawTinyPanel() {
		JPanel p = new JPanel();
		JLabel l = new JLabel();
		l.setFont(new Font("Castellar", 1, 18));
		l.setBounds(0, -6, 30, 30);
		p.add(l);
		p.setLayout(null);
		p.setSize(new Dimension(30, 30));
		return p;

	}

	/*
	 * draw on the location panels
	 */
	public void redraw() {
		if (pns != null) {
			for (int i = 0; i < rowlength; i++) {
				for (int j = 0; j < collength; j++) {
					JLabel name = (JLabel) pns[i][j].getComponent(0);
					if (map[i][j] == null) {
						pns[i][j].setBackground(Color.gray);

					} else {
						name.setText(null);
						// corridor
						if (map[i][j].toString() == "[ ]") {
							pns[i][j].setBackground(HALL);
						}
						// room entrance
						else if (map[i][j].toString() == "[*]") {
							pns[i][j].setBackground(DOOR);
						}
						// door number
						else if (map[i][j].toString().contains("<")) {
							name.setText(map[i][j].toString().substring(1, 2));
							pns[i][j].setBackground(DOOR_PLATE);
						}
						// player position
						else if (map[i][j].toString().contains("{")) {
							name.setText(map[i][j].toString().substring(1, 2));
							pns[i][j].setBackground(PLAYER);
						}
						// player position
						else if (map[i][j].toString() == "[x]") {
							name.setText(map[i][j].toString().substring(1, 2));
							pns[i][j].setBackground(HALL);
						}
					}
				}
			}
		}
	}

	/*
	 * return location panels
	 */
	public JPanel getBoardPanel() {
		return boardPanel;
	}

}
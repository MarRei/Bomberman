package UI;

import core.*;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

public class Panel extends JPanel implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * job: central processing unit for textures, images and objects game gets
	 * updated and drawn here. contains Thread, KeyListeners and paintComponents
	 * method
	 * <p>
	 * ToDo: KeyListeners to move player! (priority!)
	 */

	int globalWidth = 1280, globalHeight = 720;
	boolean quitGame = false;

	
	// resources
	Player player;
	ArrayList<Player> playerList;
	AI aiPlayer;
	Explosion explosion;
	Tile[][] grid;
	Collision coll;
	Timer timer;
	
	
	public Panel() {

		setLayout(null);
		setSize(globalWidth, globalHeight);
		setFocusable(true);
		requestFocusInWindow();

		playerList = new ArrayList<>();
		
		initGame();

		Thread t = new Thread(() -> {

			/**
			 * job: keeps the image on screen fresh (14/05/18)
			 *
			 */

			while (quitGame == false) {

				update();
				repaint();

				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	// initialize the Game
	public void initGame() {

		/**
		 * job: initialises all objects necessary to draw the game (CONSTRUCTOR!)
		 * current: draws player only (14/05/18)
		 */


		player = new Player(110, 560, 25, 25);
		playerList.add(player);
		aiPlayer = new AI();
		playerList.add(aiPlayer.getAiPlayer());
		coll = new Collision();
		timer = new Timer();
		explosion = new Explosion();


		// create grid
		grid = new Tile[11][15];
		int x = 50;
		int y = 50;
		int size = 50;

		for (int i = 0; i < 11; i++) {
			y += 50;
			for (int j = 0; j < 15; j++) {
				grid[i][j] = new Tile(x, y, size, size);
				x += 50;
			}
			x = 50;
		}
		y = 50;

		setTileIndex();
		setPowerUps();
	}

	// update Gamelogic
	public void update() {

		coll.checkIsDead(playerList, grid);

		updateActivePlayers();

		aiPlayer.checkSurroundings(grid, playerList);
		aiPlayer.moveLikeADonkey();
		


	}

	// draw the Game
	@Override
	protected void paintComponent(Graphics graphics) {

		graphics.clearRect(0, 0, globalWidth, globalHeight);

		// draw Tiles
		for (int index = 0; index < 8; index++) {
			drawTiles((byte) index, graphics);
		}

		// turn player black
		graphics.fillRect(player.x, player.y, player.width, player.height);
		graphics.fillRect(aiPlayer.getAiPlayer().x, aiPlayer.getAiPlayer().y,
				aiPlayer.getAiPlayer().width, aiPlayer.getAiPlayer().height);

	}




	public void setPowerUps()
	{
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if(grid[i][j].getIndex()==1)
				{
					if(Math.random()*100>55)
					{
						Random r = new Random();
							grid[i][j].setPowerUpMarker(r.nextInt(2 + 1) + 1);
					}
				}
			}
		}
	}


	// setting Tiles
	public void setTileIndex() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = grid[i][j];

				// setting indestructable Tiles
				if (i % 2 == 0 && j % 2 == 0) {
					t.setIndex((byte) 2);
				}

				// setting destructable Tiles
				if ((i % 2 == 1 && j % 2 == 0) || (i % 2 == 0 && j % 2 == 1)) {
					t.setIndex((byte) 1);
				}

				// sets a frame of indestructable Tiles
				for (int x = 0; x < 11; x++) {
					grid[i][0].setIndex((byte) 2);
					grid[i][14].setIndex((byte) 2);
				}
				for (int y = 0; y < 15; y++) {
					grid[0][j].setIndex((byte) 2);
					grid[10][j].setIndex((byte) 2);
				}
				
				// setting individual Tiles
				grid[1][2].setIndex((byte) 0);
				grid[1][12].setIndex((byte) 0);
				grid[2][1].setIndex((byte) 0);
				grid[2][13].setIndex((byte) 0);
				grid[8][1].setIndex((byte) 0);
				grid[8][12].setIndex((byte) 0);
				grid[9][2].setIndex((byte) 0);
				grid[9][13].setIndex((byte) 0);
				
				

			}
		}
		return;
	}

	// draw Tiles
	public void drawTiles(byte index, Graphics graphics) {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 14; j++) {
				Tile t = grid[i][j];
				if (t.getIndex() == index) {
					graphics.drawRect(t.x, t.y, t.width, t.height);
					graphics.drawString(Integer.toString(index), t.x + 22, t.y + 28);
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {


		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {

			player.setMovementY(-player.getSpeed());

		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {

			player.setMovementX(player.getSpeed());

		} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {

			player.setMovementY(player.getSpeed());

		} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {

			player.setMovementX(-player.getSpeed());

		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			player.plantBomb(grid, coll);
			explosion.setDeadlyTiles(grid, player, timer);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_RIGHT) {

			player.setMovementX(0);

		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S
				|| e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {

			player.setMovementY(0);
		}
	}

	public void updateActivePlayers(){

		for(Player p : playerList){

			if(!p.isDead()){
				p = p.determinePos();
				coll.check(p, grid);
				coll.pickUpPower(p, grid);
				p.setPlayerLines();
			}

		}

	}
}

package UI;

import core.AI;
import core.Collision;
import core.Explosion;
import core.Player;
import core.Tile;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;

	int globalWidth = 1280, globalHeight = 720;
	boolean quitGame = false;

	// resources
	Player player;
	Explosion explosion;
	Tile[][] grid;
	Collision coll;
	Tile t;
	Timer timer;
	AI bot1, bot2;
	File dir;
	String txtPath;

	public Panel() {

		setLayout(null);
		setSize(globalWidth, globalHeight);
		setFocusable(true);
		requestFocusInWindow();

		initGame();

		Thread t = new Thread(() -> {

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

	// update Gamelogic
	public void update() {

		if (!player.isDead()) {
		
			player = player.determinePos();
			coll.checkInitialBomb(player, t);
			coll.check(player, grid);
			coll.checkIsDead(player, grid);
			coll.pickUpPower(player, grid);
			
			bot1.checkDirections(grid, player);
			coll.check(bot1, grid);
			bot1 = (AI) bot1.determinePos();
		//	bot2 = (AI) bot2.determinePos();
		//	bot2.checkDirections(grid, player);
		//	coll.check(bot2, grid);
		//coll.pickUpPower(bot1, grid);
			
		}

		

		//bot2 = (AI) bot2.determinePos();
		//bot2.checkDirections(grid, player);
		//coll.check(bot2, grid);
		
	}
	

	// draw the Game
	@Override
	protected void paintComponent(Graphics graphics) {

		graphics.clearRect(0, 0, globalWidth, globalHeight);

		// draw Tiles
		for (int index = 0; index < 8; index++) {
			drawTiles((byte) index, graphics);
		}
		
		try {
			drawTextures(graphics);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// turn player black
		graphics.fillRect(player.x, player.y, player.width, player.height);
		graphics.fillRect(bot1.x, bot1.y, bot1.width, bot1.height);
		graphics.fillRect(bot2.x, bot2.y, bot2.width, bot2.height);

		// UI elements
		graphics.drawString("Bombs: " + player.getInventory(), 1000, 250);
		graphics.drawString("Bombrange: " + player.getBombRange(), 1000, 300);
		graphics.drawString("Speed: " + player.getSpeed(), 1000, 350);
	}

	// initialize the Game
	public void initGame() {

		bot1 = new AI(710, 560, 25, 25);
		bot2 = new AI(715, 160, 25, 25);
		player = new Player(110, 160, 25, 25);
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
		
		//setTextures((byte) 0);
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
			t = coll.checkTile(player, grid, (byte) 3);

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

		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			player.setInitialBomb(false);
		}

	}

	public void setPowerUps() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if (grid[i][j].getIndex() == 1) {
					if (Math.random() * 100 > 55) {
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
				grid[8][13].setIndex((byte) 0);
				grid[9][2].setIndex((byte) 0);
				grid[9][12].setIndex((byte) 0);
				

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
	
	public void setTextures(byte index) {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 14; j++) {
				Tile t = grid[i][j];
				if (t.getIndex() == index) {
					t.setImage("/textures/" +index +".png");
					
				}
			}
		}
	}
	
	public void drawTextures(Graphics g) throws IOException {
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 14; j++) {
				Tile t = grid[i][j];
				if (t.getIndex() == 0) {
					g.drawImage(ImageIO.read(new File(getTexturePath() + "0.png")), t.x , t.y, null);	
				}
				if (t.getIndex() == 1) {
					g.drawImage(ImageIO.read(new File(getTexturePath() + "1.png")), t.x , t.y, null);	
				}
				if (t.getIndex() == 2) {
					g.drawImage(ImageIO.read(new File(getTexturePath() + "2.png")), t.x , t.y, null);	
				}
			}
		}
	}
	
	public String getTexturePath() {
		
		String txt = "";
		
		dir = new File("TPI.tpi");
		txt = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().indexOf("TPI.tpi"));
		txt = txt + "textures\\";
		
		return txt;
	}
}

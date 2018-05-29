package UI;

import core.Collision;
import core.Player;
import core.Tile;
import java.awt.geom.Line2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel implements KeyListener {

    /**
     * job: central processing unit for textures, images and objects
     * game gets updated and drawn here. contains Thread, KeyListeners and paintComponents method
     * <p>
     * ToDo: KeyListeners to move player! (priority!)
     */

    int globalWidth = 1280, globalHeight = 720;
    boolean quitGame = false;


    //resources

    Player player;
    Tile[][] grid;
    Collision coll;
    Line2D top;
    Line2D bottom;
    Line2D left;
    Line2D right;
    
    public Panel() {

        setLayout(null);
        setSize(globalWidth, globalHeight);
        setFocusable(true);
        requestFocusInWindow();

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

    //override for paintcomponent method. can only be accessed through Panel.repaint

    public void update() {
    	player = determinePos(player);
    	player = coll.check(player, grid, top, bottom, left, right);
    	//coll.tileDetection(player, grid);
    	top.setLine(player.x + 2, player.y, player.x + 23, player.y);
    	bottom.setLine(player.x + 2, player.y + 25, player.x + 23, player.y + 25);
    	left.setLine(player.x, player.y + 23 , player.x , player.y + 2);
    	right.setLine(player.x + 25, player.y + 23 , player.x + 25, player.y + 2);
		
    	
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {

        graphics.clearRect(0, 0, globalWidth, globalHeight);
        
        // draw grid
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		graphics.drawRect(t.x, t.y, t.width, t.height);
        	}		
        }
        
        // draw Tiles
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 2) {
        			graphics.drawRect(t.x, t.y, t.width, t.height);
        			graphics.drawString("2", t.x + 22, t.y + 28);		// draw "2" for indestructable Tiles
        			graphics.fillRect(t.x, t.y, t.width, t.height);
        		}
        	}
        }
        
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 0) {
        			graphics.drawString("0", t.x + 22, t.y + 28);		// draw "0" for walkable Tiles
        		}
        	}
        }
        
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 1) {
        			graphics.drawString("1", t.x + 22, t.y + 28);		// draw "1" for destructable Tiles
        			graphics.fillRect(t.x + 12, t.y + 12, 25, 25);
        		}
        	} 
		}

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 13; j++) {
                Tile t = grid[i][j];
                if (t.getIndex() == (byte) 5) {
                    graphics.drawString("3", t.x + 22, t.y + 28);		// draw "0" for walkable Tiles
                }
            }
        }
        
        graphics.fillRect(player.x, player.y, player.width, player.height);
        
        }

    
    
    
    public void initGame() {

        /**
         * job: initialises all objects necessary to draw the game (CONSTRUCTOR!)
         * current: draws player only (14/05/18)
         */

    	top = new Line2D.Float();
    	bottom = new Line2D.Float();
    	left = new Line2D.Float();
    	right = new Line2D.Float();
    	
        player = new Player(50, 600, 25, 25);
        
        coll = new Collision();
        
        //create grid
        grid = new Tile[9][13];	
        int x = 50;
        int y = 50;
        int size = 50;
        
        for (int i = 0; i < 9; i++) {
        	y += 50;
        	for (int j = 0; j < 13; j++) {
        		grid[i][j] = new Tile(x,y,size,size);
        		x += 50;
        	}
        	x = 50;
        }
        y = 50;

        setTileIndex();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //System.out.println("Key Pressed: "  + e);

        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {

            player.setMovementY(-2);

        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {

            player.setMovementX(2);

        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {

            player.setMovementY(2);

        } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {

            player.setMovementX(-2);

        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if(coll.checkTile(player, grid) != null){
                coll.checkTile(player, grid).setIndex((byte) 5);
            }
            

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A ||
                e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {

            player.setMovementX(0);

        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S ||
                e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {

            player.setMovementY(0);
        }
    }

    public Player determinePos(Player p){

        p.y += p.getMovementY();
        p.x += p.getMovementX();
        return p;    
    }
    
    // setting Tiles
    public void setTileIndex() {
    	for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		
        		// setting indestructable Tiles
        		if ((i % 2 == 1 && j % 2 == 1)) {
        			t.setIndex((byte) 2);		
        		}
        		
        		// setting destructable Tiles
        		if ((i %2 == 0 && j % 2 == 1) || (i %2 == 1 && j % 2 == 0)) {
        			t.setIndex((byte) 1);		
        		}
        		
        		// setting walkable Tiles
        		if (i %2 == 0 && j % 2 == 0) {
        			t.setIndex((byte) 0);		
        		}
        		
        		grid[0][1].setIndex((byte) 0);
        		grid[0][11].setIndex((byte) 0);
        		grid[1][0].setIndex((byte) 0);
        		grid[1][12].setIndex((byte) 0);
        		grid[7][0].setIndex((byte) 0);
        		grid[7][12].setIndex((byte) 0);
        		grid[8][11].setIndex((byte) 0);
        		grid[8][1].setIndex((byte) 0);
        			
        	}
    	}
		return;	
    }
}

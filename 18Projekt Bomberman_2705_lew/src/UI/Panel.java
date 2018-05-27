package UI;

import core.Collision;
import core.Player;
import core.Tile;
import sun.java2d.loops.DrawRect;

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
    	setTileIndex();
    	player = coll.check(player, grid);
   
		
    	
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
        
        // red color for collision Tiles
        for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if ((j % 2 == 1 && i % 2 == 1)) {
        			graphics.setColor(Color.red);
        			graphics.drawRect(t.x, t.y, t.width, t.height);
        			
        		}
        	}
        }
        
        graphics.drawRect(player.x, player.y,
                player.width, player.height);
        }
        
    
    
    
    public void initGame() {

        /**
         * job: initialises all objects necessary to draw the game (CONSTRUCTOR!)
         * current: draws player only (14/05/18)
         */

        player = new Player(750, 300, 50, 50);
        
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

            System.out.println("Bomb has been planted");

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
    
    // setting indestructable Tiles
    public void setTileIndex() {
    	for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if ((j % 2 == 1 && i % 2 == 1)) {
        			t.setIndex((byte) 2);
        		}
        	}
    	}
		return;	
    }
}

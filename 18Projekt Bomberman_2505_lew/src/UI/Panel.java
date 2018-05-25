package UI;

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

    int globalWidth = 1920, globalHeight = 1080;
    boolean quitGame = false;


    //resources

    Player player;
    Tile[][] grid;
    private int tileX = 1;
	private int tileY = 1;
    
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

    @Override
    protected void paintComponent(Graphics graphics) {

        graphics.clearRect(0, 0, globalWidth, globalHeight);
        
        for (Tile[] t: grid) {
        	setTileY(getTileY() + 1);
            for (Tile elem: t) {
				graphics.drawRect(getTileX()*50, getTileY()*50, Tile.size, Tile.size);
				setTileX(getTileX() + 1);
            } 
            setTileX(1);
        } 
        setTileY(1);
        
        player = determinePos(player);
        
        graphics.drawRect(player.getPosX(), player.getPosY(),
                player.getWidthX(), player.getHeightY());
    }
    
    


    public void initGame() {

        /**
         * job: initialises all objects necessary to draw the game (CONSTRUCTOR!)
         * current: draws player only (14/05/18)
         */

        player = new Player(100, 200, 25, 75);
        
        grid = new Tile[][] {
        	{new Tile((byte) 0),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 0)},
        	{new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0)},
        	{new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1)},
        	{new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1)},
        	{new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1)},
        	{new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 1)},
        	{new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1)},
        	{new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0),new Tile((byte) 2),new Tile((byte) 0)},
        	{new Tile((byte) 0),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 1),new Tile((byte) 0),new Tile((byte) 0)},
        };
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

        p.setPosX(p.getPosX()+p.getMovementX());
        p.setPosY(p.getPosY()+p.getMovementY());

        return p;
    }public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
    
    

}

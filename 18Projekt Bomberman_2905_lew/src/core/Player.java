package core;

import java.awt.*;
import java.awt.geom.Line2D;

public class Player extends Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int movementX, movementY;
    private int bombMax = 2;
    private int inventory = bombMax;
    private int bombRange=2;
    
    Line2D top;
	Line2D bottom;
	Line2D left;
	Line2D right;
	
	public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
        
        top = new Line2D.Float();
		bottom = new Line2D.Float();
		left = new Line2D.Float();
		right = new Line2D.Float();

    }
	
	
	// set Lines for Collision Check
	public void setPlayerLines() {
		top.setLine(x + 2, y, x + 23, y);
		bottom.setLine(x + 2, y + 25, x + 23, y + 25);
		left.setLine(x, y + 23, x, y + 2);
		right.setLine(x + 25, y + 23, x + 25, y + 2);
	}
	
	
	// determine Posiotion of Player
	public Player determinePos() {
		y += getMovementY();
		x += getMovementX();
		return this;
	}
	
	
	// turn walkable Tile into Bomb Tile
	public void plantBomb(Tile [][] grid, Collision coll) {
		if (getInventory() > 0) {
			if (coll.checkTile(this, grid) != null) {
				coll.checkTile(this, grid).setIndex((byte) 3);
				setInventory(getInventory() - 1);
				if (getInventory() < getBombMax()) {

					// wait 3 seconds after Bomb planted before refilling inventory
					new java.util.Timer().schedule(new java.util.TimerTask() {
						@Override
						public void run() {
							if (getInventory() < getBombMax()) {
								setInventory(getInventory() + 1);

							}
						}
					}, 3000);
				}
			}
		}
		return;
	}
	

	
	
	// Getter & Setter
    public int getMovementX() {
        return movementX;
    }

    public void setMovementX(int movement) {
        this.movementX = movement;
    }

    public int getMovementY() {
        return movementY;
    }

    public void setMovementY(int movement) {
        this.movementY = movement;
    }
    
    public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public int getBombMax() {
		return bombMax;
	}

	public void setBombMax(int bombMax) {
		this.bombMax = bombMax;
	}

	public int getBombRange() {
		return bombRange;
	}

	public void setBombRange(int bombRange) {
		this.bombRange = bombRange;
	}
}

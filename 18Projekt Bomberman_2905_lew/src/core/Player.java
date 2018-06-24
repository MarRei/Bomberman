package core;

import java.awt.*;

public class Player extends Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int movementX, movementY;
    private int bombMax = 1;
    private int inventory = 1;
    private int bombRange=1;
    private int speed = 2;
    private boolean isDead=false;
    private boolean initialBomb = false;
    
	
	public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
    }
	

	public boolean isInitialBomb() {
		return initialBomb;
	}


	public void setInitialBomb(boolean initialBomb) {
		this.initialBomb = initialBomb;
	}


	// determine Posiotion of Player
	public Player determinePos() {
		x += getMovementX();
		y += getMovementY();
		return this;
	}
	
	
	// turn walkable Tile into Bomb Tile
	public void plantBomb(Tile [][] grid, Collision coll) {
		if (getInventory() > 0) {
			if (coll.checkTile(this, grid,(byte)0) != null) {
				coll.checkTile(this, grid,(byte)0).setIndex((byte) 3);
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
	
	 public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
}

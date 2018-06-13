package core;

import java.awt.*;

public class Player extends Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int movementX, movementY;
    private int bombMax = 2;
    private int inventory = bombMax;
    private int bombRange=2;

	
	
	public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

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

package core;

import java.awt.*;

public class Tile extends Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     *
     * tiles make up the environment of bomberman. they can be
     *  0. walkable, 1. destructible, 2. indestructible, 3. bombs , 4. deadly, 5. power ups
     *
     * ToDo: list of tiles with index 0-5 determining the kind of tile to draw
     */

    private byte index; // index determines what kind of tile is drawn on screen. (0-5)
    private boolean bombMarker = false;
    private int powerUpMarker = 0;// determines if a bomb is placed so it wont get detected by setDeadlyTiles() 
    
	

	public int getPowerUpMarker() {
		return powerUpMarker;
	}

	public void setPowerUpMarker(int powerUpMarker) {
		this.powerUpMarker = powerUpMarker;
	}

	public Tile(int x, int y, int width, int height) {
		
    	super(x, y, width, height);
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }
    
    public boolean getMarker() {
		return bombMarker;
	}

	public void setMarker(boolean marker) {
		this.bombMarker = marker;
	}  
}

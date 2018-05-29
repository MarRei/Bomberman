package core;

import java.awt.*;

public class Tile extends Rectangle {

    /**
     *
     * tiles make up the environment of bomberman. they can be
     * 1. destructible, 2. indestructible, 3. destroyed, 4. deadly, 5. power ups, 6. bombs
     *
     * ToDo: list of tiles with index 0-5 determining the kind of tile to draw
     */

    private byte index; //index determines what kind of tile is drawn on screen. (0-5)
    
	public Tile(int x, int y, int width, int height){
		
    	super(x, y, width, height);
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }





}

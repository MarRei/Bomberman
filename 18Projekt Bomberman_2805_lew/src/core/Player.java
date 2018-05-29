package core;

import java.awt.*;

public class Player extends Rectangle {

    private int movementX, movementY;
    private String peter;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

    }

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
    public void setKey(String p)
    {
    	peter =p;
    }
    public String getKey()
    {
    	
    	
    return peter;
    }
    public boolean isMoving()
    {
    	if(getMovementX()==2 || getMovementX()==-2||getMovementY()==2||getMovementY()==-2)
    	{
    		return true;
    	}
    	else {
    		return false;
    	}
    }


}

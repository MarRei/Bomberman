package core;

import java.awt.*;

public class Player extends Rectangle {

    private int movementX, movementY;
    private String key;

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

    public void setKey(String p) {
        key = p;
    }

    public String getKey() {


        return key;
    }

    public boolean isMoving() {
        if (getMovementX() != 0 || getMovementY() != 0) {
            return true;
        } else {
            return false;
        }
    }


}

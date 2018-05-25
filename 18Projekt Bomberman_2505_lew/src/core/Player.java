package core;

import java.awt.*;

public class Player extends Rectangle {

    private int posX, posY, widthX, heightY, movementX, movementY;

    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);

        posX = x;
        posY = y;
        widthX = width;
        heightY = height;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidthX() {
        return widthX;
    }

    public void setWidthX(int widthX) {
        this.widthX = widthX;
    }

    public int getHeightY() {
        return heightY;
    }

    public void setHeightY(int heightY) {
        this.heightY = heightY;
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


}

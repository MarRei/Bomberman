package core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile extends Rectangle {

	private static final long serialVersionUID = 1L;

	
	private BufferedImage image;
	private byte index; // index determines what kind of tile is drawn on screen. (0-5)
	private boolean bombMarker = false;
	private int powerUpMarker = 0;// determines if a bomb is placed so it wont get detected by setDeadlyTiles()

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

	public int getPowerUpMarker() {
		return powerUpMarker;
	}

	public void setPowerUpMarker(int powerUpMarker) {
		this.powerUpMarker = powerUpMarker;
	}
	

	public void setImage(String path) {
		try {
			//image =  ImageIO.read(getClass().getClassLoader().getResourceAsStream("/textures/0.png"));
			image = ImageIO.read(new File("C:\\Users\\Murphy\\eclipse-workspace\\Projekt Bomberman\\src\\textures\\0.png"));
		} catch (IOException e) {
			System.out.println("Image not found!");
		}
	}
	
	public BufferedImage getImage() {
		return image;	
	}	

}

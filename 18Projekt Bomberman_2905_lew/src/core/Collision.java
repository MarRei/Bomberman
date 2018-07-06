package core;

import java.awt.Rectangle;

public class Collision {

	public Collision() {

	}

	public void check(Player p, Tile[][] grid) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = grid[i][j];
				if (t.getIndex() == (byte) 1 || t.getIndex() == (byte) 2 // apply Collision on Tiles with index 1 or 2
						|| (p.getInitialBomb() && t.getIndex() == (byte) 3)) {

					if (p.intersects(t)) {
						Rectangle line = p.intersection(t);

						boolean horizontal = false;
						boolean vertical = false;
						boolean leftSide = false;
						boolean topSide = false;

						// Left Side
						if (line.x == t.x) {
							horizontal = true;
							leftSide = true;
						}

						// Right Side
						else if (line.x + line.width == t.x + t.width) {
							horizontal = true;
						}

						// Top
						if (line.y == t.y) {
							vertical = true;
							topSide = true;
						}

						// Bottom
						else if (line.y + line.height == t.y + t.height) {
							vertical = true;
						}

						// Horizontal + Vertical
						if (horizontal && vertical) {
							if (line.width > line.height) {
								horizontal = false;
							} else {
								vertical = false;
							}
						}

						else if (horizontal && p.x + p.width >= line.x) {
							p.x = t.x + t.width;
						}

						if (horizontal) {
							if (leftSide) {
								p.x = t.x - p.width;
							} else {
								p.x = t.x + t.width;
							}
						}

						else if (vertical) {
							if (topSide) {
								p.y = t.y - p.height;
							} else {
								p.y = t.y + t.height;
							}
						}
					}
				}
			}
		}
	}

	

	public void pickUpPower(Player p, Tile[][] grid) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = grid[i][j];
				if (p.intersects(t)) {

					// player speed
					if (t.getIndex() == (byte) 5 && p.getSpeed() < 8) {
						p.setSpeed(p.getSpeed() + 1);
						t.setIndex((byte) 0);
					}

					// bombrange
					else if (t.getIndex() == (byte) 6 && p.getBombRange() < 6) {
						p.setBombRange(p.getBombRange() + 1);
						t.setIndex((byte) 0);
					}

					// player inventory
					else if (t.getIndex() == (byte) 7 && p.getBombMax() < 6) {
						p.setBombMax(p.getBombMax() + 1);
						p.setInventory(p.getInventory() + 1);
						t.setIndex((byte) 0);
					}
				}
			}
		}
	}

	public void checkIsDead(Player p, Tile[][] tiles) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = tiles[i][j];

				if (t.getIndex() == (byte) 4 && p.intersects(t)) {
					System.out.println("You are DEADO");
					p.setDead(true);
				}
			}
		}
	}

	public void checkInitialBomb(Player p, Tile t) {

		if (t != null) {
			if (!p.intersects(t)) {
				p.setInitialBomb(true);
			}
		}
	}

	public Tile checkTile(Player p, Tile[][] tiles, byte index) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = tiles[i][j];

				if (t.getIndex() == (byte) index && p.intersects(t)) {
					return t;
				}
			}
		}
		return null;
	}
}
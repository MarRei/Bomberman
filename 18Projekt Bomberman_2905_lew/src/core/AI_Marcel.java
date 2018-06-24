package core;

public class AI_Marcel extends Player {
	
	Collision coll;
	Tile[][] grid;
	
	public AI_Marcel(int x, int y, int width, int height) {
		
		super(x, y, width, height);
		
		this.setSpeed(2);
	}
	
	
	public void move(Player p) {
		if (this.x > p.x) {
			this.setMovementX(-3);
		
		}
		
		if (this.x < p.x) {
			this.setMovementX(3);
		}
		
	}
	
	
	
}

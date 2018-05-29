package core;

public class Collision {

	public Collision() {
		
	}
	
	public Player check(Player p, Tile[][] grid) {
	    for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 1 || t.getIndex() == (byte) 2) {		// apply Collision only on Tiles with index 2
        		
        		if (p.intersects(t) && p.x < t.x - 45) {
        			p.x = t.x - 50;
        			
        			System.out.println("Player intersects from left");
        		}	
        			
        		if (p.intersects(t) && p.x > t.x + 45) {
            		p.x = t.x + 50;
            			
            		System.out.println("Player intersects from right");
        		}
        		
        		if (p.intersects(t) && p.y < t.y - 45) {
            		p.y = t.y - 50;
            			
            		System.out.println("Player intersects from above");
        		}
        		
        		if (p.intersects(t) && p.y < t.y + 49) {
            		p.y = t.y + 50;
            			
            		System.out.println("Player intersects from below");
        		}

        		}
        	}
        }
	    
		return p;
	}
}

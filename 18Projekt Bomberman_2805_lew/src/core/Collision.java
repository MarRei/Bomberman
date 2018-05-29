package core;

import UI.Panel;

public class Collision {
	
	public Collision() {
		 
		
	}
	
	public Player check(Player p, Tile[][] grid) {
	    for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 1 || t.getIndex() == (byte) 2) {		// apply Collision only on Tiles with index 2
        		
        		if (p.intersects(t) && p.getKey()=="A" ) {
        			p.x = t.x+50 ;
        			
        			System.out.println("Player intersects from left");
        		}	
        			
        		
        		if (p.intersects(t) && p.getKey()=="D") {
            		p.x = t.x - 25;
            			
            		System.out.println("Player intersects from right");
        		}
        		
        		if (p.intersects(t) && p.getKey()=="S") {
            		p.y = t.y-25 ;
            			
            		System.out.println("Player intersects from above");
        		}
        		if (p.intersects(t) && p.getKey()=="W") {
            		p.y = t.y + 50;
            			
            		System.out.println("Player intersects from below");
        		}

        		}
        	}
        }
	    
		return p;
	}
}

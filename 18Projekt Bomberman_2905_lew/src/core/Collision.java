package core;
import java.awt.geom.Line2D;
public class Collision {

	public Collision() {
		
	}
	
	public Player check(Player p, Tile[][] grid, Line2D top, Line2D bottom, Line2D left, Line2D right) {
	    for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 13; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 1 || t.getIndex() == (byte) 2) {		// apply Collision only on Tiles with index 2
        		
        			if (t.intersectsLine(top)) {
	            		p.y = t.y + 51; 
        				System.out.println("Player intersects from below");
        			}
        			
        			if (t.intersectsLine(bottom)) {
	            		p.y = t.y - 26; 
        				System.out.println("Player intersects from above");
        			}
        			
        			if (t.intersectsLine(left)) {
	            		p.x = t.x + 51; 
        				System.out.println("Player intersects from right");
        			}
        			
        			if (t.intersectsLine(right)) {
	            		p.x = t.x - 26; 
        				System.out.println("Player intersects from left");
        			}
        		}
        	}
        }
	    
		return p;
	}

	public Tile checkTile(Player p, Tile[][] tiles){
		for(int i = 0; i<9;i++){
			for(int j = 0; j<13; j++){
                Tile t = tiles[i][j];

                if(t.getIndex() == (byte) 0 && p.intersects(t)){
                	return t;
				}
			}
		}
		return null;
	}
}

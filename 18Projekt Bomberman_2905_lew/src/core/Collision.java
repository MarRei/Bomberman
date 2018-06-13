package core;
import java.awt.geom.Line2D;
public class Collision {

	public Collision() {
		
	}
	
	public Player check(Player p, Tile[][] grid, Line2D top, Line2D bottom, Line2D left, Line2D right) {
	    for (int i = 0; i < 11; i++) {
        	for (int j = 0; j < 15; j++) {
        		Tile t = grid[i][j];
        		if (t.getIndex() == (byte) 1 || t.getIndex() == (byte) 2) {		//  apply Collision on Tiles with index 1 or 2
        		
        			if (t.intersectsLine(top)) {
	            		p.y = t.y + 51; 
        			}
        			
        			if (t.intersectsLine(bottom)) {
	            		p.y = t.y - 26; 	
        			}
        			
        			if (t.intersectsLine(left)) {
	            		p.x = t.x + 51; 	
        			}
        			
        			if (t.intersectsLine(right)) {
	            		p.x = t.x - 26; 	
        			}
        		}
        	}
        }
	    
		return p;
	}
	
	public void checkIsDead(Player p,Tile[][] tiles) {
		for(int i = 0;  i< 11; i++){
			for(int j = 0; j < 15; j++){
                Tile t = tiles[i][j];

                if(t.getIndex() == (byte) 4 && p.intersects(t)){
                	System.out.println("You are DEADO");
                	p.setMovementX(0);
                	p.setMovementY(0);
				}
			}
		}
	}

	public Tile checkTile(Player p, Tile[][] tiles) {
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 15; j++){
                Tile t = tiles[i][j];

                if(t.getIndex() == (byte) 0 && p.intersects(t)){
                	return t;
				}
			}
		}
		return null;
	}
}

package core;

public class Explosion {

public void explosionUp(int ipos, int jpos, byte index,Tile[][]grid,Player player) {
	
	for (int i = 0; i <= player.getBombRange(); i++) {
		grid[ipos][jpos].setIndex(index);
		
		if (grid[ipos - i] [jpos].getIndex() == 1) {
			grid[ipos - i] [jpos].setIndex(index);
			break;	
		}
		
		if (grid[ipos - i] [jpos].getIndex() != 2) {
			grid[ipos - i] [jpos].setIndex(index);	
		}
		
		else {
			break;
		}
	}
}

public void explosionDown(int ipos, int jpos, byte index,Tile[][]grid,Player player) {
	
	for (int i = 0; i <= player.getBombRange(); i++) {
		grid[ipos][jpos].setIndex(index);
		
		if (grid[ipos + i] [jpos].getIndex() == 1) {
			grid[ipos + i] [jpos].setIndex(index);
			break;	
		}
		
		if (grid[ipos + i] [jpos].getIndex() != 2) {
			grid[ipos + i] [jpos].setIndex(index);	
		}
		
		else {
			break;
		}
	}
}

public void explosionRight(int ipos, int jpos, byte index,Tile[][]grid,Player player) {

	for (int i = 0; i <= player.getBombRange(); i++) {
		grid[ipos][jpos].setIndex(index);
	
		if (grid[ipos] [jpos + i].getIndex() == 1) {
			grid[ipos] [jpos + i].setIndex(index);
			break;	
		}
		
		if (grid[ipos] [jpos + i].getIndex() != 2) {
			grid[ipos] [jpos + i].setIndex(index);	
		}
		
		else {
			break;
		}
	}
}

public void explosionLeft(int ipos, int jpos, byte index,Tile[][]grid,Player player) {
	
	for (int i = 0; i <= player.getBombRange(); i++) {
		grid[ipos][jpos].setIndex(index);
		
		if (grid[ipos] [jpos - i].getIndex() == 1) {
			grid[ipos] [jpos - i].setIndex(index);
			break;	
		}
		
		if (grid[ipos] [jpos - i].getIndex() != 2) {
			grid[ipos] [jpos - i].setIndex(index);	
		}
		
		else {
			break;
		}
	}
}
public void explosionAllDirections(int ipos, int jpos, byte index,Tile[][]grid,Player player)

{
	explosionLeft( ipos,  jpos,  index,grid, player);
	explosionRight( ipos,  jpos,  index,grid, player);
	explosionUp( ipos,  jpos,  index,grid, player);
	explosionDown( ipos,  jpos,  index,grid, player);
}


}

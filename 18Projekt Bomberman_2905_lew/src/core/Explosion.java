package core;

import java.util.Timer;

public class Explosion {
	

	public void setDeadlyTiles(Tile[][]grid, Player player, Timer timer) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				int ipos = i;
				int jpos = j;

				if (grid[i][j].getIndex() == (byte) 3) {
					
					if (!grid[i][j].getMarker()) {
						timer.schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								explosionAllDirections(ipos, jpos, (byte)4, grid, player);
								timer.schedule(new java.util.TimerTask() {
									@Override
									public void run() {
										explosionAllDirections(ipos, jpos, (byte)0, grid, player);
										grid[ipos][jpos].setMarker(false);
									}
								}, 1000);
							}
						}, 3000);
					grid[i][j].setMarker(true);
					}
				}
			}
		}
	}

	public void explosionUp(int ipos, int jpos, byte index, Tile[][]grid, Player player) {
		
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
			if(grid[ipos - i] [jpos].getPowerUpMarker()!=0&&index==0)
			{
				grid[ipos - i] [jpos].setIndex((byte)checkPowerUpIndex(grid,ipos-i,jpos));
			}
		}
	}
	
	public void explosionDown(int ipos, int jpos, byte index,Tile[][]grid, Player player) {
		
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
			if(grid[ipos +i] [jpos].getPowerUpMarker()!=0&&index==0)
			{
				grid[ipos +i] [jpos].setIndex((byte)checkPowerUpIndex(grid,ipos+i,jpos));
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
			if(grid[ipos] [jpos+i].getPowerUpMarker()!=0&&index==0)
			{
				grid[ipos] [jpos+i].setIndex((byte)checkPowerUpIndex(grid,ipos,jpos+i));
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
			if(grid[ipos] [jpos-i].getPowerUpMarker()!=0&&index==0)
			{
				grid[ipos ] [jpos-i].setIndex((byte)checkPowerUpIndex(grid,ipos,jpos-i));
			}
		}
	}
	public int checkPowerUpIndex(Tile[][]grid,int ipos, int jpos)
	{
		if(grid[ipos][jpos].getPowerUpMarker()==1)
		{
			return 5;
		}
		if(grid[ipos][jpos].getPowerUpMarker()==2)
		{
			return 6;
		}
		if(grid[ipos][jpos].getPowerUpMarker()==3)
		{
			return 7;
		}
		return 8;
	}

	public void explosionAllDirections(int ipos, int jpos, byte index,Tile[][]grid,Player player) {
		explosionLeft( ipos,  jpos,  index,grid, player);
		explosionRight( ipos,  jpos,  index,grid, player);
		explosionUp( ipos,  jpos,  index,grid, player);
		explosionDown( ipos,  jpos,  index,grid, player);
	}
}

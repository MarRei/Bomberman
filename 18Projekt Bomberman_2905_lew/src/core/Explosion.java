package core;

import java.util.ArrayList;
import java.util.Timer;

public class Explosion {
	public static ArrayList<Tile> tiles;
	int counter = 0;
	public Explosion()
	{
		tiles = new ArrayList<Tile>();
	}

	public void setDeadlyTiles(Tile[][] grid, Player player, Timer timer) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				int ipos = i;
				int jpos = j;
				

				if (grid[i][j].getIndex() == (byte) 3){
					Tile t = grid[i][j];
					if(t.intersects(player)) {
					player.setBombOn(true);
					checkBombrangeAllDirections(ipos,jpos,grid,player);
					if (!grid[i][j].getMarker()) {
						timer.schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								explosionAllDirections(ipos, jpos, (byte) 4, grid, player);
								
								timer.schedule(new java.util.TimerTask() {
									@Override
									public void run() {
										deleteBombRange();
										explosionAllDirections(ipos, jpos, (byte) 0, grid, player);
										grid[ipos][jpos].setMarker(false);
										
										
										counter=0;
										player.setBombOn(false);
									}
								}, 1000);
							}
						}, 3000);
						grid[i][j].setMarker(true);
					}
				}}
			}
		}
	}

	public void explosion(int ipos, int jpos, byte index, Tile[][] grid, Player player, int x, int y) {
		grid[ipos][jpos].setIndex(index);
		for (int i = 1; i <= player.getBombRange(); i++) {
			
			if (index == 0) {
				try {
					if (grid[ipos + i * x][jpos + i * y].getIndex() == 4) {
						grid[ipos + i * x][jpos + i * y].setIndex(index);
						
						
						if (grid[ipos + i * x][jpos + i * y].getPowerUpMarker() != 0 && index == 0) {
							grid[ipos + i * x][jpos + i * y].setIndex((byte) checkPowerUpIndex(grid, ipos + i * x, jpos + i * y));
						}
					}
				} catch (Exception e) {

				}
				
			} else {
				if (grid[ipos + i * x][jpos + i * y].getIndex() == 1) {
					grid[ipos + i * x][jpos + i * y].setIndex(index);
					
					break;
				}

				else if (grid[ipos + i * x][jpos + i * y].getIndex() != 2) {
					grid[ipos + i * x][jpos + i * y].setIndex(index);
					
				}

				else {
					break;
				}
			}
		}
	}
	
	public void deleteBombRange() {
		for(int i=0;i<=counter;i++) {
			tiles.remove(0);
		}
		
	}
	public void checkBombrange(int ipos, int jpos, Tile[][] grid, Player player, int x, int y) {
		
		for (int i = 1; i <= player.getBombRange(); i++) {
			
			
				if (grid[ipos + i * x][jpos + i * y].getIndex() == 1) {
					
					tiles.add(grid[ipos+i*x][jpos+i*y]);
					counter++;
					break;
				}

				else if (grid[ipos + i * x][jpos + i * y].getIndex() != 2) {
					
					tiles.add(grid[ipos+i*x][jpos+i*y]);
					counter++;
				}

				else {
					break;
				}
			}
		}
	
	
	public ArrayList<Tile> getList() {
		return tiles;
	}


	public int checkPowerUpIndex(Tile[][] grid, int ipos, int jpos) {
		if (grid[ipos][jpos].getPowerUpMarker() == 1) {
			return 0;
		}

		if (grid[ipos][jpos].getPowerUpMarker() == 2) {
			return 0;
		}

		if (grid[ipos][jpos].getPowerUpMarker() == 3) {
			return 0;
		}

		return 0;
	}

	public void explosionAllDirections(int ipos, int jpos, byte index, Tile[][] grid, Player player) {
		
		explosion(ipos, jpos, index, grid, player, 1, 0);
		explosion(ipos, jpos, index, grid, player, -1, 0);
		explosion(ipos, jpos, index, grid, player, 0, 1);
		explosion(ipos, jpos, index, grid, player, 0, -1);
	}
	
	public void checkBombrangeAllDirections(int ipos, int jpos,  Tile[][] grid, Player player) {
		tiles.add(grid[ipos][jpos]);
		checkBombrange(ipos, jpos,  grid, player, 1, 0);
		checkBombrange(ipos, jpos,  grid, player, -1, 0);
		checkBombrange(ipos, jpos,  grid, player, 0, 1);
		checkBombrange(ipos, jpos,  grid, player, 0, -1);
	}
}

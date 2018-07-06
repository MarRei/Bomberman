package core;

import java.util.ArrayList;
import java.util.Timer;

public class AI extends Player {

	private static final long serialVersionUID = 1L;

	Explosion explosion;
	Collision coll;
	Timer timer;
	Tile start;
	ArrayList<Tile> PathFinding;
	ArrayList<Tile> walk;
	

	int ipos;
	int jpos;
	double xpos, ypos;
	
	boolean test = false;
	boolean right = false;
	boolean left = false;
	boolean up = false;
	boolean down = false;
	double destinationX = 0;
	double destinationY = 0;
	Tile playerDestination;
	
	
	
	public AI(int x, int y, int width, int height) {
		super(x, y, width, height);
		coll = new Collision();
		explosion = new Explosion();
		timer = new Timer();
		PathFinding = new ArrayList<Tile>();
		walk = new ArrayList<Tile>();
		start = new Tile(700,550,50,50);

	}

	
	
	public void checkDirections(Tile[][] grid, Player p) {

		destinationX = xpos;
		destinationY = ypos;
		playerDestination= new Tile(p.x,p.y,25,25);
		direction = "";
		System.out.println("PathFinding: " +PathFinding);
		System.out.println("Gefährlich: "+explosion.tiles);
		
		
		if(!PathFinding.isEmpty()) {
	//	System.out.println("Walk: "+walk);
		if(this.intersects(PathFinding.get(PathFinding.size()-1)) ) {
			walk.clear();
			start = coll.checkTile(this, grid, (byte)0);
			plantBomb(grid, coll);
			explosion.setDeadlyTiles(grid, this, timer);
		}}
		System.out.println();
		PathFinding.clear();
		if (getBombOn()) {
			searchPath(grid, this);
		}
		else searchPath(grid,p);
	//	 
		 System.out.println("AI Location: " + x + " " + y);
		 if (!walk.contains(coll.checkTile(this, grid, (byte) 0))) {
		 walk.add(coll.checkTile(this, grid, (byte) 0));
		 }
		 if (PathFinding.isEmpty()) {
		 
				walk.clear();
			}
		 
		
		
		
	}

	public void searchPath(Tile[][] grid, Player p) {

		boolean found=false;
		if (explosion.getList().isEmpty()) {
			checkSafety(grid, explosion.getList(), (byte)1, p);
			
		}

		else {
			for (Tile t : explosion.getList()) {
				
				if (this.intersects(t))  {
					found=true;
					checkSafety(grid, explosion.getList(), (byte) 0,this);
					break;
				}

				else if (!this.intersects(t) && !getBombOn()) {
					checkSafety(grid, explosion.getList(), (byte)1, p);

				}
				
			}
			if(found!=true)
			{
				System.out.println(PathFinding);
				move();
			}
		}
	}


	public void checkSafety(Tile[][] grid, ArrayList<Tile> tiles, byte index,Player p) {

		ArrayList<Tile> shortestPathTiles = new ArrayList<Tile>();
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				Tile t = grid[i][j];
				if (t.getIndex() == index && !tiles.contains(t)) {
					shortestPathTiles.add(t);
					
					
				}
			}
		}

	

		if (!shortestPathTiles.isEmpty()) {
			up = false;
			down = false;
			right = false;
			left = false;
			if(getBombOn()) {
			selectionSort(shortestPathTiles, this);
			
			startSearch(grid, shortestPathTiles, (byte) 3 , 1);}		// Fehler vielleicht hier?
																		// AI geht von der 3 runter
			else {
				up = false;
				down = false;
				right = false;
				left = false;
				selectionSort(shortestPathTiles, p);
				
				startSearch(grid, shortestPathTiles, (byte) 0, 2);
			}
		}
		
	}

	public void startSearch(Tile[][] grid, ArrayList<Tile> list, byte index, int fall) {
	
		if(index == 3&&coll.checkTile(this, grid, (byte)3)!=null) {
			start = coll.checkTile(this, grid, (byte) 3);
		//	System.out.println("Start:     "+start);
		}
		ArrayList<Tile> previousPath = new ArrayList<Tile>();
		for (int i = 0; i <= list.size() - 1; i++) {

			Tile destination = list.get(i);
			
			if (fall == 1) {
				previousPath.add(start);
				
			if (findPath(grid, start, destination, previousPath)) {
				xpos = destination.x + 10;
				ypos = destination.y + 10;
			System.out.println("Destination: " +destination.x + " " + destination.y);
				move();
				break;
			}
			
			
			}
			else {
				previousPath.add(start);
					if(!findOne(grid, start, playerDestination, previousPath)) {
						previousPath.clear();
						previousPath.add(start);
					if(findOne(grid, start, destination, previousPath)) {
						System.out.println("DITETETETETER");
					if (up) {
						xpos = destination.x + 10;
						ypos = destination.y + 70;
						move();
						break;
					}
					
					if (down) {
						xpos = destination.x + 10;
						ypos = destination.y - 70;
						move();
						break;
					}
					
					if (right) {
						xpos = destination.x + 70;
						ypos = destination.y + 10;
						move();
						break;
					}
					
					if (left) {
						xpos = destination.x - 70;
						ypos = destination.y + 10;
						move();
						break;
					}
				}
					}
					else {
						if (up) {
							xpos = playerDestination.x + 10;
							ypos = playerDestination.y + 70;
							move();
							break;
						}
						
						if (down) {
							xpos = playerDestination.x + 10;
							ypos = playerDestination.y - 70;
							move();
							break;
						}
						
						if (right) {
							xpos = playerDestination.x + 70;
							ypos = playerDestination.y + 10;
							move();
							break;
						}
						
						if (left) {
							xpos = playerDestination.x - 70;
							ypos = playerDestination.y + 10;
							move();
							break;
						}
					}
			
			}
			previousPath.clear();
		}

	}

	public boolean findPath(Tile[][] grid, Tile start, Tile destination, ArrayList<Tile> previous) {
		int i = start.y / 50 - 2;
		int j = start.x / 50 - 1;
		
		
		if (right || left || up || down) {
			return true;
		} 
		
		if (grid[i - 1][j].getIndex() == 0 || grid[i - 1][j].getIndex() == 5 || grid[i - 1][j].getIndex() == 6
				|| grid[i - 1][j].getIndex() == 7) { // oben
			if (!previous.contains(grid[i - 1][j])) {
				//System.out.println("----------------------------------------");
				if (grid[i - 1][j] != destination) {
					previous.add(grid[i - 1][j]);
					if (!walk.contains(grid[i - 1][j])) {
						PathFinding.add(grid[i - 1][j]);
					}
					findPath(grid, grid[i - 1][j], destination, previous);
				} else {
					PathFinding.add(grid[i - 1][j]);
					up = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		} 
		
		if (grid[i + 1][j].getIndex() == 0 || grid[i + 1][j].getIndex() == 5 || grid[i + 1][j].getIndex() == 6
				|| grid[i + 1][j].getIndex() == 7) { // unten
			if (!previous.contains(grid[i + 1][j])) {
				if (grid[i + 1][j] != destination) {
					previous.add(grid[i + 1][j]);
					if (!walk.contains(grid[i + 1][j])) {
						PathFinding.add(grid[i + 1][j]);
					}
				
					findPath(grid, grid[i + 1][j], destination, previous);

				} else {
					PathFinding.add(grid[i + 1][j]);
					down = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		} 
		
		if (grid[i][j + 1].getIndex() == 0 || grid[i][j + 1].getIndex() == 5 || grid[i][j + 1].getIndex() == 6
				|| grid[i][j + 1].getIndex() == 7) { // rechts
			if (!previous.contains(grid[i][j + 1])) {
				if (grid[i][j + 1] != destination) {
					previous.add(grid[i][j + 1]);
					if (!walk.contains(grid[i][j + 1])) {
						PathFinding.add(grid[i][j + 1]);
					}
				
					findPath(grid, grid[i][j + 1], destination, previous);
				} else {
					PathFinding.add(grid[i][j + 1]);
					right = true;
					return true;
				}
			}
		}
		
		if (right || left || up || down) {
			return true;
		} 
		
		if (grid[i][j - 1].getIndex() == 0 || grid[i][j - 1].getIndex() == 5 || grid[i][j - 1].getIndex() == 6
				|| grid[i][j - 1].getIndex() == 7) { // links
			System.out.println(previous);
			if (!previous.contains(grid[i][j - 1])) {
			
				if (grid[i][j - 1] != destination) {
					previous.add(grid[i][j - 1]);
					if (!walk.contains(grid[i][j - 1])) {
						PathFinding.add(grid[i][j - 1]);
					}
					findPath(grid, grid[i][j - 1], destination, previous);
				} else {
					PathFinding.add(grid[i][j - 1]);
					left = true;
					return true;
				}
			}
		}
		if (right || left || up || down) {
			return true;
		} else {
			if (!PathFinding.isEmpty()) {
				PathFinding.remove(PathFinding.size() - 1);
				}
			return false;
		}
	}

	public void selectionSort(ArrayList<Tile> list, Player p) {
		double miniRange = 0;
		double miniRange2 = 0;
		for (int i = 0; i < list.size(); i++) {
			// find position of smallest num between (i + 1)th element and last element
			int pos = i;
			for (int j = i; j < list.size(); j++) {
				miniRange = Math.sqrt(Math.pow(list.get(j).x - p.x, 2) + Math.pow(list.get(j).y - p.y, 2));
				miniRange2 = Math.sqrt(Math.pow(list.get(pos).x - p.x, 2) + Math.pow(list.get(pos).y - p.y, 2));
				if (miniRange < miniRange2)
					pos = j;
			}
			// Swap min (smallest num) to current position on array
			Tile min = list.get(pos);
			list.set(pos, list.get(i));
			list.set(i, min);
		}
	}
	

	
	public boolean findOne(Tile[][] grid, Tile start, Tile destination, ArrayList<Tile> previous) {
		int i = start.y / 50 - 2;
		int j = start.x / 50 - 1;
	
		if (right || left || up || down) {
			return true;
		}
			
		if (grid[i - 1][j].getIndex() == 0) { 			// oben		
			if (!previous.contains(grid[i - 1][j])) {
				if (grid[i - 2][j] != destination) {
					previous.add(grid[i - 1][j]);
					if(!walk.contains(grid[i - 1][j]))
					{	PathFinding.add(grid[i - 1][j]);
						
					}
					findOne(grid, grid[i - 1][j], destination, previous);
				} else {
			
					PathFinding.add(grid[i - 1][j]);
					up = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		}
		if (grid[i + 1][j].getIndex() == 0) { 			// unten
			
			if (!previous.contains(grid[i + 1][j])) {
				if (grid[i+2][j] != destination) {
					previous.add(grid[i + 1][j]);
					if (!walk.contains(grid[i + 1][j])) {
						PathFinding.add(grid[i + 1][j]);
					}
					findOne(grid, grid[i + 1][j], destination, previous);

				} else {
					PathFinding.add(grid[i + 1][j]);
					down = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		}
		if (grid[i][j - 1].getIndex() == 0) { // links
			
			if (!previous.contains(grid[i][j - 1])) {
				
				if (grid[i][j-2] != destination) {
					
					previous.add(grid[i][j - 1]);
					if (!walk.contains(grid[i][j - 1])) {
						PathFinding.add(grid[i][j - 1]);
					}
					findOne(grid, grid[i][j - 1], destination, previous);
				} else {
					
					PathFinding.add(grid[i][j - 1]);
					left = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		}
		if (grid[i][j + 1].getIndex() == 0) { // rechts
			if (!previous.contains(grid[i][j + 1])) {
				if (grid[i][j+2] != destination) {
					
					previous.add(grid[i][j + 1]);
					if (!walk.contains(grid[i][j+1])) {
						{PathFinding.add(grid[i][j+1]);}
					}
					findOne(grid, grid[i][j + 1], destination, previous);
				} else {
					PathFinding.add(grid[i][j + 1]);
					right = true;
					return true;
				}
			}
		}

		if (right || left || up || down) {
			return true;
		} else {
			if (!PathFinding.isEmpty()) {
			PathFinding.remove(PathFinding.size() - 1);
			}
			return false;
		}
	}
	
	public void move() {

		if (!PathFinding.isEmpty()) {
			
				System.out.println("-----------------------------------------");
				System.out.println("PathFinding2: "+PathFinding);
				if (PathFinding.get(0).x +10 < x && PathFinding.get(0).y + 10 == y) {
					setMovementX(-2);
					setMovementY(0);
					System.out.println("nach links");
				}
				else if (PathFinding.get(0).x +10 > x && PathFinding.get(0).y + 10 == y) {
					setMovementX(2);
					setMovementY(0);
					System.out.println("nach rechts");
				}
				
				else if (PathFinding.get(0).y + 10 < y && PathFinding.get(0).x + 10 == x) {
					setMovementY(-2);
					setMovementX(0);
					System.out.println("nach oben");
				}
				else if (PathFinding.get(0).y + 10 > y && PathFinding.get(0).x + 10 == x) {
					setMovementY(2);
					setMovementX(0);
					System.out.println("nach unten");
				}
				
		/*	
			else if (x <= PathFinding.get(0).x + 10 && y == PathFinding.get(0).y + 10) {
				setMovementX(2);
				System.out.println("nach rechts");
			}
			
			else if (x == PathFinding.get(0).x + 10 && y <= PathFinding.get(0).y +10) {
				setMovementY(2);
				System.out.println("nach unten");
			}
			
			else if (x == PathFinding.get(0).x + 10 && y >= PathFinding.get(0).y + 10) {
				setMovementY(-2);
				System.out.println("nach oben");
			}*/
		}
		else {
			setMovementX(0);
			setMovementY(0);
		}
		
		
		
	}
	
	
}

package core;

import java.util.Timer;

public class AI_Ivan extends Player {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Collision coll;
	int ipos;
	int jpos;
	double[] pos;

	Explosion explo;
	Timer timer;
	int prevMovement = 0;
	boolean setDirection = false;

	public AI_Ivan(int x, int y, int width, int height) {
		super(x, y, width, height);
		coll = new Collision();
		pos = new double[4];
		explo = new Explosion();
		timer = new Timer();

	}

	public void checkDirections(Tile[][] grid, Player p) {

		double ipos = this.x;
		double jpos = this.y;
		double rangel = 50;
		double ranger = 50;
		double rangeu = 50;
		double ranged = 50;

		if (!bombOn) {
			/*
			 * left = grid[ipos][jpos-1]; right = grid[ipos][jpos+1]; up =
			 * grid[ipos-1][jpos]; down = grid[ipos+1][jpos];
			 */
			double apos = p.x;
			double bpos = p.y;

			rangel = Math.sqrt(Math.pow(apos - (ipos - getSpeed()), 2) + Math.pow(bpos - jpos, 2));

			pos[0] = rangel;

			ranger = Math.sqrt(Math.pow(apos - (ipos + getSpeed()), 2) + Math.pow(bpos - jpos, 2));

			pos[1] = ranger;

			rangeu = Math.sqrt(Math.pow(apos - ipos, 2) + Math.pow(bpos - (jpos - getSpeed()), 2));

			pos[2] = rangeu;

			ranged = Math.sqrt(Math.pow(apos - ipos, 2) + Math.pow(bpos - (jpos + getSpeed()), 2));

			pos[3] = ranged;

		}

		if (coll.checkColl(this, grid, (byte) 1)) {
			if (direction.equals("L") || direction.equals("R")) {
				plantBomb(grid, coll);
				explo.setDeadlyTiles(grid, this, timer);
				setMovementX(0);
				setMovementY(0);

			}

			else if (direction.equals("U") || direction.equals("D")) {
				plantBomb(grid, coll);
				explo.setDeadlyTiles(grid, this, timer);
				setMovementX(0);
				setMovementY(0);
			}

		}

		else if (coll.checkColl(this, grid, (byte) 2)) {

			if (direction.equals("L") || direction.equals("R")) {
				if (!setDirection) {
					if (rangeu < ranged) {
						setMovementY(-2);

						prevMovement = getMovementY();
						setDirection = true;

					} else {
						setDirection = true;
						setMovementY(2);
						prevMovement = getMovementY();

					}

				}
			} else if (setDirection) {
				setMovementY(prevMovement);
			}

			else if (direction.equals("U") || direction.equals("D")) {
				if (!setDirection) {
					if (rangel < ranger) {
						setMovementX(-2);
						prevMovement = getMovementX();
						setDirection = true;

					} else {
						setMovementX(2);
						prevMovement = getMovementX();
						setDirection = true;

					}
				} else if (setDirection) {
					setMovementX(prevMovement);
				}
			}

		}

		else {
			setDirection = false;
			double minimum = minimum(pos);
			minimumAction(minimum, pos, grid);

			if (pos[0] == 1 && pos[1] == 1 && pos[2] == 1 && pos[3] == 1) {
				setMovementX(0);
				setMovementY(0);
			}
		}
	}

	public void minimumAction(double min, double[] pos, Tile[][] grid) {
		if (min == 0) {

			setMovementY(0);
			setMovementX(-2);

		} else if (min == 1) {

			setMovementY(0);
			setMovementX(2);

		} else if (min == 2) {

			setMovementX(0);
			setMovementY(-2);

		} else if (min == 3) {

			setMovementX(0);
			setMovementY(2);
		}
	}

	public double minimum(double[] pos) {
		double min = 10000000;
		int position = 0;
		for (int i = 0; i < pos.length; i++) {
			if (pos[i] <= min) {
				min = pos[i];
				position = i;
			}
		}

		return position;
	}

}

package core;

import java.util.ArrayList;
import java.util.List;

public class AI {

    Player aiPlayer;
    boolean playerAbove, playerBelow, playerLeft, playerRight;

    public AI() {
        aiPlayer = new Player(710, 160, 25, 25);
    }


    public void checkSurroundings(Tile[][] grid, ArrayList<Player> players) {

        Tile left;
        Tile right;
        Tile above;
        Tile below;

        Tile enemy1;
        Tile enemy2;
        Tile enemy3;

        int ipos = -1;
        int jpos = -1;

        int enemy1iPos = -1;
        int enemy1jPos = -1;
        int enemy2iPos = -1;
        int enemy2jPos = -1;
        int enemy3iPos = -1;
        int enemy3jPos = -1;

        int h = 0;


        for (Player p : players) {

            h++;

            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 15; j++) {

                    if (p.intersects(grid[i][j])) {

                        //for each player the different locations are saved (h = 1,2 or 3)

                        if(h == 1) {
                            enemy1 = grid[i][j];
                            enemy1iPos = i;
                            enemy1jPos = j;

                        }else if (h==2){
                            enemy2 = grid[i][j];
                            enemy2iPos = i;
                            enemy2jPos = j;

                        }else {
                            enemy3 = grid[i][j];
                            enemy3iPos = i;
                            enemy3jPos = j;
                        }

                    }

                    if (aiPlayer.intersects(grid[i][j])) {

                        //the ai reads all adjacent tiles and stores their index

                        if(j>0) {

                            left = grid[i][j - 1];

                        }else left = null;

                        if(j<15) {

                            right = grid[i][j + 1];

                        }else right = null;

                        if(i>0) {

                            above = grid[i - 1][j];

                        }else above = null;

                        if(i<11) {

                            below = grid[i + 1][j];

                        }else below = null;

                    }

                }
            }
        }
    }

    public Player getAiPlayer() {
        return aiPlayer;
    }

    public void moveLikeADonkey(){
        aiPlayer.setMovementX(-2);
    }



}

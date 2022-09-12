import java.util.ArrayList;

public class Player {

    private String name;
    private String[][] grid; // grid of your own ships
    private String[][] shots; // grid of where you've shot (opponent's ships)
    // "—" = water, "O" = misses, "X" = hits, "*" = ships
    private ArrayList<Ship> ships;

    public Player(String name) {
        this.name = name;
        grid = new String[10][10];
        ships = new ArrayList<Ship>();

        // create ships
        String[] shipNames = {"aircraft carrier", "battleship", "cruiser", "submarine", "destroyer"};
        int[] shipLengths = {5, 4, 3, 3, 2};
        for (int i=0; i<5; i++) {
            Ship s = new Ship(shipNames[i], shipLengths[i]);
            ships.add(s);
        }

        // create grid - your ships
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++) {
                grid[i][j] = "—"; // "—" = water
            }
        }

         // create shots – empty grid that tracks your shots
        for (int i=0; i < shots.length; i++) {
            for (int j=0; j < shots[i].length; j++) {
                shots[i][j] = "—"; // "—" = water
            }
        }

    }

    public void shoot(int x, int y, Player opponent) {
        // check coordinate based on the symbol in the opponent's grid
        String[][] oppGrid = opponent.getGrid();
        if (oppGrid[x][y].equals("*")) {
            shots[x][y] = "X";
        }
        else if (oppGrid[x][y].equals("—")) {
            shots[x][y] = "O";
        }
    }

    public void updateShips(int x, int y) { // update coordinates in ships
        // (x, y) is the coordinate that the opponent shot
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String getName(){
        return name;
    }

    public String[][] getGrid(){
        return grid;
    }

    public String[][] getShots(){
        return shots;
    }
}

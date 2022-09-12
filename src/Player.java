import java.util.ArrayList;

public class Player {

    private String name;
    private String[][] grid; // grid of your own ships
    private String[][] shots; // grid of where you've shot (opponent's ships)
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
        // "—" = water, "O" = misses, "X" = hits, "*" = ships
        for (int i=0; i < grid.length; i++) {
            for (int j=0; j < grid[i].length; j++) {
                grid[i][j] = "—"; // "—" = water
            }
        }

        //create shots - where you have shot
        for (int i=0; i < shots.length; i++) {
            for (int j=0; j < shots[i].length; j++) {
                if 
            }
        }
    }

    public void shoot(int x, int y, Player player) {
        // check coordinate based on the symbol in the opponent's grid
    }

    public void updateShips() {

    }


}

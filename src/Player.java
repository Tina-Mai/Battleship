import java.util.ArrayList;

public class Player {

    private String name;
    private String[][] grid; // grid of your own ships
    // "—" = water, "*" = ships
    private String[][] shots; // grid of where you've shot (opponent's ships)
    // "—" = water, "O" = misses, "X" = hits
    private ArrayList<Ship> ships;

    public Player(String name) {
        this.name = name;
        grid = new String[10][10];
        shots = new String[10][10];
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

    public boolean shoot(int x, int y, Player opponent) {
        // check coordinate based on the symbol in the opponent's grid
        // returns whether the shot was a hit or miss
        String[][] oppGrid = opponent.getGrid();

        if (oppGrid[y][x].equals("*")) {
//            opponent.updateGrid(x, y, "—");
            shots[y][x] = "X";
            return true;
        }
        else if (oppGrid[x][y].equals("—")) {
            shots[y][x] = "O";
        }
        return false;
    }

    public void updateShips(int x, int y, Player opponent) { // update coordinates in ships and return whether the ship has sunk
        // (x, y) is the coordinate that the opponent shot
        // return true if the ship has sunk, false if there are still coordinates in its list
        ships = opponent.getShips();

        // figure out which ship was shot
        for (int i=0; i<ships.size();i++){
            Ship ship = ships.get(i);

            for (int j=0; j<ship.getCoordinates().size(); j++){
                int[] coords = ship.getCoordinates().get(j);
                if (coords[0]==x && coords[1]==y){
                    ships.get(i).removeCoordinates(x,y);
                    if (ships.get(i).getCoordinates().size() == 0) {
                        System.out.println("You sunk " + opponent.getName() + "'s " + ship.getName() + "!");
                    }
                }
            }
        }
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

    public void updateGrid(int x, int y, String e){
        grid[y][x]=e;
    }

    public String[][] getShots(){
        return shots;
    }

    public void printGrid(){
        for (int i=0; i< grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void printShots(){
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                System.out.print(shots[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

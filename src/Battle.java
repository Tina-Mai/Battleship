import java.util.Scanner;

public class Battle {

    private Scanner s;
    private Player player1;
    private Player player2;
    private boolean gameOver;

    public Battle() {
        s = new Scanner(System.in);

        System.out.print("Player 1 name: ");
        String name1 = s.nextLine();
        player1 = new Player(name1);

        System.out.print("Player 2 name: ");
        String name2 = s.nextLine();
        player2 = new Player(name2);
        System.out.println("something");
    }

    public void initializeGame(Player player) {
        System.out.println(player.getName()+": ");
        for (int i = 0; i<player.getShips().size(); i++){
            Ship currentShip = player.getShips().get(i);
            int length = currentShip.getLength();
            player.printGrid();

            // choose coordinates and orientation
            System.out.println("Where would you like to place your " + currentShip.getName() + " (length: " +
                    currentShip.getLength() + ")? Type the topmost or leftmost coordinate, then select orientation.");
            System.out.print("x: ");
            int startingX = s.nextInt() - 1;
            System.out.print("y: ");
            int startingY = s.nextInt() - 1;
            System.out.print("Orientation (horizontal or vertical): ");
            String orientation = s.nextLine();

            // validate user choice
            while (!checkCoords(startingX, startingY, orientation, player, length)) {
                System.out.println("——————————");
                System.out.println("Try a different combo of x, y, and orientation");
                System.out.print("x: ");
                startingX = s.nextInt() - 1;
                System.out.print("y: ");
                startingY = s.nextInt() - 1;
                s.nextLine();
                System.out.print("Orientation: ");
                orientation = s.nextLine();
            }

            // iterate through the length of the ship adding coordinates to the ship's coordinate list based on user
            // input and adding the ship to the player's grid
            for (int j=0; j<currentShip.getLength(); j++){
                if (orientation.equals("vertical")){
                    currentShip.addCoordinates(startingX, startingY*j);
                    // add another if statement for if horizontal
                }
            }

        }

    }

    // checks if the coordinates that the player entered are valid
    public boolean checkCoords(int x, int y, String o, Player p, int length) {
        String[][] grid = p.getGrid();
        o = o.toLowerCase();

        // first check if user inputs are actually valid numbers and strings
        if (!o.equals("horizontal") && !o.equals("vertical")) {
            System.out.println("Invalid orientation.  Please type \"horizontal\" or \"vertical\".");
            return false;
        }
        if (!(x >= 0 && x < grid[0].length)) {
            System.out.println("Invalid x.  Must be >0 and <" + grid[0].length);
            return false;
        }
        if (!(y >= 0 && y < grid.length)) {
            System.out.println("Invalid y.  Must be >0 and <" + grid[0].length);
            return false;
        }

        // then check if the combo fits on the grid (if it hits a wall)
        if ((o.equals("vertical") && y + length >= grid.length) || (o.equals("horizontal") && x + length >= grid[0].length)) {
            System.out.println("That combo doesn't fit on the grid.");
            return false;
        }

        // finally check if the combo will overlap with another ship
        if (o.equals("vertical")) {
            for (int i = y; i < length; i++) {
                if (grid[x][i].equals("*")) {
                    System.out.println("That combo overlaps with another ship.");
                    return false;
                }
            }
        }
        if (o.equals("horizontal")) {
            for (int i = x; i < length; i++) {
                if (grid[i][y].equals("*")) {
                    System.out.println("That combo overlaps with another ship.");
                    return false;
                }
            }

            return true;

        }
    }*/
    /*public void play() {
        boolean playerSwitch = true;
        Player currentPlayer;
        Player opponentPlayer;
        //when player is true, player is player 1
        //when player is false, player is player 2

        while (!Battle.isGameOver()){

            //will be initialized to player 1- will run through and then at end will switch to next player
            if (playerSwitch){
                currentPlayer = player1;
                opponentPlayer = player2;
            }
            else {
                currentPlayer = player2;
                opponentPlayer = player1;
            }

            //Show the player their ships
            currentPlayer.printGrid();

            //Show the player their shots fired
            currentPlayer.printShots();

            //ask where they want to shoot and validate
            System.out.println("What x-Coord do you want to shoot?");
            int xCoord = s.nextInt();
            while (xCoord<1 || xCoord>10){
                System.out.println("Please put a valid number 1-10.");
                xCoord = s.nextInt()-1;
            }

            System.out.println("What y-Coord do you want to shoot?");
            int yCoord = s.nextInt();
            while(yCoord < 1 || yCoord > 10) {
                System.out.println("Please put a valid number 1-10.");
                yCoord = s.nextInt()-1;
            }


            //While loop until shoot is false (use shoot method)
            boolean hit = currentPlayer.shoot(xCoord, yCoord, opponentPlayer);
            if (!hit){
                System.out.println("You missed!");
            }
            while (hit){
                //check if ship is sunk or just hit using UpdateShips
                System.out.println("Direct Hit!");
                currentPlayer.updateShips(xCoord, yCoord, opponentPlayer);

                System.out.println("——————————");

                //take new coordinates
                System.out.println("Play again!");
                System.out.println("What x-Coord do you want to shoot?");
                xCoord = s.nextInt()-1;
                while (xCoord<0 || xCoord>9){
                    System.out.println("Please put a valid number 1-10.");
                    xCoord = s.nextInt()-1;
                }
                System.out.println("What y-Coord do you want to shoot?");
                yCoord = s.nextInt()-1;
                while(yCoord < 0 || yCoord > 9) {
                    System.out.println("Please put a valid number 1-10.");
                    yCoord = s.nextInt()-1;
                }
            }

            //switch players
            playerSwitch=!playerSwitch;
        }
    }


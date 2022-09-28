import java.util.Scanner;

public class Battle {

    private Scanner s;
    private Player player1;
    private Player player2;
    private boolean gameOver;

    public Battle() {
        s = new Scanner(System.in);
        gameOver = false;

        System.out.print("Player 1 name: ");
        String name1 = s.nextLine();
        player1 = new Player(name1);

        System.out.print("Player 2 name: ");
        String name2 = s.nextLine();
        player2 = new Player(name2);

        System.out.println("——————————");

        initializeGame(player1);
        System.out.println(player1.getName() + "'s final grid:");
        player1.printGrid();

        System.out.println("——————————");

        initializeGame(player2);
        System.out.println(player2.getName() + "'s final grid:");
        player2.printGrid();

        System.out.println("——————————");

    }

    public void initializeGame(Player player) {
        // for one player, iterate through ships and ask where they would like to place it
        System.out.println(player.getName()+": ");

        for (int i = 0; i<player.getShips().size(); i++){

            Ship currentShip = player.getShips().get(i);
            int length = currentShip.getLength();
            player.printGrid();

            // choose coordinates and orientation
            System.out.println("Where would you like to place your " + currentShip.getName() + " (length: " +
                    currentShip.getLength() + ")? Type the topmost or leftmost coordinate, then select orientation.");
            System.out.print("col: ");
            int startingX = s.nextInt() - 1;
            System.out.print("row: ");
            int startingY = s.nextInt() - 1;
            s.nextLine();
            System.out.print("Orientation (horizontal or vertical): ");
            String orientation = s.nextLine();

            // validate user choice
            while (!checkCoords(startingX, startingY, orientation, player, length)) {
                System.out.println("——————————");
                System.out.println("Try a different combo of col, row, and orientation");
                System.out.print("col: ");
                startingX = s.nextInt() - 1;
                System.out.print("row: ");
                startingY = s.nextInt() - 1;
                s.nextLine();
                System.out.print("Orientation: ");
                orientation = s.nextLine();
            }

            // iterate through the length of the ship adding coordinates to the ship's coordinate list based on user
            // input and adding the ship to the player's grid
            for (int j=0; j<currentShip.getLength(); j++){
                if (orientation.equals("vertical")){
                    currentShip.addCoordinates(startingX, startingY+j);
                    player.updateGrid(startingX, startingY+j, "*");
                }
                if (orientation.equals("horizontal")){
                    currentShip.addCoordinates(startingX+j, startingY);
                    player.updateGrid(startingX+j, startingY, "*");
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
            System.out.println("Invalid col.  Must be >0 and <" + grid[0].length);
            return false;
        }
        if (!(y >= 0 && y < grid.length)) {
            System.out.println("Invalid row.  Must be >0 and <" + grid[0].length);
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
                if (grid[i][x].equals("*")) {
                    System.out.println("That combo overlaps with another ship.");
                    return false;
                }
            }
        }
        if (o.equals("horizontal")) {
            for (int i = x; i < length; i++) {
                if (grid[y][i].equals("*")) {
                    System.out.println("That combo overlaps with another ship.");
                    return false;
                }
            }
        }
        return true;
    }

    public void play() {
        boolean playerSwitch = true;
        Player currentPlayer;
        Player opponentPlayer;
        //when player is true, player is player 1
        //when player is false, player is player 2

        while (!gameOver){

            //will be initialized to player 1- will run through and then at end will switch to next player
            if (playerSwitch){
                currentPlayer = player1;
                opponentPlayer = player2;
            }
            else {
                currentPlayer = player2;
                opponentPlayer = player1;
            }

            System.out.println(currentPlayer.getName() + "'s turn!\n");

            //Show the player their shots fired
            System.out.println("\nStarting shooting!");
            currentPlayer.printShots();

            //ask where they want to shoot and validate
            System.out.print("What col do you want to shoot? ");
            int xCoord = s.nextInt()-1;
            while (xCoord<0 || xCoord>9){
                System.out.print("Please enter a valid col number 1-10: ");
                xCoord = s.nextInt()-1;
            }
            System.out.print("What row do you want to shoot? ");
            int yCoord = s.nextInt()-1;
            while (yCoord < 0 || yCoord > 9) {
                System.out.print("Please enter a valid row number 1-10: ");
                yCoord = s.nextInt()-1;
            }
            // validate that they aren't shooting in a place where they've already shot
            while (!currentPlayer.getShots()[yCoord][xCoord].equals("—")) {
                System.out.println("You've already shot there. Try another col and row.");
                System.out.print("What col do you want to shoot? ");
                xCoord = s.nextInt()-1;
                System.out.print("What row do you want to shoot? ");
                yCoord = s.nextInt()-1;
            }

            //While loop until shoot is false (use shoot method)
            boolean hit = currentPlayer.shoot(xCoord, yCoord, opponentPlayer);
            if (!hit){
                System.out.println("You missed!");
                System.out.println("——————————");
            }
            while (hit){

                // check if ship is sunk or just hit using UpdateShips
                System.out.println("Direct Hit!");
                currentPlayer.updateShips(xCoord, yCoord, opponentPlayer);

                // check is game is over after hit
                if (isGameOver()) {
                    System.out.println("Game over!");
                    gameOver = true;
                    break;
                }

                System.out.println("——————————");

                //take new coordinates
                System.out.println("Play again!");
                currentPlayer.printShots();
                System.out.print("What col do you want to shoot? ");
                xCoord = s.nextInt()-1;
                while (xCoord<0 || xCoord>9){
                    System.out.print("Please enter a valid col number 1-10: ");
                    xCoord = s.nextInt()-1;
                }
                System.out.print("What row do you want to shoot? ");
                yCoord = s.nextInt()-1;
                while (yCoord < 0 || yCoord > 9) {
                    System.out.print("Please enter a valid row number 1-10: ");
                    yCoord = s.nextInt()-1;
                }
                // validate that they aren't shooting in a place where they've already shot
                while (!currentPlayer.getShots()[yCoord][xCoord].equals("—")) {
                    System.out.println("You've already shot there. Try another col and row.");
                    System.out.print("What col do you want to shoot? ");
                    xCoord = s.nextInt()-1;
                    System.out.print("What row do you want to shoot? ");
                    yCoord = s.nextInt()-1;
                }
                hit = currentPlayer.shoot(xCoord, yCoord, opponentPlayer);

                if (!hit){
                    System.out.println("You missed!");
                    System.out.println("——————————");
                }
            }

            //switch players
            playerSwitch =! playerSwitch;

        }
    }
    public boolean isGameOver () {
        // for each player, iterate through the grid of shots they've taken; if the number of Xs (X = hit) equals
        // 17 (the number of ship coords that could be hit), then they've hit all the ships on the grid and won

        int p1Counter = 0; // counts how many successful shots Player 1 has
        for (int i=0; i<player1.getShots().length; i++) {
            for (int j=0; j<player1.getShots()[0].length; j++) {
                if (player1.getShots()[i][j].equals("X"))
                    p1Counter++;
            }
        }
        if (p1Counter == 17) { // there are 17 coordinates with ships
            System.out.println("——————————");
            System.out.println(player1.getName() + " won");
            return true;
        }

        int p2Counter = 0; // counts how many successful shots Player 2 has
        for (int i=0; i<player2.getShots().length; i++) {
            for (int j=0; j<player2.getShots()[0].length; j++) {
                if (player2.getShots()[i][j].equals("X"))
                    p2Counter++;
            }
        }
        if (p2Counter == 17) {
            System.out.println("——————————");
            System.out.println(player2.getName() + " won");
            return true;
        }

        return false;
    }
}


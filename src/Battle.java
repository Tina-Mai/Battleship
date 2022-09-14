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

            /*
            // validate user choice
            while (checkCoords(startingX, startingY, orientation, player, length).equals("invalid orientation")) {
                System.out.print("Invalid orientation. Please type \"horizontal\" or \"vertical\": ");
                orientation = s.nextLine();
            }
            while (checkCoords(startingX, startingY, orientation, player, length).equals("invalid x")) {
                System.out.print("Invalid x. Pick a new x: ");
                startingX = s.nextInt() - 1;
            }
            while (checkCoords(startingX, startingY, orientation, player, length).equals("invalid y")) {
                System.out.print("Invalid y. Pick a new y: ");
                startingY = s.nextInt() - 1;
            }
            while (checkCoords(startingX, startingY, orientation, player, length).equals("already a ship")) {
                System.out.println("There's already a ship there.");
                System.out.print("Pick a new x: ");
                startingX = s.nextInt()-1;
                System.out.print("Pick a new y: ");
                startingY = s.nextInt()-1;
            }

             */

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

    public void play() {
        boolean playerSwitch = true;
        Player currentPlayer;
        Player opponentPlayer;
        //when player is true, player is player 1
        //when player is false, player is player 2

        //while (!Battle.isGameOver()){
            while (true){
            //will be initialized to player 1- will run through and then at end will switch to next player
            if (playerSwitch){
                currentPlayer = player1;
                opponentPlayer=player2;
            }
            else {
                currentPlayer = player2;
                opponentPlayer=player1;
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
                xCoord = s.nextInt();
            }

            System.out.println("What y-Coord do you want to shoot?");
            int yCoord = s.nextInt();
            while(yCoord < 1 || yCoord > 10) {
                System.out.println("Please put a valid number 1-10.");
                yCoord = s.nextInt();
            }


            //While loop until shoot is false (use shoot method)
            while (currentPlayer.shoot(xCoord-1, yCoord-1, opponentPlayer)){
                //check if ship is sunk or just hit using UpdateShips
                System.out.println("Direct Hit!");
                currentPlayer.updateShips(xCoord-1, yCoord-1);

                //take new coordinates
                System.out.println("Play again!");
                System.out.println("What x-Coord do you want to shoot?");
                xCoord = s.nextInt();
                while (xCoord<1 || xCoord>10){
                    System.out.println("Please put a valid number 1-10.");
                    xCoord = s.nextInt();
                }
                System.out.println("What y-Coord do you want to shoot?");
                yCoord = s.nextInt();
                while(yCoord < 1 || yCoord > 10) {
                    System.out.println("Please put a valid number 1-10.");
                    yCoord = s.nextInt();
                }
            }

            //switch players
            playerSwitch=!playerSwitch;
        }
    }
}

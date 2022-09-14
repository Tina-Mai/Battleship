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
            System.out.println(player.getGrid());
            System.out.println("Where would you like to place  " + currentShip.getName() + "? (type the topmost or " +
                    "leftmost coordinate");
            System.out.println("x: ");
            int startingX = s.nextInt();
            System.out.println("y: ");
            int startingY = s.nextInt();
            System.out.println("orientation: (horizontal or vertical) ");
            String orientation = s.nextLine();
            for (int j=0; j<currentShip.getLength(); j++){
                if (orientation.equals("vertical")){
                    currentShip.addCoordinates(startingX, startingY*j);
                    // add another if statement for if horizontal
                }
            }

        }

    }

    public void play() {
        while (!gameOver){

        }
    }
}

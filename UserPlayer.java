import java.util.Scanner;

public class UserPlayer extends Player{
    private Scanner input;
    private String name;

    /**
     * constructor for user player.
     * @param scanner the scanner used to get input from the user
     * @param name the name of the user player
     */
    public UserPlayer(Scanner scanner, String name) {
        this.name = name;
        this.input = new Scanner(System.in);
    }

    /**
     * This method is used to get the name of the user player.
     * @return the name of the user player
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to set the name of the user player.
     * @param Name the name of the user player
     */
    public void setName(String Name) {
        this.name = Name;
    }

    @Override
    /**
     * Prints out the users possible moves and changes the game state accordingly.
     * @return the game state after the user has made their move
     */
    public MiniCheckers chooseMove(MiniCheckers game)
    {
        System.out.println(game);
        
        for (int i = 0; i < game.possibleMoves(this).size(); i++) {
            System.out.println(i + ":\n" + game.possibleMoves(this).get(i));
        }
        System.out.println("Enter your move: ");
        String move = input.nextLine();
        int moveInt = Integer.parseInt(move);
        MiniCheckers temp = game.possibleMoves(this).get(moveInt);
        char[][] otherTemp = temp.getBoard();
        for(int i = 0; i < game.getBoard().length; i++){
            if (game.getBoard()[4][i] == 'r')
            {
                otherTemp[4][i] = 'R';
            }
            if (game.getBoard()[0][i] == 'b')
            {
                otherTemp[4][i] = 'B';
            }
        game.setBoard(otherTemp);
        }
        return game;
    }

    @Override
    public String toString()
    {
        return name;
    }
}

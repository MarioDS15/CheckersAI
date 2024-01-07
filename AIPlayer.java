public class AIPlayer extends Player {
    
    private Player opponent;
    private String name;


    // Make getters and setters

    /**
     * constructor for AI player.
     * @param name the name of the AI player
     * @param opponent the opponent of the AI player
     */
    public AIPlayer(String name, Player opponent)
    {
        this.opponent = opponent;
        this.name = name;
    }

    /**
     * This method is used to get the opponent of the AI player.
     * @return the opponent of the AI player
     */
    public Player getOpponent() {
        return opponent;
    }

    /**
     * This method is used to set the opponent of the AI player.
     * @param Opponent the opponent of the AI player
     */
    public void setOpponent(Player Opponent) {
        this.opponent = Opponent;
    }

    /**
     * This method is used to get the name of the AI player.
     * @return the name of the AI player
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to set the name of the AI player.
     * @param Name the name of the AI player
     */
    public void setName(String Name) {
        this.name = Name;
    }

    @Override
    /**
     * This method is used to return the name of the AI player.
     * @return the name of the AI player
     */
    public String toString()
    {
        return name + " (AI)";
    }

    @Override
    /**
     * This method is used to choose the best move for the AI player.
     * @param game the game state that needs to be evaluated
     * @return the best move for the AI player
     */
    public MiniCheckers chooseMove(MiniCheckers game) {
        double bestValue = Double.NEGATIVE_INFINITY;
        MiniCheckers bestMove = null;
        int depth = 10;  // Depth limit for the recursion as mentioned in project requirements
    
        // Loop through all possible moves for the AI player
        for (MiniCheckers possibleMove : game.possibleMoves(this)) {
            // Evaluate the move using the minValue method
            double value = minValue(possibleMove, depth - 1);
    
            // If the value of the current move is better than the best known value, update bestMove and bestValue
            if (value > bestValue) {
                bestValue = value;
                bestMove = possibleMove;
            }
        }
    
        // If there were no possible moves (bestMove is null), return the original game state
        if (bestMove == null) {
            return game;
        }
    
        // Return the move with the highest value
        return bestMove;
    }


    /**
     * This method is used to find the min value for the AI player. 
     * @param game the game state that needs to be evaluated
     * @param depth the depth limit for the recursion
     * @return the min value for the AI player
     */
    public double minValue(MiniCheckers game, int depth)
    {
        if (game.checkWin(this)) {
            return 10.0; // AI has won
        } else if (game.checkWin(this.opponent)) {
            return -10.0; // Opponent has won
        } else if (depth <= 0) {
            return boardValueCustom(game); // Evaluate the board and return its value
        }
        if(game.possibleMoves(opponent).size() == 0)
        {
            return boardValueCustom(game);
        }
        double v = 1.0;
        double minValue = 1000.0;
        for (MiniCheckers s : game.possibleMoves(opponent))
        {
            v = Math.min(v, maxValue(s, depth - 1));
            if(v < minValue){ minValue = v;}
        }
        return minValue;
    }


    /**
     * This method is used to find the max value for the AI player.
     * @param game the game state that needs to be evaluated
     * @param depth the depth limit for the recursion
     * @return the max value for the AI player
     */
    public double maxValue(MiniCheckers game, int depth)
    {
        if (game.checkWin(this)) {
            return 10.0; // AI has won
        } else if (game.checkWin(this.opponent)) {
            return -10.0; // Opponent has won
        } else if (depth <= 0) {
            return boardValueCustom(game); // Evaluate the board and return its value
        }
        if(game.possibleMoves(this).size() == 0)
        {
            return boardValueCustom(game);
        }
        double v = -100.0;
        double maxValue = -1000.0;
        for (MiniCheckers s : game.possibleMoves(this))
        {
            v = Math.max(v, minValue(s, depth - 1));
            if(v > maxValue){ maxValue = v;}
        }
        return maxValue;
    }

    /**
     * This helper method is used to evaluate the board state for the AI player.
     * @param board the board state that needs to be evaluated
     * @return the value of the board state
     */
    public double boardValueCustom(MiniCheckers board)
    {
        double total = 0.0;
        if(board.checkWin(this))
        {
            return 1.0;
        }
        else if(board.checkLose(this)) 
        {
            return -1.0;
        }
        else
        {
            
            if(board.getRed() == this){
                for(int i = 0; i < board.getBoard().length; i++)
                {
                        for(int j = 0; j < board.getBoard()[i].length; j++)
                        {
                            if(board.getBoard()[i][j] == 'r')
                            {
                                total = total + 1.0;
                            }
                            else if(board.getBoard()[i][j] == 'R')
                            {
                                total = total + 3.0;
                            }
                            else if(board.getBoard()[i][j] == 'b')
                            {
                               total = total - 1.0;
                            }
                            else if(board.getBoard()[i][j] == 'B')
                            {
                                total = total - 3.0;
                            }
                        }
                }
            }
            else
            {
                total = 0 - total;
            }
        }
        return total;
    }

    @Override
    public double boardValue(MiniCheckers board) {
        return maxValue(board, 10);
    }
}

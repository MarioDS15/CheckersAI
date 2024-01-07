public abstract class Player {
    
    public abstract MiniCheckers chooseMove(MiniCheckers board);

    public double boardValue(MiniCheckers board)
    {
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
            return 0.0;
        }
    }
}

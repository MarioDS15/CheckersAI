import java.util.ArrayList;

public class MiniCheckers {
    private char[][] board;
    private Player red;
    private Player black;

    /**
     * constructor for MiniCheckers (5x5 board))
     */
    public MiniCheckers(Player red, Player black) {
        this.red = red;
        this.black = black;
        board = new char[5][5]; // Changed from 8 by 8 
        board[0][0] = board[0][2] = board[0][4] = 'r';
        board[0][1] = board[0][3] = '.';

        board[1][0] = board[1][2] = board[1][4] = '.';
        board[1][1] = board[1][3] = '_';

        board[2][1] = board[2][3] = '.';
        board[2][0] = board[2][2] = board[2][4] = '_';

        board[3][0] = board[3][2] = board[3][4] = '.';
        board[3][1] = board[3][3] = '_';

        board[4][1] = board[4][3] = '.';
        board[4][0] = board[4][2] = board[4][4] = 'b';
    }

    /**
     * Gets the board.
     * @return the board
     */
    public char[][] getBoard() {
        return this.board;
    }

    /**
     * Sets the board.
     */
    public void setBoard(char[][] board) {
        this.board = board;
    }

    /**
     * Gets the red player.
     * @return the red player
     */
    public Player getRed() {
        return this.red;
    }

    /**
     * Sets the red player.
     */
    public void setRed(Player red) {
        this.red = red;
    }

    /**
     * Gets the black player.
     * @return the black player
     */
    public Player getBlack() {
        return this.black;
    }

    /**
     * Sets the black player.
     */
    public void setBlack(Player black) {
        this.black = black;
    }

    /**
     * Counts the amount of pieces on the board that match the color and rank.
     * @param color the color of the pieces to count
     * @return the amount of pieces on the board that match the color
     */
    public int countChecker(char color) { // Should only check for exact color (not kings)
        int count = 0;
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[row].length; col++) {
                if(board[row][col] == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if the player has won.
     * @param player the player to check
     * @return true if the player has won, false otherwise
     */
    public boolean checkWin(Player player) {
        //System.out.println(countChecker('r'));
        if(countChecker('r') + countChecker('R') == 0 && player == this.getBlack()) {
            //System.out.println(countChecker('r'));
            //System.out.println("Black wins!");
            return true;
        } else if(countChecker('b') + countChecker('B') == 0 && player == this.getRed()) {
            //System.out.println("Red wins!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the player has lost.
     * @param player the player to check
     * @return true if the player has lost, false otherwise
     */
    public boolean checkLose(Player player) {
        if(countChecker('r') + countChecker('R') == 0 && player == this.getRed()) {
            System.out.println("Black wins!");
            return true;
        } else if(countChecker('b') + countChecker('B') == 0 && player == this.getBlack()) {
            System.out.println("Red wins!");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of the board.
     * @return a string representation of the board
     */
    public String toString() {
        String res = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                res += board[i][j];
            }
            res += "\n";
        }
        return res;
    }

    private MiniCheckers makeClone() {
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers newMiniCheckers = new MiniCheckers(this.red, this.black);
        char[][] newBoard = newMiniCheckers.getBoard();
        char[][] curBoard = this.getBoard();
        for (int i = 0; i < curBoard.length; i++) {
            for (int j = 0; j < curBoard[i].length; j++) {
                newBoard[i][j] = curBoard[i][j];
            }
        }
        newMiniCheckers.setBoard(newBoard);
        return newMiniCheckers;
    }

    private MiniCheckers movePiece(int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers move = this.makeClone();
        char[][] newBoard = move.getBoard();
        char tmpPiece = newBoard[startRow][startCol];
        newBoard[startRow][startCol] = '_';
        newBoard[endRow][endCol] = tmpPiece;
        if((tmpPiece=='r' && endRow==newBoard.length-1)||(tmpPiece=='b'&&endRow==0)){
            newBoard[endRow][endCol] = Character.toUpperCase(newBoard[endRow][endCol]);
        }
        return move;
    }

    private static void removePiece(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        board[i][j] = '_';
    }

    private static boolean validIndex(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        if(i<0 || j<0 || i>=board.length || j>=board[0].length) return false;
        return true;
    }


    /**
     * The following methods are used to check if a piece can move to a certain spot.
     * @param board the board state
     * @param startRow the row of the piece to move
     * @param startCol the column of the piece to move
     * @param endRow the row to move the piece to
     * @param endCol the column to move the piece to
     * @return true if the piece can move to the spot, false otherwise
     */
    private static boolean redCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){ // Check this method again
        boolean kinged = false;

        // Check if piece is even there in the first place 
        if(board[startRow][startCol] == 'R') kinged = true;
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(board[endRow][endCol] != '_') return false; // Check if the spot is even available
        if(kinged){
            if(Math.abs(startRow-endRow)!=1 || Math.abs(startCol-endCol)!=1) return false;
            return true;
        } else {
            if(startRow-endRow!= - 1) return false;
            if(Math.abs(startCol-endCol)!=1) return false;
            return true;
        }
    }
    private static boolean redCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'r'){
            if(endRow != startRow+2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow+1][startCol+1] == 'b' || board[startRow+1][startCol+1] == 'B')) ||
                (endCol == startCol-2 && (board[startRow+1][startCol-1] == 'b' || board[startRow+1][startCol-1] == 'B'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'R'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='b' || board[startRow+1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='b' || board[startRow-1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='b' || board[startRow+1][startCol-1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='b' || board[startRow-1][startCol-1]=='B') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The following methods are used to check if a black piece can move to a certain spot.
     * @param board the board state
     * @param startRow the row of the piece to move
     * @param startCol the column of the piece to move
     * @param endRow the row to move the piece to
     * @param endCol the column to move the piece to
     * @return true if the piece can move to the spot, false otherwise
     */
    private static boolean blackCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        boolean kinged = false;
        if(board[startRow][startCol] == 'B') kinged = true;
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(board[endRow][endCol] != '_') return false; // Check if the spot is even available

        if(kinged){
            if(Math.abs(startRow-endRow)!=1 || Math.abs(startCol-endCol)!=1) return false;
            return true;
        } else {
            if(startRow-endRow!=1) return false;
            if(Math.abs(startCol-endCol)!=1) return false;
            return true;
        }
    }

    private static boolean blackCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'b'){
            if(endRow != startRow-2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow-1][startCol+1] == 'r' || board[startRow-1][startCol+1] == 'R')) ||
                (endCol == startCol-2 && (board[startRow-1][startCol-1] == 'r' || board[startRow-1][startCol-1] == 'R'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'B'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='r' || board[startRow+1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='r' || board[startRow-1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='r' || board[startRow+1][startCol-1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='r' || board[startRow-1][startCol-1]=='R') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<MiniCheckers> possibleMoves(Player player) {
        /* provided code. You may call this method, but you should NOT modify it */
        char[][] curBoard = this.getBoard();
        ArrayList<MiniCheckers> rv = new ArrayList<MiniCheckers>();
        if(player == this.red){
            boolean couldJump = false;
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='r' || board[i][j] =='R'){
                        if(redCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='r' || board[i][j]=='R'){                            
                            if(redCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else if(player==this.black){
            boolean couldJump = false;
            //check for jumps first
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='b' || board[i][j] =='B'){
                        if(blackCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='b' || board[i][j]=='B'){
                            if(blackCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Unrecognized player?!");
        }
        return rv;
    }
}
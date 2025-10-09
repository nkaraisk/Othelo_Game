package ce326.hw2;

import java.util.LinkedList;

public class Board {

    private Pawn[][] board;
    private int numOfPawns;
    private char Player;
    private char AI;


    //Getters
    public Pawn[][] getBoard() {
        return board;
    }

    public int getNumOfPawns() {
        return numOfPawns;
    }

    public char getAI() {
        return AI;
    }

    public char getPlayer() {
        return Player;
    }

    // Setters
    public void setBoard(Pawn[][] board) {
        this.board = board;
    }

    public void setNumOfPawns(int numOfPawns) {
        this.numOfPawns = numOfPawns;
    }

    public void setAI(char AI) {
        this.AI = AI;
    }

    public void setPlayer(char player) {
        this.Player = player;
    }

    // Constructor of the main board.
    public Board() {
        int[][] arrayValues = {
                {500, -20, 10, 5, 5, 10, -20, 500},  // Row 1
                {-20, -50, -2, -2, -2, -2, -50, -20},  // Row 2
                {10, -2, 1, 1, 1, 1, -2, 10},  // Row 3
                {5, -2, 1, 0, 0, 1, -2, 5},  // Row 4
                {5, -2, 1, 0, 0, 1, -2, 5},  // Row 5
                {10, -2, 1, 1, 1, 1, -2, 10},  // Row 6
                {-20, -50, -2, -2, -2, -2, -50, -20},  // Row 7
                {500, -20, 10, 5, 5, 10, -20, 500}   // Row 8
        };

        board = new Pawn[8][8];

        initializeData(arrayValues);
        setBoard(board);
    }

    // Constructor that copies the mainBoard to create the secondary boards of minMax.
    public Board(Board gameBoard) {
        Pawn[][] newBoard = new Pawn[8][8];//We create the new board
        Pawn[][] mainBoard = gameBoard.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (mainBoard[row][col] instanceof NoPawn) {// and then, we check if the object we will copy is a NoPawn
                    newBoard[row][col] = new NoPawn(row, col, mainBoard[row][col].getValue(), mainBoard[row][col].getPosition());
                }
                else if (mainBoard[row][col] instanceof PawnBlack) {// or a PawnBlack
                    newBoard[row][col] = new PawnBlack(row, col, mainBoard[row][col].getValue(), mainBoard[row][col].getPosition());
                }
                else if (mainBoard[row][col] instanceof PawnWhite) {// or a PawnWhite.
                    newBoard[row][col] = new PawnWhite(row, col, mainBoard[row][col].getValue(), mainBoard[row][col].getPosition());
                }
                else if (mainBoard[row][col] instanceof AvailableMove) {
                    newBoard[row][col] = new AvailableMove(row, col, mainBoard[row][col].getValue(), mainBoard[row][col].getPosition());
                }
            }
        }
        setBoard(newBoard);
        setNumOfPawns(gameBoard.getNumOfPawns());
        setAI(gameBoard.getAI());
        setPlayer(gameBoard.getPlayer());
    }
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    //Helper methods for the constructors

    // This method initializes the main board of the game.
    private void initializeData(int[][] arrayValues) {
        String position;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // At first, we create the position of the pawn as a string
                position = (char) (col + 'a') + String.valueOf(row+1);// Number
                board[row][col] = new NoPawn(row, col, arrayValues[row][col], position);// then we create the objects of the board and set their position values.
            }
        }
        setInitialCenterPawns(board);// At the end, we place at the center of the board the 4 initial pawns. 2 for each player.
        setNumOfPawns(4);
    }

    // This method places the initial for pawns at the center of the board
    private void setInitialCenterPawns(Pawn[][] mainBoard) {
        mainBoard[3][3] = new PawnWhite(3, 3, mainBoard[3][3].getValue(), mainBoard[3][3].getPosition());// White pawn.

        mainBoard[3][4] = new PawnBlack(3, 4, mainBoard[3][4].getValue(), mainBoard[3][4].getPosition());// Black pawn.

        mainBoard[4][3] = new PawnBlack(4, 3, mainBoard[4][3].getValue(), mainBoard[4][3].getPosition());// White pawn.

        mainBoard[4][4] = new PawnWhite(4, 4, mainBoard[4][4].getValue(), mainBoard[4][4].getPosition());// Black pawn.
    }

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *//* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    //Methods to find all the available moves of the array.

    // This method checks if the move that is given is VALID to play.
    public boolean isMoveValid(int row, int col, char currentPlayer, char opponent) {
        int xOffset, yOffset;
        boolean opponentFound;
        Pawn pawnInCheck;

        // Directions we check to determine if the move is valid (up, right, down, left and the diagonals).
        int[] directions = {-1, 0, 1};

        // We check all the possible directions.
        for (int xDirection = 0; xDirection < directions.length; xDirection++) {
            for (int yDirection = 0; yDirection < directions.length; yDirection++) {

                if((directions[xDirection] != 0) || (directions[yDirection] != 0)) {
                    xOffset = row + directions[xDirection];// The first xOffset we check.
                    yOffset = col + directions[yDirection];// The first yOffset we check.
                    opponentFound = false;

                    // We check until we go out of bounds.
                    while((xOffset >= 0) && (xOffset < 8) && (yOffset >= 0) && (yOffset < 8)) {

                        pawnInCheck = board[xOffset][yOffset]; // We get the pawn of the current position

                        if((pawnInCheck instanceof NoPawn) || (pawnInCheck instanceof AvailableMove)) {// If this position is empty or already marked as available
                            break;// we break out of the loop.
                        }
                        else if (pawnInCheck.getSymbol() == opponent) {// If we find an opponent
                            opponentFound = true;// we signal it
                        }
                        else if (pawnInCheck.getSymbol() == currentPlayer) {// If we find a pawn that belongs to the player
                            if (opponentFound) {// and the last pawn was of an opponents, this position is valid
                                return true;// so we return true;
                            }
                            else{// if the last pawn was not of the opponent's
                                break;// we break out of the loop.
                            }
                        }
                        else{// If an unidentified piece is found
                            break;// we break out of the loop.
                        }

                        // Move in the current direction
                        xOffset += directions[xDirection];
                        yOffset += directions[yDirection];
                    }
                }
            }
        }

        return false;
    }


    // This method removes the objects that represent the available moves
    public void clearAvailableMoves() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] instanceof AvailableMove) {// If we find an object of type AvailableMove
                    board[row][col] = new NoPawn(row, col, board[row][col].getValue(), board[row][col].getPosition());// we replace it with a NoPawn type of object.
                }
            }
        }
    }


    // This method marks all the available moves of the player on the board.
    public LinkedList<Pawn> markAvailableMoves(char currentPlayer) {
        char opponent;
        boolean moveValid;
        LinkedList <Pawn> availableMoves = new LinkedList<>();

        clearAvailableMoves();// We erase the marked positions from the last call because we do not know if they are valid.

        if (currentPlayer == 'X') {// We set the opponent's symbol.
            opponent = 'O';
        }
        else {
            opponent = 'X';
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] instanceof NoPawn) {// If there is an empty position
                    moveValid = isMoveValid(row, col, currentPlayer, opponent);// we check if it is valid for the currentPlayer to use.

                    if(moveValid) {// If it valid
                        board[row][col] = new AvailableMove(row, col, board[row][col].getValue(), board[row][col].getPosition());// we mark it as such.
                        availableMoves.add(board[row][col]);
                    }
                }
            }
        }
        return availableMoves;
    }

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    //Methods to check and make a move.

    // Creates a Pawn object (Black or White) based on the player symbol
    private Pawn createPawn(int row, int col, char symbol) {
        String position = board[row][col].getPosition();
        int value = board[row][col].getValue();

        if (symbol == 'O') {
            return new PawnBlack(row, col, value, position);
        }
        else {
            return new PawnWhite(row, col, value, position);
        }
    }

    // This method checks the given move, and if it is valid, it makes it.
    public boolean makeMove(Move move) {
        char opponent;
        int[][] pawnsToFlip;
        int xOffset, yOffset, numOfFlips;
        boolean moveValid = false;
        boolean opponentFound;
        Pawn pawnInCheck;

        if ( !((board[move.getLine()][move.getColumn()] instanceof NoPawn) || (board[move.getLine()][move.getColumn()] instanceof AvailableMove))) {
            return false;
        }
        else{
            if (move.getCurrentPlayer() == 'X'){// We set the opponent's symbol.
                opponent = 'O';
            }
            else{
                opponent = 'X';
            }

            // Directions we check to determine if the move is valid (up, right, down, left and the diagonals).
            int[] directions = {-1, 0, 1};

            for (int xDirection : directions) {
                for (int yDirection : directions) {
                    if ((xDirection != 0) || (yDirection != 0)) {
                        xOffset = move.getLine() + xDirection;// The first xOffset we check.
                        yOffset = move.getColumn() + yDirection;// The first yOffset we check.
                        opponentFound = false;

                        pawnsToFlip = new int[8][2];
                        numOfFlips = 0;

                        // We check until we go out of bounds.
                        while((xOffset >= 0) && (xOffset < 8) && (yOffset >= 0) && (yOffset < 8)) {

                            pawnInCheck = board[xOffset][yOffset]; // We get the pawn of the current position

                            if((pawnInCheck instanceof NoPawn) || (pawnInCheck instanceof AvailableMove)) {// If this position is empty or already marked as available
                                break;// we break out of the loop.
                            }
                            else if (pawnInCheck.getSymbol() == opponent) {// If we find an opponent's pawn

                                pawnsToFlip[numOfFlips][0] = xOffset;// we store it in case we need to flip it later
                                pawnsToFlip[numOfFlips][1] = yOffset;
                                numOfFlips++;
                                opponentFound = true;// we signal it
                            }
                            else if (pawnInCheck.getSymbol() == move.getCurrentPlayer()) {// If we find a pawn that belongs to the player

                                if (opponentFound) {// and the last pawn was of an opponents, this position is valid
                                    for(int i = 0; i < numOfFlips; i++) {// so, we flip all the opponent's pawns that we stored in pawnsToFlip
                                        board[pawnsToFlip[i][0]][pawnsToFlip[i][1]] = createPawn(pawnsToFlip[i][0], pawnsToFlip[i][1], move.getCurrentPlayer());
                                    }

                                    moveValid = true;// and set moveValid as true.
                                    clearAvailableMoves();
                                }

                                break;//In the end, we stop searching and break out of the loop.
                            }
                            else{// If an unidentified piece is found
                                break;// we break out of the loop.
                            }

                            // Move in the current direction
                            xOffset += xDirection;
                            yOffset += yDirection;
                        }
                    }
                }
            }

            // If no pawns were flipped in any direction, it's not a valid move
            if (!moveValid){
                return false;// so we return false.
            }
            else{// Else
                board[move.getLine()][move.getColumn()] = createPawn(move.getLine(), move.getColumn(), move.getCurrentPlayer());// we make the chosen move
                setNumOfPawns(getNumOfPawns()+1);
                return true;// and return true.
            }
        }
    }
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    //Scores

    // Method to calculate a player's total pawns.
    public int numOfPlayerPawns(char playerSymbol) {
        int numOfPawns = 0;

        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(board[row][col].getSymbol() == playerSymbol) {
                    numOfPawns ++;
                }
            }
        }
        return numOfPawns;
    }


    // Method that calculates the player's total Score.

    private int totalPlayerScore(char playerSymbol, Pawn[][] board) {
        int score = 0;

        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                if(board[row][col].getSymbol() == playerSymbol) {
                    score += board[row][col].getValue();
                }
            }
        }
        return score;
    }


    // This method returns the score of the Board tha is given from the AI's perspective.
    public int AIBoardScore(Board gameBoard) {
        int AIScore;
         int playerScore;

        AIScore = totalPlayerScore(gameBoard.getAI(), gameBoard.getBoard());

        playerScore = totalPlayerScore(gameBoard.getPlayer(), gameBoard.getBoard());

        return AIScore - playerScore;
    }

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    //Method to print the board we play.

    // This method prints the whole board
    public void printBoard() {

        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            System.out.print(row+1);
            for (int col = 0; col < 8; col++) {
                System.out.print(" " + board[row][col].getSymbol());
            }
            System.out.println();
        }
        System.out.println();
    }

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    // This method checks if the game is over or not.
    public boolean gameOver() {

        LinkedList<Pawn> availableMovesX = markAvailableMoves('X');// We check if white player has any available moves.
        LinkedList<Pawn> availableMovesO = markAvailableMoves('O');// We check if black player has any available moves.

        if(numOfPawns == 64){// If the gameBoard is full
            return true;// it is game over
        }
        else{
            return availableMovesX.isEmpty() && availableMovesO.isEmpty();// if there are no available moves for both is game over.
        }
    }


    // Min-Max with alpha beta pruning
    public evalObject minMaxWithPruning(Board gameBoard, LinkedList<Pawn> availableMoves,  int depth, int alpha, int beta, char playerSymbol) {
        evalObject selection = new evalObject();
        evalObject eval;

        if(depth == 0 || gameBoard.gameOver()) {
            selection.setSelectedEval(AIBoardScore(gameBoard));
            return selection;
        }

        if(playerSymbol == gameBoard.getAI()) {
            selection.setSelectedEval(Integer.MIN_VALUE);

            for (Pawn pawn : availableMoves) {
                Board tempBoard = new Board(gameBoard);
                Move move = new Move(pawn.getLine(), pawn.getColumn(), pawn.getPosition(), playerSymbol);

                boolean validMove = tempBoard.isMoveValid(move.getLine(), move.getColumn(), move.getCurrentPlayer(), tempBoard.getPlayer());
                if(validMove){
                    boolean moveSuccess = tempBoard.makeMove(move);

                    tempBoard.clearAvailableMoves();
                    LinkedList<Pawn> tempAvailableMoves = tempBoard.markAvailableMoves(gameBoard.getPlayer());

                    eval = minMaxWithPruning(tempBoard, tempAvailableMoves, depth-1, alpha, beta, tempBoard.getPlayer());

                    if(selection.getSelectedEval() < eval.getSelectedEval()){
                        selection.setSelectedEval(eval.getSelectedEval());
                        selection.setSelectedMove(move);
                    }

                    alpha = Math.max(alpha, eval.getSelectedEval());
                    if(beta <= alpha){
                        break;
                    }
                }
            }
        }
        else{
            selection.setSelectedEval(Integer.MAX_VALUE);

            for (Pawn pawn : availableMoves) {
                Board tempBoard = new Board(gameBoard);
                Move move= new Move(pawn.getLine(), pawn.getColumn(), pawn.getPosition(), playerSymbol);

                boolean validMove = tempBoard.isMoveValid(move.getLine(), move.getColumn(), move.getCurrentPlayer(), tempBoard.getAI());
                if(validMove){
                    boolean moveSuccess = tempBoard.makeMove(move);

                    tempBoard.clearAvailableMoves();
                    LinkedList<Pawn> tempAvailableMoves = tempBoard.markAvailableMoves(gameBoard.getAI());

                    eval = minMaxWithPruning(tempBoard, tempAvailableMoves, depth-1, alpha, beta, tempBoard.getAI());

                    if(selection.getSelectedEval() > eval.getSelectedEval()){
                        selection.setSelectedEval(eval.getSelectedEval());
                        selection.setSelectedMove(move);
                    }

                    beta = Math.min(beta, eval.getSelectedEval());
                    if(beta <= alpha){
                        break;
                    }
                }
            }
        }
        return selection;
    }
}

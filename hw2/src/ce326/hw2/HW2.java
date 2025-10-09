package ce326.hw2;

import java.util.LinkedList;
import java.util.Scanner;

public class HW2 {
    public static Scanner sc = new Scanner(System.in);
    public static Board startGame() {

        Board gameEssentials = null;

        System.out.println("Welcome to Othello!\n");

        System.out.print("Choose color (black/white/b/w/B/W/O/X/o/x): ");

        while (sc.hasNext()) {
            String word = sc.next();

            if ("black".equals(word) || "white".equals(word) || "b".equals(word) || "w".equals(word) || "B".equals(word) || "W".equals(word) || "O".equals(word) || "X".equals(word) || "o".equals(word) || "x".equals(word)) {
                gameEssentials = new Board();

                if ("black".equals(word) || "b".equals(word) || "B".equals(word) || "O".equals(word) || "o".equals(word)) {
                    gameEssentials.setPlayer('O');
                    gameEssentials.setAI('X');
                } else {
                    gameEssentials.setPlayer('X');
                    gameEssentials.setAI('O');
                }
                break;
            } else {
                System.out.println("Invalid color. Try again...");

                System.out.print("Choose color (black/white/b/w/B/W/O/X/o/x): ");
            }
        }
        return gameEssentials;
    }


    public static int movesForward() {

        int depth = 0;

        System.out.print("Estimate forward moves [1,9]: ");

        while (sc.hasNext()) {
            depth = Integer.parseInt(sc.next());

            if ((depth < 1) || (depth > 9)) {
                System.out.println("Invalid moves. Try again...");

                System.out.print("Estimate forward moves [1,9]: ");
            } else {
                break;
            }
        }
        return depth;
    }


    public static boolean isOver(Board gameEssentials) {

        if ((gameEssentials.getNumOfPawns() == 64) || (gameEssentials.gameOver())){

            int playerPawns = gameEssentials.numOfPlayerPawns(gameEssentials.getPlayer());
            int AIPawns = gameEssentials.numOfPlayerPawns(gameEssentials.getAI());

            System.out.println("X:" + (gameEssentials.getPlayer() == 'X' ? playerPawns : AIPawns) + "/O:" + (gameEssentials.getPlayer() == 'O' ? playerPawns : AIPawns));

            if (playerPawns != AIPawns) {
                System.out.println(" Player " + (playerPawns > AIPawns ? gameEssentials.getPlayer() : gameEssentials.getAI()) + " won!");
            }
            else{
                System.out.println(" It's a draw!");
            }
            System.out.println();

            return true;
        }
        else {
            return false;
        }
    }

    public static char nextPlayer(char currentPlayer) {
        if(currentPlayer == 'X'){
            return 'O';
        }
        else{
            return 'X';
        }
    }


    public static void main(String[] args) {

        evalObject AIEvaluation;
        int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
        char currentPlayer = 'O';
        Move newMove;
        boolean validMove;
        LinkedList <Pawn> availableMoves;
        LinkedList <Move> movesHistory = new LinkedList<>();


        Board gameEssentials = startGame();

        int depth = movesForward();

        while(true){

            boolean gameOver = isOver(gameEssentials);
            if (gameOver) {
                break;
            }

            // 2nd step.
            System.out.println("Player's " + currentPlayer + " turn\n");

            availableMoves = gameEssentials.markAvailableMoves(currentPlayer);
            gameEssentials.printBoard();

            if(availableMoves.isEmpty()) {
                System.out.println("No available moves!");
                currentPlayer = nextPlayer(currentPlayer);
                continue;
            }

            // 3rd step.
            if(currentPlayer == gameEssentials.getAI()) {
                AIEvaluation = gameEssentials.minMaxWithPruning(gameEssentials, availableMoves, depth, alpha, beta, currentPlayer);
                newMove = new Move(AIEvaluation.getSelectedMove().getLine(), AIEvaluation.getSelectedMove().getColumn(), AIEvaluation.getSelectedMove().getPosition(), currentPlayer);
                
                gameEssentials.makeMove(newMove);
            }
            else {
                System.out.print("Enter your move (e.g. c2): ");
                while (true) {
                    String movePosition = sc.next().toLowerCase();
                    validMove = gameEssentials.isMoveValid(movePosition.charAt(1)-'1', movePosition.charAt(0)-'a', currentPlayer, gameEssentials.getAI());
                    if ((!validMove) || (movePosition.length() > 2) || (movePosition.charAt(0)- 'a' < 0) || (movePosition.charAt(0) - 'a' > 8) || (movePosition.charAt(1) - '1' < 0) || (movePosition.charAt(1) - '1' > 8)) {
                        System.out.println("Invalid move. Try again!\n");

                        System.out.print("Enter your move (e.g. c2): ");
                    }
                    else{
                        newMove = new Move((movePosition.charAt(1)-'1'), (movePosition.charAt(0)-'a'), movePosition, gameEssentials.getPlayer());
                        
                        gameEssentials.makeMove(newMove);
                        break;
                    }
                }
            }
            movesHistory.add(newMove);

            // 4th step.
            System.out.println("Player " + currentPlayer + " played:" + newMove.toString() + "\n");
            gameEssentials.clearAvailableMoves();
            gameEssentials.printBoard();

            System.out.print("Moves history:");
            for(Move move : movesHistory) {
                System.out.print(move.toString());
            }
            System.out.println("\n");


            // 5th step.
           currentPlayer = nextPlayer(currentPlayer);
        }
    }
}
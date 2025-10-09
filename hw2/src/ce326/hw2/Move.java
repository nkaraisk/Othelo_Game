package ce326.hw2;

public class Move extends Pawn{
    private char currentPlayer;

    public Move(int line, int column, String position, char player) {
        super(line, column, position);
        this.currentPlayer = player;
    }

    // Getter
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    // Setter
    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public String toString() {
        String position = getPosition();
        return " " + position;
    }
}



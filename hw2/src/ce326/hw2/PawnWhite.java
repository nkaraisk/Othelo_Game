package ce326.hw2;

public class PawnWhite extends Pawn {

    public PawnWhite(int line, int column, int value, String position) {
        super(line, column, value, position);
    }

    @Override
    public char getSymbol() {
        return 'X';
    }
}

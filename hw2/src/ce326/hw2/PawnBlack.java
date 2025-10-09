package ce326.hw2;

public class PawnBlack extends Pawn {

    public PawnBlack(int line, int column, int value, String position) {
        super(line, column, value, position);
    }

    @Override
    public char getSymbol() {
        return 'O';
    }
}

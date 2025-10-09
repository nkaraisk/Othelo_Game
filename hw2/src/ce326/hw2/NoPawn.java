package ce326.hw2;

public class NoPawn extends Pawn{

    public NoPawn(int line, int column, int value, String position) {
        super(line, column, value, position);
    }

    @Override
    public char getSymbol() {
        return '.';
    }
}

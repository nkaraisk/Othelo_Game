package ce326.hw2;

public abstract class Pawn {

    private int line;
    private int column;
    private int value;
    private String position;


    public Pawn(int line, int column, int value, String position) {
        setLine(line);
        setColumn(column);
        setValue(value);
        setPosition(position);
    }


    public Pawn(int line, int column, String position) {
        setLine(line);
        setColumn(column);
        setPosition(position);
    }


    // Get methods
    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    public String getPosition() {
        return position;
    }

    public char getSymbol() {
        return ' ';
    }

    // Set methods
    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

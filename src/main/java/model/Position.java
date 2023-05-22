package model;

/**Represents a position on the board.*/
public class Position {
    private final int row;
    private final int column;

    /**
     * Instantiates {@code Position} with given parameters.
     * @param row Row index
     * @param column Column Index
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /** @return Row index*/
    public int getRow() {
        return row;
    }

    /**@return Column index*/
    public int getColumn() {
        return column;
    }


    /** @return A position as a string*/
    @Override
    public String toString() {
        return "(" + row+1 + "," + column+1 + ')';
    }
}



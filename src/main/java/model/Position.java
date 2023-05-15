package model;

/**Represents position on the board.*/
public class Position {
    private final int row;
    private final int column;

    /**
     * Instantiates Position with given parameters.
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
}



package model;

public class EndGameState {
    private int whiteNumber;
    private int blackNumber;
    private Disk[][] board;

    public EndGameState() {}

    public EndGameState(int whiteNumber, int blackNumber, Disk[][] board){
        this.whiteNumber = whiteNumber;
        this.blackNumber = blackNumber;
        this.board = board;
    }

    public int getWhiteNumber() {
        return whiteNumber;
    }

    public int getBlackNumber() {
        return blackNumber;
    }

    public Disk[][] getBoard() {
        return board;
    }

    public void setWhiteNumber(int whiteNumber) {
        this.whiteNumber = whiteNumber;
    }

    public void setBlackNumber(int blackNumber) {
        this.blackNumber = blackNumber;
    }

    public void setBoard(Disk[][] board) {
        this.board = board;
    }
}

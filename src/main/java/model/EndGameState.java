package model;

/** Class representing the end state of game, in order to store them persistently.*/
public class EndGameState {
    private int whiteNumber;
    private int blackNumber;
    private Disk[][] board;

    /** Empty Constructor need for Jackson ObjectMapper.*/
    public EndGameState() {}

    /**
     * Instantiates EndGameState with the numbers of each {@link Colors} and the {@link Disk} matrix.
     * @param whiteNumber Number of white disks
     * @param blackNumber Number of black disks
     * @param board       Instance of the gameBoard
     */
    public EndGameState(int whiteNumber, int blackNumber, Disk[][] board){
        this.whiteNumber = whiteNumber;
        this.blackNumber = blackNumber;
        this.board = board;
    }

    /** @return Number of white disks at the end*/
    public int getWhiteNumber() {
        return whiteNumber;
    }

    /** @return Number of black disks at the end*/
    public int getBlackNumber() {
        return blackNumber;
    }

    /** @return The gameBoard at the end*/
    public Disk[][] getBoard() {
        return board;
    }

    /**
     * Setter needed for Jackson ObjectMapper.
     * @param whiteNumber Number of white disks
     */
    public void setWhiteNumber(int whiteNumber) {
        this.whiteNumber = whiteNumber;
    }

    /**
     * Setter needed for Jackson ObjectMapper.
     * @param blackNumber Number of black disks
     */
    public void setBlackNumber(int blackNumber) {
        this.blackNumber = blackNumber;
    }

    /**
     * Setter needed for Jackson ObjectMapper.
     * @param board Instance of {@link Disk} Matrix board
     */
    public void setBoard(Disk[][] board) {
        this.board = board;
    }
}

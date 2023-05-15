package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** Represents the main logic of the game.*/
public class GameModel {
    /**Static element representing the size of the Board.*/
    public static int boardSize=8;
    private final Disk[][] gameBoard =new Disk[boardSize][boardSize];
    private boolean currentPlayer;
    private int whiteNumber;
    private int blackNumber;
    private int validNumber;
    private final List<ResultOfFlipping> previousFlips=new ArrayList<>();
    private EndGameState endGameState;

    /**
     * Instantiates GameModel by first initializing the board to be the start of Othello, then sets the current player to Black by the rules.
     * Calculates valid steps, and current numbers of each player.
     */
    public GameModel() {
        initializeBoard();
        currentPlayer=false;
        validMoves();
        calculateNumbers();
    }
    private void initializeBoard(){
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                gameBoard[row][col] = new Disk(Colors.NONE);
                if ((row==3 && col==3) || (row==4 && col==4)) {
                    gameBoard[row][col].setColor(Colors.WHITE);
                }
                else if ((row==3 && col==4) || (row==4 && col==3)){
                    gameBoard[row][col].setColor(Colors.BLACK);
                }
            }
        }
    }

    /** @return Number of White disks*/
    public int getWhiteNumber() {
        return whiteNumber;
    }

    /** @return Number of Black disks*/
    public int getBlackNumber() {
        return blackNumber;
    }

    /** @return The state of the board at the end of the game*/
    public EndGameState getEndGameState() {
        return endGameState;
    }

    /** @return The color of the opponent {@link Colors}*/
    public Colors opponentColor(){
        if (currentPlayer)
            return Colors.BLACK;
        else
            return Colors.WHITE;
    }

    /** @return The color of current player {@link Colors}*/
    public Colors currentColor(){
        if (currentPlayer)
            return Colors.WHITE;
        else
            return Colors.BLACK;
    }

    /**
     * @param row The row index of the board
     * @param col The column index of the board
     * @return The disk at the given coordinates
     */
    public  Disk getDisk(int row,int col) {
        return gameBoard[row][col];
    }

    /** @return True, if the board is full, neither player has any valid moves, or either player has 0 disks, false otherwise */
    public boolean isGameOver(){
        if (whiteNumber == 0 || blackNumber == 0 || blackNumber + whiteNumber == 64 || validNumber==0){
            resetValid();
            setUpEndGameState();
            return true;
        }
        return false;
    }

    /** @return The game board as a String, 0 meaning nothing, 1 meaning valid, W meaning white and B meaning black.*/
    public String boardAsString() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gameBoard)
                .map(this::getRowString)
                .forEach(rowString -> sb.append(rowString).append(System.lineSeparator()));

        return sb.toString();
    }

    private String getRowString(Disk[] row) {
        return Arrays.stream(row)
                .map(disk -> switch (disk.getColor()) {
                    case NONE -> "0";
                    case VALID -> "1";
                    case WHITE -> "W";
                    case BLACK -> "B";
                })
                .collect(Collectors.joining(" "));
    }

    /** @return List of moves represented by Positions, for debugging reasons.*/
    public List<Position> getAllMoves(){
        return previousFlips.stream()
                .map(ResultOfFlipping::getTriggerPosition)
                .collect(Collectors.toList());
    }

    /** Undoes the previous move, by restoring the flipped position's colors.
     * @return True, if there was anything to undo, based on the list of previous moves.*/
    public boolean undoLast(){
        if (previousFlips.size()>=1) {
            ResultOfFlipping prevFlip = previousFlips.remove(previousFlips.size() - 1);
            gameBoard[prevFlip.getTriggerPosition().getRow()][prevFlip.getTriggerPosition().getColumn()].setColor(Colors.NONE);
            for (var flip : prevFlip.getFlippedPositions()) {
                gameBoard[flip.getRow()][flip.getColumn()].setColor(prevFlip.getColor());
            }
            nextPlayer();
            return true;
        }
        return false;
    }

    /**
     * Puts down a disk at the given coordinates, adds the move and the turned disks to {@code previousFlips} then switches to the next player.
     * @param row The row index of the board
     * @param col The column index of the board

     */
    public void putDisk(int row, int col) {
        gameBoard[row][col].setColor(currentColor());
        Position triggerPosition = new Position(row, col);
        List<Position> turnedPositions = flipDisks(row, col);
        previousFlips.add(new ResultOfFlipping(triggerPosition,turnedPositions,opponentColor()));
        nextPlayer();
    }
    private List<Position> flipDisks(int row, int col) {
        List<Position> turnedPositions = new ArrayList<>();
        var neighbourList = enemyNeighbours(row, col);
        for (var vector : neighbourList) {
            List<Disk> needTurning = new ArrayList<>();
            int xAxisDirection = vector.xAxisDirection();
            int yAxisDirection = vector.yAxisDirection();
            int currentXPosition = row + xAxisDirection;
            int currentYPosition = col + yAxisDirection;
            while ((currentXPosition >= 0 && currentXPosition <= boardSize - 1) &&
                    (currentYPosition >= 0 && currentYPosition <= boardSize - 1)) {
                if (gameBoard[currentXPosition][currentYPosition].getColor() == opponentColor()) {
                    needTurning.add(gameBoard[currentXPosition][currentYPosition]);
                    turnedPositions.add(new Position(currentXPosition, currentYPosition));
                } else if (gameBoard[currentXPosition][currentYPosition].getColor() == currentColor()) {
                    flipDisksInList(needTurning);
                    break;
                } else {
                    break;
                }
                currentXPosition += xAxisDirection;
                currentYPosition += yAxisDirection;

            }
        }
        return turnedPositions;
    }
    private void flipDisksInList(List<Disk> disks){
        for (var disk : disks) {
            disk.setColor(currentColor());
        }
    }
    private void nextPlayer(){
        calculateNumbers();
        if (!isGameOver()){
            currentPlayer = !currentPlayer;
            validMoves();
            if (validNumber == 0) {
                currentPlayer = !currentPlayer;
                validMoves();
            }
        }
    }
    private void setUpEndGameState() {
        endGameState = new EndGameState(whiteNumber,blackNumber,gameBoard);
    }

    private void calculateNumbers(){
        int tempWhiteNumber=0;
        int tempBlackNumber=0;
        for (var i = 0; i < boardSize; i++) {
            for (var j = 0; j < boardSize; j++) {
                if (gameBoard[i][j].getColor()==Colors.WHITE)
                    tempWhiteNumber++;
                else if (gameBoard[i][j].getColor()==Colors.BLACK)
                    tempBlackNumber++;
            }
        }
        this.whiteNumber=tempWhiteNumber;
        this.blackNumber=tempBlackNumber;
    }

    private void validMoves() {
        resetValid();
        ArrayList<Position> possibleMoves = possibleMoves();
        for (var move : possibleMoves) {
            var neighbourList=enemyNeighbours(move.getRow(), move.getColumn());
            for (var vector:neighbourList){
                int xAxisDirection = vector.xAxisDirection();
                int yAxisDirection = vector.yAxisDirection();
                int currentXPosition=move.getRow()+xAxisDirection;
                int currentYPosition=move.getColumn()+yAxisDirection;
                while (((currentXPosition >= 0) && (currentXPosition <= 7)) &&
                        ((currentYPosition >= 0) && (currentYPosition <= 7))){
                    if (gameBoard[currentXPosition][currentYPosition].getColor()==currentColor()){
                        gameBoard[move.getRow()][move.getColumn()].setColor(Colors.VALID);
                        validNumber++;
                    }
                    else if(gameBoard[currentXPosition][currentYPosition].getColor()==Colors.NONE ||
                            gameBoard[currentXPosition][currentYPosition].getColor()==Colors.VALID){
                        break;
                    }
                    currentXPosition+=xAxisDirection;
                    currentYPosition+=yAxisDirection;
                }
            }
        }
    }
    private void resetValid(){
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                if (gameBoard[row][col].getColor()==Colors.VALID)
                    gameBoard[row][col].setColor(Colors.NONE);

            }
        }
        validNumber=0;
    }
    private ArrayList<Position> possibleMoves(){
        ArrayList<Position> stepsList =new ArrayList<>();
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                if(gameBoard[row][col].getColor()==Colors.NONE && !enemyNeighbours(row,col).isEmpty())
                    stepsList.add(new Position(row,col));
            }
        }
        return stepsList;
    }
    private ArrayList<DirectionVector>enemyNeighbours(int row, int col){
        ArrayList<DirectionVector> directionVectors = new ArrayList<>();
        for (var rowShift=-1 ;rowShift<=1;rowShift++){
            for (var colShift=-1;colShift<=1;colShift++){
                if( ((row+rowShift>=0 || rowShift>=0) && (row+rowShift<=boardSize-1 || rowShift<1)) &&
                        ((col+colShift>=0 || colShift>=0) && (col+colShift<=boardSize-1 || colShift<1)) ){
                    if (gameBoard[row+rowShift][col+colShift].getColor()==opponentColor())
                        directionVectors.add(new DirectionVector(rowShift,colShift));
                }
            }
        }
        return directionVectors;
    }
}

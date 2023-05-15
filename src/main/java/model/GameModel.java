package model;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    public static int boardSize=8;
    private final Disk[][] gameBoard =new Disk[boardSize][boardSize];
    private boolean currentPlayer;
    private int whiteNumber;
    private int blackNumber;
    private int validNumber;
    private final List<ResultOfFlipping> previousFlips=new ArrayList<>();
    private EndGameState endGameState;

    public GameModel() {
        initializeBoard();
        currentPlayer=false;
        validSteps();
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

    public int getWhiteNumber() {
        return whiteNumber;
    }

    public int getBlackNumber() {
        return blackNumber;
    }

    public EndGameState getEndGameState() {
        return endGameState;
    }

    public Colors opponentColor(){
        if (currentPlayer)
            return Colors.BLACK;
        else
            return Colors.WHITE;
    }

    public Colors currentColor(){
        if (currentPlayer)
            return Colors.WHITE;
        else
            return Colors.BLACK;
    }

    public  Disk getDisk(int row,int col) {
        return gameBoard[row][col];
    }

    public boolean isGameOver(){
        if (whiteNumber == 0 || blackNumber == 0 || blackNumber + whiteNumber == 64 || validNumber==0){
            resetValid();
            setUpEndGameState();
            return true;
        }
        return false;
    }

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
            validSteps();
            if (validNumber == 0) {
                currentPlayer = !currentPlayer;
                validSteps();
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

    private void validSteps() {
        resetValid();
        ArrayList<Position> possibleSteps = possibleSteps();
        for (var step : possibleSteps) {
            var neighbourList=enemyNeighbours(step.getRow(), step.getColumn());
            for (var vector:neighbourList){
                int xAxisDirection = vector.xAxisDirection();
                int yAxisDirection = vector.yAxisDirection();
                int currentXPosition=step.getRow()+xAxisDirection;
                int currentYPosition=step.getColumn()+yAxisDirection;
                while (((currentXPosition >= 0) && (currentXPosition <= 7)) &&
                        ((currentYPosition >= 0) && (currentYPosition <= 7))){
                    if (gameBoard[currentXPosition][currentYPosition].getColor()==currentColor()){
                        gameBoard[step.getRow()][step.getColumn()].setColor(Colors.VALID);
                        validNumber++;
                    }
                    else if(gameBoard[currentXPosition][currentYPosition].getColor()==Colors.NONE)
                        break;
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
    private ArrayList<Position> possibleSteps(){
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

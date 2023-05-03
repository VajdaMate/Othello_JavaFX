package model;

import org.tinylog.Logger;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;


public class GameModel {

    public static int boardSize=8;
    private Disk[][] gameBoard =new Disk[8][8];
    private boolean currentPlayer;
    private int whiteNumber;
    private int blackNumber;
    private int validNumber;
    private boolean Over;


    public GameModel() {
        for (var row = 0; row < boardSize; row++) {
            for (var col = 0; col < boardSize; col++) {
                gameBoard[row][col] = new Disk(new Position(row,col),Colors.NONE);
                if ((row==3 && col==3) || (row==4 && col==4)) {
                    gameBoard[row][col].setColor(Colors.WHITE);
                }
                else if ((row==3 && col==4) || (row==4 && col==3)){
                    gameBoard[row][col].setColor(Colors.BLACK);
                }
            }
        }
        currentPlayer=false;
        validSteps();
        calculateNumbers();
    }

    public void calculateNumbers(){
        this.whiteNumber=0;
        this.blackNumber=0;
        for (var i = 0; i < boardSize; i++) {
            for (var j = 0; j < boardSize; j++) {
                if (gameBoard[i][j].getColor()==Colors.WHITE)
                    this.whiteNumber++;
                else if (gameBoard[i][j].getColor()==Colors.BLACK)
                    this.blackNumber++;
            }
        }
    }

    public int getWhiteNumber() {
        return whiteNumber;
    }

    public int getBlackNumber() {
        return blackNumber;
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

    private void nextPlayer(){
        if (isGameOver())
            Over=true;
        else {
            currentPlayer = !currentPlayer;
            validSteps();
            if (validNumber == 0) {
                currentPlayer = !currentPlayer;
                validSteps();
            }
        }

    }


    private ArrayList<Pair<Integer,Integer>>enemyNeighbours(int row, int col){
        ArrayList<Pair<Integer,Integer>> positionVector = new ArrayList<>();
        for (var i=-1;i<=1;i++){
            for (var j=-1;j<=1;j++){
                if( ((row+i>=0 || i>=0) && (row+i<=boardSize-1 || i<1)) &&
                    ((col+j>=0 || j>=0) && (col+j<=boardSize-1 || j<1)) ){
                    if (gameBoard[row+i][col+j].getColor()==opponentColor())
                            positionVector.add(new Pair<>(i, j));
                }
            }
        }
        return positionVector;
    }

    private ArrayList<Position> possibleSteps(){
        ArrayList<Position> stepsList =new ArrayList<>();
        for (var i = 0; i < boardSize; i++) {
            for (var j = 0; j < boardSize; j++) {
                if(gameBoard[i][j].getColor()==Colors.NONE && !enemyNeighbours(i,j).isEmpty())
                    stepsList.add(new Position(i,j));
            }
        }
        return stepsList;
    }

    private void validSteps() {
        for (var i = 0; i < boardSize; i++) {
            for (var j = 0; j < boardSize; j++) {
                if (gameBoard[i][j].getColor()==Colors.VALID)
                    gameBoard[i][j].setColor(Colors.NONE);
            }
        }
        ArrayList<Position> possibleSteps = possibleSteps();
        validNumber=0;
        for (var step : possibleSteps) {
            var neighbourList=enemyNeighbours(step.getRow(), step.getColumn());
            for (var vector:neighbourList){
                    int x = vector.getKey();
                    int y = vector.getValue();
                    int startX=step.getRow()+x;
                    int startY=step.getColumn()+y;
                    while (((startX >= 0) && (startX <= 7)) && ((startY >= 0) && (startY <= 7))){
                        if (gameBoard[startX][startY].getColor()==currentColor()){
                            gameBoard[step.getRow()][step.getColumn()].setColor(Colors.VALID);
                            validNumber++;
                        }
                        else if(gameBoard[startX][startY].getColor()==Colors.NONE)
                            break;
                        startX+=x;
                        startY+=y;
                    }
                }
        }
    }

    public void putDisk(int row, int col){
        gameBoard[row][col].setColor(currentColor());
        var neighbourList = enemyNeighbours(row, col);
        for (var vector : neighbourList) {
            List<Disk> needTurning = new ArrayList<>();
            int x = vector.getKey();
            int y = vector.getValue();
            int startX = row + x;
            int startY = col + y;
            needTurning.add(gameBoard[startX][startY]);
            while ((startX >=0 && startX <= boardSize-1) && (startY >= 0 && startY <=boardSize-1)) {
                if (gameBoard[startX][startY].getColor() == opponentColor()) {
                    needTurning.add(gameBoard[startX][startY]);
                }
                else if (gameBoard[startX][startY].getColor() == currentColor()){
                    for (var disk : needTurning) {
                        disk.setColor(currentColor());
                    }
                }
                else {
                    break;
                }
                startX += x;
                startY += y;
            }
        }
        calculateNumbers();
        nextPlayer();

    }




    public boolean isGameOver(){
        return whiteNumber == 0 || blackNumber == 0 || blackNumber + whiteNumber == 64 || validNumber==0;
    }

    public  Disk[][] getGameBoard() {
        return gameBoard;
    }

    public boolean isOver() {
        return Over;
    }
}

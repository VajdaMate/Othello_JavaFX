package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameModel {

    private Disk[][] gameBoard =new Disk[8][8];
    private boolean currentPlayer;

    public GameModel() {
        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                gameBoard[i][j] = new Disk(new Position(i,j),Colors.NONE);
                if ((i==3 && j==3) || (i==4 && j==4)) {
                    gameBoard[i][j].setColor(Colors.BLACK);
                }
                else if ((i==3 && j==4) || (i==4 && j==3)){
                    gameBoard[i][j].setColor(Colors.WHITE);
                }
            }
        }
        currentPlayer=false;
        validSteps();
    }


    private Colors opponentColor(){
        if (currentPlayer)
            return Colors.BLACK;
        else
            return Colors.WHITE;
    }

    private Colors currentColor(){
        if (currentPlayer)
            return Colors.WHITE;
        else
            return Colors.BLACK;
    }

    public void nextPlayer(){
        currentPlayer=!currentPlayer;
    }


    private ArrayList<Pair<Integer,Integer>>enemyNeighbours(int row, int column){
        ArrayList<Pair<Integer,Integer>> positionVector = new ArrayList<>();
        for (var i=-1;i<2;i++){
            for (var j=-1;j<2;j++){
                if((row+i>=0 && row+i<8) && (column+j>=0 && column+j<8)){
                    if (gameBoard[row+i][column+j].getColor()==opponentColor())
                        positionVector.add(new Pair<>(i, j));
                }
            }
        }
        return positionVector;
    }

    private ArrayList<Position> allSteps(){
        ArrayList<Position> stepsList =new ArrayList<>();
        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                if(gameBoard[i][j].getColor()==Colors.NONE && !enemyNeighbours(i,j).isEmpty())
                    stepsList.add(new Position(i,j));
            }
        }
        return stepsList;
    }

    public void validSteps() {
        ArrayList<Position> allSteps = allSteps();
        for (var step : allSteps) {
            var neighbourList=enemyNeighbours(step.getRow(), step.getColumn());
            boolean foundValidMove = false;
            for (var vector:neighbourList){
                    int x = vector.getKey();
                    int y = vector.getValue();
                    int startX=step.getRow()+x;
                    int startY=step.getColumn()+y;
                    while ((startX>=0 && startX<8) &&(startY>=0 && startY<8)){
                        startX+=x;
                        startY+=y;
                        if (gameBoard[startX][startY].getColor()==currentColor()){
                            gameBoard[step.getRow()][step.getColumn()].setValid();
                            foundValidMove=true;
                            break;
                        }
                        else if(gameBoard[startX][startY].getColor()==Colors.NONE)
                            break;
                    }
                    if (foundValidMove)
                        break;
                }
        }
    }

    public void putDisk(int i, int j){




        nextPlayer();
        validSteps();
    }

    public Disk[][] getGameBoard() {
        return gameBoard;
    }
}

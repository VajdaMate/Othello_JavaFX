package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.GameModel;

import java.util.List;

public class GameBoardController {
    @FXML
    public Circle currentPlayerDisk;
    @FXML
    public Label numberOfBlackDisks;
    @FXML
    public Label numberOfWhiteDisks;
    @FXML
    public GridPane gameBoard;
    private GameModel model;

    @FXML
    public void initialize() {
        model = new GameModel();
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                var circle = createCircle(i, j);
                gameBoard.add(circle, j, i);

            }
        }

    }
    private Circle createCircle(int i, int j) {
        var circle = new Circle(50);
        circle.setOpacity(0);
        if ((i==3 && j==3) || (i==4 && j==4)) {
            circle.setFill(Color.BLACK);
            circle.setOpacity(1);
        }
        else if ((i==3 && j==4) || (i==4 && j==3)){
            circle.setFill(Color.WHITE);
            circle.setOpacity(1);
        }
        else if (model.getGameBoard()[i][j].isValid()) {
            circle.setFill(Color.GRAY);
            circle.setOpacity(1);
        }
        return circle;
    }


    @FXML
    public void restartGame(ActionEvent actionEvent) {
    }

    @FXML
    public void diskDrop(MouseEvent mouseEvent) {
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import model.GameModel;

public class GameBoardController {
    public Circle currentPlayerDisk;
    public Label numberOfBlackDisks;
    public Label numberOfWhiteDisks;
    private GameModel model;

    @FXML
    public void initialize() {
        model = new GameModel();
    }

    public void restartGame(ActionEvent actionEvent) {
    }
}

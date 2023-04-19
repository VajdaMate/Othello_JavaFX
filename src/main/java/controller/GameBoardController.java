package controller;

import javafx.fxml.FXML;
import model.GameModel;

public class GameBoardController {
    private GameModel model;

    @FXML
    public void initialize() {
        model = new GameModel();
    }

}

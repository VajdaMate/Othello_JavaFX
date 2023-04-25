package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class EndScreenController {
    @FXML
    public Label endWhiteDisks;
    @FXML
    public Label endBlackDisks;
    @FXML
    public Button endRestartButton;
    @FXML
    public GridPane endBoard;

    public void restartGame(ActionEvent actionEvent) {
    }
}

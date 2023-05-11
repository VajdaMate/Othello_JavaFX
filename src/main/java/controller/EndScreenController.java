package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Colors;
import model.GameModel;
import org.tinylog.Logger;

import java.io.IOException;

public class EndScreenController {
    @FXML
    public Label endWhiteDisks;
    @FXML
    public Label endBlackDisks;
    @FXML
    public Button endRestartButton;
    @FXML
    public GridPane endBoard;
    private GameModel model;

    public void setModel(GameModel model) {
        this.model = model;
        setUpBoard();
    }

    private void setUpBoard(){
        for (int row = 0; row < endBoard.getRowCount(); row++) {
            for (int col = 0; col < endBoard.getColumnCount(); col++) {
                var circle = new Circle(23.75);
                circle.setFill(getColor(model.getDisk(row,col).getColor()));
                endBoard.add(circle,col,row);
            }
        }
        endWhiteDisks.setText(String.valueOf(model.getWhiteNumber()));
        endBlackDisks.setText(String.valueOf(model.getBlackNumber()));
    }
    private Color getColor(Colors color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case VALID -> Color.LIGHTSLATEGRAY;
            default -> Color.TRANSPARENT;
        };
    }

    @FXML
    public void restartGame(ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Restarted");
    }

    @FXML
    public void backToTheStart(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Back to the Start Menu");
    }

    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

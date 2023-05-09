package controller;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Colors;
import model.GameModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.beans.binding.Bindings;

import java.io.IOException;


import org.tinylog.Logger;

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
    private StringProperty whiteValue = new SimpleStringProperty();
    private StringProperty blackValue = new SimpleStringProperty();
    private ObjectProperty<Color> currentColorValue = new SimpleObjectProperty<>();

    @FXML
    public void initialize() {
        model = new GameModel();
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            for (int j = 0; j < gameBoard.getColumnCount(); j++) {
                var square = createSquare(i, j);
                gameBoard.add(square, j, i);
            }
        }

        currentColorValue.set(getColor(model.currentColor()));
        whiteValue.set(String.valueOf(model.getWhiteNumber()));
        blackValue.set(String.valueOf(model.getBlackNumber()));

        currentPlayerDisk.fillProperty().bind(currentColorValue);
        numberOfWhiteDisks.textProperty().bind(whiteValue);
        numberOfBlackDisks.textProperty().bind(blackValue);
        Logger.info("Game Initialized");
    }


    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        var circle = new Circle(50);
        circle.fillProperty().bind(Bindings.createObjectBinding(() ->
                getColor(model.getDisk(row,col).getColor()), model.getDisk(row,col).colorProperty()));
        square.getChildren().add(circle);
        square.setOnMouseClicked(this::diskPut);
        return square;
    }



    @FXML
    public void diskPut(MouseEvent mouseEvent) {
        StackPane square = (StackPane) mouseEvent.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        if (model.getDisk(row,col).getColor()==Colors.VALID) {
            Logger.info(("%s put a disk on the board at: %d,%d")
                    .formatted((model.currentColor().toString()),row+1,col+1));
            model.putDisk(row,col);
            whiteValue.set(String.valueOf(model.getWhiteNumber()));
            blackValue.set(String.valueOf(model.getBlackNumber()));
            currentColorValue.set(getColor(model.currentColor()));
            if (model.isOver()) {
                Logger.info("The Game is Over");
                Logger.info(("The scores are: White:%s   Black: %s")
                        .formatted(numberOfWhiteDisks.getText(),numberOfBlackDisks.getText()));
                try {
                    nextScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void nextScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root=loader.load();
        EndScreenController endScreenController = loader.getController();
        endScreenController.setModel(model);
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
    public void restartGame() throws IOException {
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainGame.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Restarted");
    }

}

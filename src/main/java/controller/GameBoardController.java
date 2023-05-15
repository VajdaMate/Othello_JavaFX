package controller;
import model.Colors;
import model.GameModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
    private final StringProperty whiteValue = new SimpleStringProperty();
    private final StringProperty blackValue = new SimpleStringProperty();
    private final ObjectProperty<Color> currentColorValue = new SimpleObjectProperty<>();

    @FXML
    public void initialize() {
        model = new GameModel();
        for (int row = 0; row < gameBoard.getRowCount(); row++) {
            for (int col = 0; col < gameBoard.getColumnCount(); col++) {
                var square = createSquare(row, col);
                gameBoard.add(square, col, row);
            }
        }
        bindSetup();
    }
    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        var circle = new Circle(50);
        circle.fillProperty().bind(Bindings.createObjectBinding(() ->
                getOwnColor(model.getDisk(row,col).getColor()), model.getDisk(row,col).colorProperty()));
        square.getChildren().add(circle);
        square.setOnMouseClicked(this::diskPut);
        return square;
    }
    @FXML
    public void backToStart() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root=fxmlLoading(loader);
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Back to the Start Menu");
    }

    @FXML
    public void restartGame() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainGame.fxml"));
        Parent root=fxmlLoading(loader);
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Restarted");
    }

    @FXML
    public void previousModel() {
        model.undo();

        modelUpdate();
        Logger.info("Last move undone");
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
            modelUpdate();
            if (model.isGameOver()) {
                Logger.info("The Game is Over");
                Logger.info(("Scores: White:%s   Black: %s")
                        .formatted(numberOfWhiteDisks.getText(),numberOfBlackDisks.getText()));
                nextScene();
            }
        }
    }
    private void bindSetup(){
        currentPlayerDisk.fillProperty().bind(currentColorValue);
        numberOfWhiteDisks.textProperty().bind(whiteValue);
        numberOfBlackDisks.textProperty().bind(blackValue);
        modelUpdate();
    }
    private void modelUpdate(){
        currentColorValue.set(getOwnColor(model.currentColor()));
        whiteValue.set(String.valueOf(model.getWhiteNumber()));
        blackValue.set(String.valueOf(model.getBlackNumber()));
    }
    private void nextScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root=fxmlLoading(loader);
        EndScreenController endScreenController = loader.getController();
        endScreenController.setModel(model);
        Stage stage = (Stage) gameBoard.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    private Color getOwnColor(Colors color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case VALID -> Color.LIGHTSLATEGRAY;
            default -> Color.TRANSPARENT;
        };
    }

    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            Logger.error("Cannot load the FXML file"+"\n"+ e);
            throw new RuntimeException(e);
        }
    }
}

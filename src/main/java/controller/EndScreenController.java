package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Colors;
import model.EndGameState;
import model.GameModel;
import org.tinylog.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EndScreenController {
    @FXML
    public Label endWhiteDisks;
    @FXML
    public Label endBlackDisks;
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
                circle.setFill(getOwnColor(model.getDisk(row,col).getColor()));
                endBoard.add(circle,col,row);
            }
        }
        endWhiteDisks.setText(String.valueOf(model.getWhiteNumber()));
        endBlackDisks.setText(String.valueOf(model.getBlackNumber()));
        writeResult();
    }

    private Color getOwnColor(Colors color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case VALID -> Color.LIGHTSLATEGRAY;
            default -> Color.TRANSPARENT;
        };
    }

    private void writeResult()  {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        var filePath="results.json";
        var file = new File(filePath);
        List<EndGameState> endGameStates;
        if (file.exists()) {
            try {
                endGameStates = objectMapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException("Cannot read the JSON file: " + e.getMessage());
            }
        }
        else {
            endGameStates = new ArrayList<>();
        }

        try {
            String absolutePath = Paths.get("").toAbsolutePath().toString();
            String absoluteFilePath = absolutePath + File.separator + filePath;
            var writer = new FileWriter(absoluteFilePath);
            ArrayNode rootArrayNode = objectMapper.createArrayNode();
            for (EndGameState state : endGameStates) {
                ObjectNode stateNode = objectMapper.valueToTree(state);
                rootArrayNode.add(stateNode);
            }

            EndGameState newEndGameState = model.getEndGameState();
            ObjectNode newEndGameStateNode = objectMapper.valueToTree(newEndGameState);
            rootArrayNode.add(newEndGameStateNode);
            objectMapper.writeValue(writer, rootArrayNode);

        }catch (IOException e) {
            throw new RuntimeException("Cannot write JSON file: " + e.getMessage());
        }
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

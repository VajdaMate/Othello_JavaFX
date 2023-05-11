package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import javafx.scene.control.Button;
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
                circle.setFill(getColor(model.getDisk(row,col).getColor()));
                endBoard.add(circle,col,row);
            }
        }
        endWhiteDisks.setText(String.valueOf(model.getWhiteNumber()));
        endBlackDisks.setText(String.valueOf(model.getBlackNumber()));
        writeResult();
    }

    private void writeResult()  {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File("results.json");
        List<EndGameState> endGameStates;

        if (file.exists()) {
            try {
                // Read the existing JSON file and parse it as a List<EndGameState>
                endGameStates = objectMapper.readValue(file, new TypeReference<List<EndGameState>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Error reading existing JSON file: " + e.getMessage());
            }
        } else {
            // Create a new List<EndGameState> if the file doesn't exist
            endGameStates = new ArrayList<>();
        }

        try {
            FileWriter writer = new FileWriter("results.json");

            // Create a root JSON array node
            ArrayNode rootArrayNode = objectMapper.createArrayNode();

            // Add the existing endGameStates to the root array
            for (EndGameState state : endGameStates) {
                ObjectNode stateNode = objectMapper.valueToTree(state);
                rootArrayNode.add(stateNode);
            }

            // Add the new endGameState to the root array
            EndGameState newEndGameState = model.getEndGameState();
            ObjectNode newEndGameStateNode = objectMapper.valueToTree(newEndGameState);
            rootArrayNode.add(newEndGameStateNode);

            // Write the updated root array to the JSON file
            objectMapper.writeValue(writer, rootArrayNode);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON file: " + e.getMessage());
        }
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

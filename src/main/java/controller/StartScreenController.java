package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;



public class StartScreenController {

    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void startButtonPressed(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void prevGamesScene(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prevGames.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void clearHistory() {
        var fileName="results.json";
        String absolutePath = Path.of("").toAbsolutePath().toString();
        String absoluteFilePath = absolutePath + File.separator + fileName;
        var file = new File(absoluteFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}

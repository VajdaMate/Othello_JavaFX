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


    @FXML
    public void startButtonPressed(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainGame.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.info("Game Initialized");
    }
    @FXML
    public void prevGamesScene(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/prevGames.fxml"));
        Parent root = fxmlLoading(loader);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        Logger.debug("Loading previous Match results");
    }
    private Parent fxmlLoading(FXMLLoader loader){
        try {
            return loader.load();
        } catch (IOException e) {
            Logger.error("Cannot load the FXML file"+"\n"+ e);
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void clearHistory() {
        var fileName="results.json";
        String absolutePath = Path.of("").toAbsolutePath().toString();
        String absoluteFilePath = absolutePath + File.separator + fileName;
        var file = new File(absoluteFilePath);
        if (file.delete()) {
            Logger.info("Match history cleared");
        }
        else{
            Logger.debug("There was no match history to clear");
        }
    }
}

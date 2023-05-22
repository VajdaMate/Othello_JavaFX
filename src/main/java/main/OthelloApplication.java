package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class responsible for the first loading of the game.*/
public class OthelloApplication extends Application {
    /**
     * Loads the start screen of the game.
     * @param stage The base of the UI
     * @throws Exception I/O exception, not being able to find the fxml need to load the start screen
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Othello");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}

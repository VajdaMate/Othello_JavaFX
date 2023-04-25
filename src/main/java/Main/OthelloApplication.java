package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OthelloApplication extends Application {


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

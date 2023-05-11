package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import model.Colors;
import model.Disk;
import model.EndGameState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class prevGameController {


    @FXML
    public VBox mainBox;
    private List<EndGameState> endStates =new ArrayList<>();

    @FXML
    public void backToTheStart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("results.json");
        try {
            List<EndGameState> endGameStates = objectMapper.readValue(file, new TypeReference<List<EndGameState>>() {
            });
            this.endStates.addAll(endGameStates);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addBoards();
    }


    private void addBoards(){
        for (var states:endStates){
            var anchorPane=makeAnchor();
            var whiteLabel=makeWhiteLabel(states.getWhiteNumber());
            var blackLabel=makeBlackLabel(states.getBlackNumber());
            VBox vbox = new VBox();
            vbox.setMinSize(200,400);
            vbox.setStyle("-fx-background-color: #966F33;");
            vbox.getChildren().addAll(whiteLabel,blackLabel);
            var gridPane=makeGridPane(states.getBoard());
            HBox hbox=new HBox();
            hbox.getChildren().addAll(gridPane, vbox);
            AnchorPane.setTopAnchor(hbox, 0.0);
            AnchorPane.setRightAnchor(hbox, 0.0);
            AnchorPane.setBottomAnchor(hbox, 0.0);
            AnchorPane.setLeftAnchor(hbox, 0.0);
            anchorPane.getChildren().add(hbox);
            mainBox.getChildren().add(anchorPane);
        }
    }

    private AnchorPane makeAnchor(){
        int anchorWidth=600;
        int anchorHeight=400;
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #966F33;");
        anchorPane.setPrefSize(anchorWidth, anchorHeight);
        anchorPane.setPadding(new Insets(0, 0, 50, 0));
        return anchorPane;

    }

    private GridPane makeGridPane(Disk[][] board){
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefSize(400, 400);
        gridPane.setGridLinesVisible(true);
        gridPane.setStyle("-fx-background-color: #0BB346;");
        /*makeRows(gridPane);
        makeColumns(gridPane);*/
        addCircles(gridPane,board);
        return gridPane;
    }
    /*private void makeColumns(GridPane gridPane){
        for (int i = 0; i < gridPane.getColumnCount(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(javafx.scene.layout.Priority.NEVER);
            columnConstraints.setMinWidth(50);
            columnConstraints.setMaxWidth(Double.POSITIVE_INFINITY);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }*/

    /*private void makeRows(GridPane gridPane){
        for (int i = 0; i < gridPane.getRowCount(); i++) {;
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(50);
            rowConstraints.setMaxHeight(Double.POSITIVE_INFINITY);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.NEVER);
            gridPane.getRowConstraints().add(rowConstraints);
        }
    }*/

    private void addCircles(GridPane gridPane, Disk[][] board){
        for (var row=0;row<8;row++){
            for (var col = 0; col<8;col++){
                var circle = new Circle(25);
                circle.setFill(getColor(board[row][col].getColor()));
                gridPane.add(circle,col,row);
            }
        }
    }

    private Label makeWhiteLabel(int whiteNumber){
        Label whiteLabel = new Label("White disks: "+ whiteNumber);
        whiteLabel.setMinSize(200, 200);
        whiteLabel.setFont(new Font(40));
        whiteLabel.setTextFill(Color.WHITE);
        whiteLabel.setWrapText(true);
        whiteLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        //VBox.setMargin(whiteLabel, new Insets(50, 0, 50, 0));
        return whiteLabel;
    }
    private Label makeBlackLabel(int blackNumber){
        Label blackLabel = new Label("Black disks:"+ blackNumber);
        blackLabel.setMinSize(200, 200);
        blackLabel.setFont(new Font(40));
        blackLabel.setTextFill(Color.BLACK);
        blackLabel.setWrapText(true);
        blackLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        //VBox.setMargin(blackLabel, new Insets(50, 0, 50, 0));
        return blackLabel;
    }



    private Color getColor(Colors color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case VALID -> Color.LIGHTSLATEGRAY;
            default -> Color.TRANSPARENT;
        };
    }


}

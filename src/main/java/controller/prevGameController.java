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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import model.Colors;
import model.Disk;
import model.EndGameState;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class prevGameController {


    @FXML
    public VBox mainBox;
    private final List<EndGameState> endStates =new ArrayList<>();

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
        var fileName="results.json";
        try {
            var absolutePath= Path.of("").toAbsolutePath().toString();
            String absoluteFilePath = absolutePath + File.separator + fileName;
            var absolutFile=new File(absoluteFilePath);
            List<EndGameState> endGameStates = objectMapper.readValue(absolutFile, new TypeReference<>() {
            });
            this.endStates.addAll(endGameStates);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addBoards();
    }

    private void addBoards(){
        for (var state:endStates){
            var anchorPane=makePane(state);
            mainBox.getChildren().add(anchorPane);
        }
    }

    private AnchorPane makePane(EndGameState state){
        var anchorPane=makeAnchor();
        var whiteLabel=makeLabel(state.getWhiteNumber(),Color.WHITE);
        var blackLabel=makeLabel(state.getBlackNumber(),Color.BLACK);
        var vBox=makeVbox(whiteLabel,blackLabel);
        var gridPane=makeGridPane(state.getBoard());
        var hBox=setupHbox(gridPane,vBox);
        anchorPane.getChildren().add(hBox);
        return anchorPane;
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
    private Label makeLabel(int number, Color color){
        Label label = new Label(getColorName(color)+" disks: "+"\n"+ number);
        label.setMinSize(200, 200);
        label.setFont(new Font(29));
        label.setTextFill(color);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        return label;
    }
    private String getColorName(Color color) {
        String hexColor=color.toString();
        return switch (hexColor) {
            case "0x000000ff" -> "Black";
            case "0xffffffff" -> "White";
            default -> hexColor;
        };
    }

    private VBox makeVbox(Label whiteLabel,Label blackLabel){
        VBox vbox = new VBox();
        vbox.setMinSize(200,400);
        vbox.setStyle("-fx-background-color: #966F33;");
        vbox.getChildren().addAll(whiteLabel,blackLabel);
        return vbox;
    }
    private GridPane makeGridPane(Disk[][] board){
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefSize(400, 400);
        gridPane.setGridLinesVisible(true);
        gridPane.setStyle("-fx-background-color: #0BB346;");
        addCircles(gridPane,board);
        return gridPane;
    }
    private void addCircles(GridPane gridPane, Disk[][] board){
        for (var row=0;row<8;row++){
            for (var col = 0; col<8;col++){
                var circle = new Circle(25);
                circle.setFill(getOwnColor(board[row][col].getColor()));
                gridPane.add(circle,col,row);
            }
        }
    }
    private Color getOwnColor(Colors color) {
        return switch (color) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case VALID -> Color.LIGHTSLATEGRAY;
            default -> Color.TRANSPARENT;
        };
    }
    private HBox setupHbox(GridPane gridPane,VBox vbox){
        HBox hbox=new HBox();
        hbox.getChildren().addAll(gridPane, vbox);
        AnchorPane.setTopAnchor(hbox, 0.0);
        AnchorPane.setRightAnchor(hbox, 0.0);
        AnchorPane.setBottomAnchor(hbox, 0.0);
        AnchorPane.setLeftAnchor(hbox, 0.0);
        return hbox;
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    @FXML Pane definitionPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setTitle("Find Meaning");
        primaryStage.setScene(new Scene(root, 500, 500));
        List<String> params = getParameters().getRaw();
        ClientUtility.address = params.get(0);
        ClientUtility.port = Integer.parseInt(params.get(1));

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class ServerView extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serverDisplay.fxml"));
        primaryStage.setTitle("Find Meaning");
        primaryStage.setScene(new Scene(root, 600, 400));

        List<String> params = getParameters().getRaw();
        ServerUtility.port = Integer.parseInt(params.get(0));
        ServerUtility.filePath = params.get(1);
        primaryStage.show();


    }



}

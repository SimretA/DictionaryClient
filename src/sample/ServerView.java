package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serverDisplay.fxml"));
        primaryStage.setTitle("Find Meaning");
        primaryStage.setScene(new Scene(root, 600, 600));

        primaryStage.show();

    }


}

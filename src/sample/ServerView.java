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
    /**
     *Done by:
     *
     * Selam G/hiwot ATR/2042/09 - ghiwotselam@gmail.com
     * Segni Habulu ATR/6492/09 - segnih8@gmail.com
     * Simret Araya ATR/6579/09 - simretaraya7@gmail.com
     * Yeabsira Tesfaye ATR/7874/09 - tesfayeyeabsira0@gmail.com
     * Zekaryas Tadele ATR/8749/09 - zekaryasdinku@gmail.com
     *
     * Section 3
     * **/


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

package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

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

public class Main extends Application {
    @FXML Pane definitionPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setTitle("Find Meaning");
        primaryStage.setScene(new Scene(root, 500, 500));
        List<String> params = getParameters().getRaw();
        if(params.size() != 2){
            System.out.println("Invalid command line arguments, please provide Servers IP address and port");
            System.exit(1);
        }
        ClientUtility.address = params.get(0);

        try{
            ClientUtility.port = Integer.parseInt(params.get(1));
        }catch (NumberFormatException e){
            System.out.println("Invalid port number.");
            System.exit(1);
        }
        Client.getClient();

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Client.getClient().sendRequest(new SocketObject(null, "EXIT"));
        System.exit(0);
    }

    public static void main(String[] args) {

        launch(args);
    }
}

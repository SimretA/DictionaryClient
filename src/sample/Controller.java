package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;


public class Controller {
    @FXML Pane definitionPane;
    @FXML TextField wordInput;
    @FXML Text wordOutput;
    public void search(MouseEvent mouseEvent) {

        Client client = new Client("127.0.0.1",5000);
        SocketObject socketObject = new SocketObject(new Word(wordInput.getText(),null),"GET");
        try {
            client.sendRequest(socketObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //wordOutput.setText(wordInput.getText());
        definitionPane.setVisible(true);

    }
}

package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    @FXML Pane definitionPane;
    @FXML TextField wordInput;
    @FXML Text wordOutput;
    @FXML TextField word;
    @FXML TextArea definition;
    @FXML Label warning;

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

    public void openAddWordDialog(MouseEvent mouseEvent) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addWord.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Add Word");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void addWord(MouseEvent mouseEvent) {
        if(word.getText().trim().isEmpty() || definition.getText().trim().isEmpty()){
            warning.setVisible(true);


            return;
        }

        Client client = new Client("127.0.0.1",5000);
        SocketObject socketObject = new SocketObject(new Word(word.getText(),definition.getText()),"POST");
        try {
            client.sendRequest(socketObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

    }
    public void deleteWord(MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //TODO send request

        }

    }
}

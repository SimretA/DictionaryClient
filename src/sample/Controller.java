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
    @FXML Text meaningOutput;
    @FXML TextField word;
    @FXML TextArea definition;
    @FXML Label warning;

    private Word currentWord =null;

    public void search(MouseEvent mouseEvent) {

        Client client = new Client("127.0.0.1",5000);
        SocketObject socketObject = new SocketObject(new Word(wordInput.getText(),null),"GET");
        try {
            Word word = client.sendRequest(socketObject);
            currentWord = word;
            System.out.println(word.word +": "+word.meaning);
            wordOutput.setText(word.word.toUpperCase());
            meaningOutput.setText(word.meaning.toUpperCase());
            definitionPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //wordOutput.setText(wordInput.getText());


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
            Word word = client.sendRequest(socketObject);
            if(word.word.equals(socketObject.getWord().word)){
                System.out.println("WORD ADDED");//TODO add UI
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

    }
    public void deleteWord(MouseEvent mouseEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            SocketObject socketObject = new SocketObject(currentWord,"DELETE");
            Client client= new Client("127.0.0.1",5000);
            try {
                Word response = client.sendRequest(socketObject);
                if(response.word.equals(currentWord.word)){
                    System.out.println("word deleted");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word Deleted!",  ButtonType.OK);
                    alert1.showAndWait();
                    clearDefinitionPane();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private void clearDefinitionPane() {
        this.definitionPane.setVisible(false);
        this.wordInput.setText("");
    }


}

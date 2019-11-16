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
    @FXML Button deleteButton;

    private Word currentWord =null;


    public void search(MouseEvent mouseEvent) {

        Client client = new Client(ClientUtility.address, ClientUtility.port);
        SocketObject socketObject = new SocketObject(new Word(wordInput.getText(),null),"GET");
        try {
            SocketObject search_response = client.sendRequest(socketObject);
            currentWord = search_response.getWord();

            if(search_response.getMethod().equals("OK")){
                wordOutput.setText(currentWord.word.toUpperCase());
                meaningOutput.setText(currentWord.meaning.toUpperCase());
                deleteButton.setVisible(true);
            }
            else if(search_response.getMethod().equals("NotFound")){
                wordOutput.setText(currentWord.word.toUpperCase());
                meaningOutput.setText("Word not found".toUpperCase());
                deleteButton.setVisible(false);
            }

            definitionPane.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("something went wrong");
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

        Client client = new Client(ClientUtility.address, ClientUtility.port);
        SocketObject socketObject = new SocketObject(new Word(word.getText(),definition.getText()),"POST");
        try {
            SocketObject add_response = client.sendRequest(socketObject);
            if(add_response.getMethod().equals("OK")){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word successfully added",  ButtonType.OK);
                alert1.showAndWait();
            }
            else if(add_response.getMethod().equals("AlreadyExists")){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word already exists",  ButtonType.OK);
                alert1.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

    }
    public void deleteWord(MouseEvent mouseEvent){
        //can't delete not found words
        if(currentWord.meaning.equals("Not Found")){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word Doesn't exist",  ButtonType.OK);
            alert1.showAndWait();
            //clearDefinitionPane();
            return;

        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            SocketObject socketObject = new SocketObject(currentWord,"DELETE");
            Client client= new Client(ClientUtility.address, ClientUtility.port);
            try {
                SocketObject response = client.sendRequest(socketObject);
                if(response.getMethod().equals("OK")){
                    System.out.println("word deleted");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word Deleted!",  ButtonType.OK);
                    alert1.showAndWait();
                    clearDefinitionPane();
                }
                else if(response.getMethod().equals("NotFound")) {
                    System.out.println("word doesn't exist anymore");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Word doesn't exist anymore",  ButtonType.OK);
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

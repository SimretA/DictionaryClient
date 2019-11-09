package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class Controller {
    @FXML Pane definitionPane;
    @FXML TextField wordInput;
    @FXML Text wordOutput;
    public void search(MouseEvent mouseEvent) {

        //wordOutput.setText(wordInput.getText());
        definitionPane.setVisible(true);

    }
}

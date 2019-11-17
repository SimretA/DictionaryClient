package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ServerDisplay {
    @FXML TextArea infoDisplay;
    @FXML Button connectButton, disconnectButton;
    @FXML Text portText, fileText;
    Thread thread = null;
    WorkerThread workerThread = null;
    public void connectServer(MouseEvent mouseEvent) {
        workerThread = new WorkerThread(ServerUtility.port, infoDisplay);
        thread = new Thread(workerThread);
        thread.start();

        //thread.stop();


        connectButton.setDisable(true);
        disconnectButton.setDisable(false);


        portText.setText(String.valueOf(ServerUtility.port));
        fileText.setText(ServerUtility.filePath);




    }

    public void disconnectServer(MouseEvent mouseEvent) {

        System.exit(0);

    }
}
class WorkerThread extends Thread{
    private int port;
    private TextArea infoDisplay;
    public WorkerThread(int port, TextArea infoDisplay){
        this.infoDisplay = infoDisplay;
        this.port = port;
    }
    @Override
    public void run() {
        Server.startServer(this.port, this.infoDisplay);
    }

    public void disco(){
        Server.stopServer();

    }
}

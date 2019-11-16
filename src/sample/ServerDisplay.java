package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ServerDisplay {
    @FXML TextArea infoDisplay;
//    ServerDisplay(){
//        super();
//        Server server = new Server(5000, infoDisplay);
//    }
    public void connectServer(MouseEvent mouseEvent) {
        WorkerThread workerThread = new WorkerThread(5000, infoDisplay);
        Thread thread = new Thread(workerThread);
        thread.start();



    }

    public void disconnectServer(MouseEvent mouseEvent) {


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
}

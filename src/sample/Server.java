package sample;

import javafx.scene.control.TextArea;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private TextArea infoDisplay;

    public Server(int port){
        this.infoDisplay = infoDisplay;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at port " + port);
            //infoDisplay.setText(infoDisplay.getText() + "\n"+"Server started at port " + port);

            int i = 0;
            while(true) {

                socket = serverSocket.accept();
                System.out.println("Client connected");


                i++;
                Thread workerThread = new Thread(new ProccessRequest(socket,i));
                workerThread.start();



            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}

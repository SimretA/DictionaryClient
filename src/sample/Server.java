package sample;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream =  null;
    private ObjectInputStream objectInputStream =  null;
    private TextArea infoDisplay;

    public Server(int port){
        this.infoDisplay = infoDisplay;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at port " + port);
            //infoDisplay.setText(infoDisplay.getText() + "\n"+"Server started at port " + port);

            while(true) {

                socket = serverSocket.accept();
                System.out.println("Client connected");
                dataInputStream = new DataInputStream(
                        new BufferedInputStream(socket.getInputStream())
                );

                Thread workerThread = new Thread(new ProccessRequest(socket,1));
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

package sample;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Server server;

    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private static TextArea infoDisplay;

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at port " + port);
            infoDisplay.setText(infoDisplay.getText()+"\nServer started at port " + port);

            int i = 0;
            while(true) {

                socket = serverSocket.accept();
                infoDisplay.setText(infoDisplay.getText() + "\nRequest received");


                i++;
                Thread workerThread = new Thread(new ProccessRequest(socket,i, infoDisplay));
                workerThread.start();



            }
        }catch (Exception e){
            infoDisplay.setText(infoDisplay.getText()+"\nPort already in use");


        }
    }

    public static void  startServer(int port, TextArea infoDisplay) {


        Server.infoDisplay = infoDisplay;
        server = new Server(port);
    }

    public static void stopServer(){

        try {
            server.serverSocket.close();
        } catch (IOException e) {
            infoDisplay.setText(infoDisplay.getText()+"\nSomething went wrong");
        }

    }

//    public static void main(String[] args) {
//        Server server = new Server(5000);
//    }
}

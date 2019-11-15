package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream =  null;
    private ObjectInputStream objectInputStream =  null;

    public Server(int port){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started at port " + port);

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


    private void getMethod(Word word){

    }

    private void deleteMethod(Word word){

    }
    private void postMethod(Word word){

    }
    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}

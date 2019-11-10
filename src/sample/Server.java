package sample;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            socket = serverSocket.accept();
            System.out.println("Client connected");
            dataInputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream())
            );
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            SocketObject socketObject = (SocketObject)objectInputStream.readObject();
            System.out.println("Got Object method" + socketObject.getMethod() +" and word "+socketObject.getWord().word);
            socket.close();
            dataInputStream.close();

        }catch (Exception e){
            System.out.println(e);

        }
    }
    private void parseSocketObject(SocketObject socketObject){
        String method = socketObject.getMethod();
        switch (method){
            case "GET":
                //do stuff here
                break;
            case "POST":
                //do stuff here
                break;
            case "DELETE":
                //do stuff here
                break;
                default:
                    break;

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

package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    public List<SocketObject> requests;
    private static Client client = null;

    public static Client getClient() {
        if(client ==null){
            client = new Client(ClientUtility.address,ClientUtility.port);
            client.requests = new ArrayList<>();
        }
        return client;
    }

    private Client(String ipAddress, int port){
        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected");
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Server not reachable.",  ButtonType.OK);
            alert1.showAndWait();

        }

    }
    public SocketObject sendRequest(SocketObject socketObject) throws IOException, ClassNotFoundException {
        if(socketObject.getMethod().equals("EXIT")){
            objectOutputStream.writeObject(socketObject);
            objectOutputStream.flush();
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        }
        objectOutputStream.writeObject(socketObject);
        objectOutputStream.flush();
        SocketObject socketObject1 = (SocketObject) objectInputStream.readObject();
        //Word temp = socketObject1.getWord();

        return socketObject1;
       // objectOutputStream.close();


        //socket.close();
    }
    class ClientThread extends Thread{

        public ClientThread(Client client){

        }
    }

}

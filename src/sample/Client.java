package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    public Client(String ipAddress, int port){
        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected");
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Connection Error.",  ButtonType.OK);
            alert1.showAndWait();
        }

    }
    public SocketObject sendRequest(SocketObject socketObject) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(socketObject);
        objectOutputStream.flush();
        SocketObject socketObject1 = (SocketObject) objectInputStream.readObject();
        //Word temp = socketObject1.getWord();

        return socketObject1;
       // objectOutputStream.close();


        //socket.close();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",5000);
    }
}

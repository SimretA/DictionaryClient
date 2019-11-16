package sample;

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
            e.printStackTrace();
        }

    }
    public Word sendRequest(SocketObject socketObject) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(socketObject);
        objectOutputStream.flush();
        SocketObject socketObject1 = (SocketObject) objectInputStream.readObject();

        return socketObject1.getWord();
       // objectOutputStream.close();


        //socket.close();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",5000);
    }
}

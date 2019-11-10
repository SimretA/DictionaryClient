package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    public Client(String ipAddress, int port){
        try {
            socket = new Socket(ipAddress, port);
            System.out.println("Connected");
            dataInputStream = new DataInputStream(System.in);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendRequest(SocketObject socketObject) throws IOException {
        objectOutputStream.writeObject(socketObject);
        objectOutputStream.close();

        dataOutputStream.close();
        dataInputStream.close();
        socket.close();
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",5000);
    }
}

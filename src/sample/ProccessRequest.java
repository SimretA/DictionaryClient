package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.Socket;

public class ProccessRequest extends Thread {
    private Socket socket;
    private  int numnber;


    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;

    public ProccessRequest(Socket socket, int number){
        this.socket = socket;
        this.numnber = number;

    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            SocketObject socketObject = (SocketObject) objectInputStream.readObject();
            System.out.println("Got Object method" + socketObject.getMethod() + " and word " + socketObject.getWord().word);

            parseSocketObject(socketObject);
            this.socket.close();
            this.objectInputStream.close();

        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "File couldn't be accessed.",  ButtonType.OK);
            alert1.showAndWait();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void parseSocketObject(SocketObject socketObject) throws IOException, JSONException {
        String method = socketObject.getMethod();
        switch (method){
            case "GET":
                JSONObject dictionary = getDictionary();
                Word word= null;
                SocketObject response = null;
                try{
                     word = new Word(socketObject.getWord().word,dictionary.getString(socketObject.getWord().word));
                     response = new SocketObject(word, "OK");

                }catch(JSONException e){
                     word = new Word(socketObject.getWord().word,"");

                    response = new SocketObject(word, "NotFound");
                }
                finally {
                    System.out.println(word.word+": "+ word.meaning);
                    objectOutputStream.writeObject(response);
                    objectOutputStream.flush();
                    System.out.println("object sent");
                }



                break;
            case "POST":
                JSONObject dictionary1 = getDictionary();
                Word word1= socketObject.getWord();
                Word word_confirm_post;
                SocketObject response_post = null;
                try{
                    word_confirm_post= new Word(socketObject.getWord().word,dictionary1.getString(word1.word));
                    response_post = new SocketObject(word_confirm_post, "AlreadyExists");



                }catch (JSONException e){
                    response_post = new SocketObject(word1, "OK");
                    addWord(dictionary1,socketObject.getWord());


                }
                finally {
                    objectOutputStream.writeObject(response_post);
                    objectOutputStream.flush();
                }
                break;
            case "DELETE":
                JSONObject dictionary2 = getDictionary();
                Word word2 = socketObject.getWord();
                Word word_confirm_delete;
                SocketObject response_delete = null;
                try{
                    word_confirm_delete= new Word(word2.word,dictionary2.getString(word2.word));
                    deleteWord(dictionary2, word2);
                    response_delete = new SocketObject(word_confirm_delete, "OK");
                }
                catch (JSONException e){
                    response_delete= new SocketObject(word2, "NotFound");
                }
                finally {
                    objectOutputStream.writeObject(response_delete);
                    objectOutputStream.flush();
                }
                break;
            default:
                break;

        }
    }



    private JSONObject getDictionary() throws FileNotFoundException, JSONException {
        JSONTokener jsonTokener = new JSONTokener(new FileReader(ServerUtility.filePath));
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject;

    }
    private void saveDictionary(JSONObject jsonObject) throws IOException {
        FileWriter fileWriter =  null;
        fileWriter = new FileWriter(ServerUtility.filePath,false);
        fileWriter.write(jsonObject.toString());
        fileWriter.flush();
        fileWriter.close();



    }

    private synchronized void addWord(JSONObject jsonObject, Word word1) throws JSONException, IOException {
        jsonObject.put(word1.word, word1.meaning);
        saveDictionary(jsonObject);
        System.out.println("Word added");
    }
    private synchronized void deleteWord(JSONObject jsonObject, Word word) throws IOException {
        jsonObject.remove(word.word);
        saveDictionary(jsonObject);
        System.out.println("Word removed");
    }

}

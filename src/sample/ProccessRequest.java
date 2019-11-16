package sample;

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
            e.printStackTrace();
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
                Word word = new Word(socketObject.getWord().word,dictionary.getString(socketObject.getWord().word));
                System.out.println(word.word+": "+ word.meaning);
                //TODO check if word is found, send word to client
                objectOutputStream.writeObject(new SocketObject(word, "OK"));
                objectOutputStream.flush();
                System.out.println("object sent");


                break;
            case "POST":
                JSONObject dictionary1 = getDictionary();
                Word word1= socketObject.getWord();
                if(true){ //TODO Check if word doesn't exists
                    addWord(dictionary1,socketObject.getWord());
                    objectOutputStream.writeObject(new SocketObject(word1, "OK"));
                    objectOutputStream.flush();
                }
                else {
                    //TODO word already exists
                    // TODO Send response
                }
                break;
            case "DELETE":
                JSONObject dictionary2 = getDictionary();
                Word word2 = socketObject.getWord();
                if(true){//TODO check if word exists
                    deleteWord(dictionary2, word2);
                    objectOutputStream.writeObject(new SocketObject(word2, "OK"));
                    objectOutputStream.flush();
                }
                else{
                    //TODO do stuff
                }


                break;
            default:
                break;

        }
    }



    private JSONObject getDictionary() throws FileNotFoundException, JSONException {
        JSONTokener jsonTokener = new JSONTokener(new FileReader("/home/simret/IdeaProjects/DictionaryClient/src/sample/word.json"));
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject;

    }
    private void saveDictionary(JSONObject jsonObject) throws IOException {
        FileWriter fileWriter =  null;
        fileWriter = new FileWriter("/home/simret/IdeaProjects/DictionaryClient/src/sample/word.json",false);
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

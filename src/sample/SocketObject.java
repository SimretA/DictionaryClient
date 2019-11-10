package sample;

import java.io.Serializable;

public class SocketObject implements Serializable {
    private Word word;
    private String method;
    public SocketObject(Word word, String method){
        this.word = word;
        this.method = method;

    }

    public Word getWord() {
        return word;
    }

    public String getMethod() {
        return method;
    }
}

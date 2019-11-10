package sample;

import java.io.Serializable;

public class Word implements Serializable {
    public String word;
    public String meaning;
    public Word(String word, String meaning){
        this.word = word;
        this.meaning = meaning;
    }
}

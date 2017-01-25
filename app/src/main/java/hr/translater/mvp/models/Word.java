package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Igor on 25.1.2017..
 */
@Generated("org.jsonschema2pojo")
public class Word {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("word")
    @Expose
    private String word;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

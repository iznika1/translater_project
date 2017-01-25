package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Igor on 25.1.2017..
 */

@Generated("org.jsonschema2pojo")
public class WordResponse {

    @SerializedName("words")
    @Expose
    private List<Word> words;

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}

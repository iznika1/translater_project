package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Igor on 24.1.2017..
 */

@Generated("org.jsonschema2pojo")
public class TranslateData {

    @SerializedName("langFrom")
    @Expose
    private String langFrom;
    @SerializedName("langTo")
    @Expose
    private String langTo;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("translation")
    @Expose
    private String translation;

    public TranslateData() {
    }

    public TranslateData(String langFrom, String langTo, String word, String translation) {
        this.langFrom = langFrom;
        this.langTo = langTo;
        this.word = word;
        this.translation = translation;
    }

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }

    public String getLangTo() {
        return langTo;
    }

    public void setLangTo(String langTo) {
        this.langTo = langTo;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}

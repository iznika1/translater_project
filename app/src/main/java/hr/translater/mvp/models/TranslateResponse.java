package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Igor on 24.1.2017..
 */

@Generated("org.jsonschema2pojo")
public class TranslateResponse {

    @SerializedName("langFrom")
    @Expose
    private String langFrom;
    @SerializedName("langTo")
    @Expose
    private List<String> langToSet;


    /**
     *
     * @return
     * The langFrom
     */
    public String getLangFrom() {
        return langFrom;
    }

    /**
     *
     * @param langFrom
     * The langFrom
     */
    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }

    /**
     *
     * @return
     * The langToSet
     */
    public List<String> getLangToSet() {
        return langToSet;
    }

    /**
     *
     * @param langToSet
     * The langToSet
     */
    public void setLangToSet(List<String> langToSet) {
        this.langToSet = langToSet;
    }
}

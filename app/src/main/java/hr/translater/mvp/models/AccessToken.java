package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import javax.annotation.Generated;

import hr.translater.db.TranslaterDb;

/**
 * Created by Igor on 28.1.2017..
 */
@Generated("org.jsonschema2pojo")
@Table(database = TranslaterDb.class)
public class AccessToken extends BaseModel {

    @Column
    @PrimaryKey
    private int id;
    @SerializedName("accessToken")
    @Expose
    @Column
    private String accessToken;
    @SerializedName("grantType")
    @Expose
    @Column
    private String grantType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getGrantType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if (! Character.isUpperCase(grantType.charAt(0))) {
            grantType =
                    Character
                            .toString(grantType.charAt(0))
                            .toUpperCase() + grantType.substring(1);
        }

        return grantType;
    }
}
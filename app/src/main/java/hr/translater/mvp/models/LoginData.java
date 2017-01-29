package hr.translater.mvp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
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
public class LoginData extends BaseModel {

    @PrimaryKey
    @Column
    long uid;
    @SerializedName("username")
    @Expose
    @Column
    String username;
    @SerializedName("password")
    @Expose
    String password;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    AccessToken accessToken;

    public LoginData() {
    }

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginData(String username, String password, AccessToken accessToken) {
        this.username = username;
        this.password = password;
        this.accessToken = accessToken;
    }

    public long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}

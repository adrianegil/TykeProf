package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class Player {

    @ColumnInfo(name = "userName")
    @SerializedName("username")
    private String userName;

    @ColumnInfo(name = "fullName")
    @SerializedName("fullname")
    private String fullName;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email;

    public Player() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "id_user")
    @SerializedName("id")
    private long id_user;

    @ColumnInfo(name = "userName")
    @SerializedName("username")
    private String userName;

    @ColumnInfo(name = "fullName")
    @SerializedName("fullname")
    private String fullName;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email;

    @ColumnInfo(name = "password")
    @SerializedName("password")
    private String password;

    @ColumnInfo(name = "dob")
    @SerializedName("dob")
    private String dob;

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    private String gender;

    @ColumnInfo(name = "lang")
    @SerializedName("lang")
    private String lang;

    @Ignore
    @SerializedName("roles")
    List<Role> roles;

    @ColumnInfo(name = "loginAttempt", defaultValue = "0")
    @SerializedName("loginAttempt")
    Integer loginAttempt;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    String status;

    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    String image_url;

    public User() {
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(Integer loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}

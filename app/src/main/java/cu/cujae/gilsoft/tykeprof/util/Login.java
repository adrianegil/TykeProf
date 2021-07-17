package cu.cujae.gilsoft.tykeprof.util;

import com.google.gson.annotations.SerializedName;

//CLASE DE AYUDA PARA LA AUTENTICACIÓN
public class Login {

    @SerializedName("password")
    private String password;

    @SerializedName("userCredential")
    private String userCredential;

    public Login(String password, String userCredential) {
        this.password = password;
        this.userCredential = userCredential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(String userCredential) {
        this.userCredential = userCredential;
    }
}

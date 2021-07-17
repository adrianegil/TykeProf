package cu.cujae.gilsoft.tykeprof.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

//CLASE DE AYUDA PARA LA GESTIÓN DE TOKENS Y ALGUNOS DATOS DEL USUARIO
public class UserHelper {

    public static void saveToken(String token, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        return preferences.getString("token", "");
    }

    public static void saveUserLogin(Login login, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userCredential", login.getUserCredential());
        editor.putString("password", login.getPassword());
        editor.apply();
    }

    public static Login getUserLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        return new Login(preferences.getString("password", ""), preferences.getString("userCredential", ""));
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        return preferences.getBoolean("firstLaunch", true);
    }

    public static void changefirstLaunch(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("autenticacion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstLaunch", false);
        editor.apply();
    }
}

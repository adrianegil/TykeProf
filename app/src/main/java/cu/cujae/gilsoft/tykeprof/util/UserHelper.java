package cu.cujae.gilsoft.tykeprof.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.LoginActivity;
import cu.cujae.gilsoft.tykeprof.service.Login_Service;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//CLASE DE AYUDA PARA LA GESTIÓN DE TOKENS Y ALGUNOS DATOS DEL USUARIO
public class UserHelper {

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

    public static void renovateToken(Context context) {
        Login_Service login_service = RetrofitClient.getRetrofit().create(Login_Service.class);
        Login login = getUserLogin(context);
        Call<ResponseBody> userLoginCall = login_service.userLogin(login);
        userLoginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String token = null;
                    try {
                        token = response.body().string();
                        Log.e("Json Web Token Renovado", token);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Log.e("ERROR TOKEN:  ", e.getMessage());
                    }
                    saveToken(token, context);
                    Toast.makeText(context, context.getResources().getString(R.string.try_again_operation), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401 || response.code() == 500) {
                    Toast.makeText(context, context.getResources().getString(R.string.change_user_config), Toast.LENGTH_SHORT).show();
                    Activity activity = (Activity) context;
                    context.startActivity(new Intent(context, LoginActivity.class));
                    activity.finish();
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
}

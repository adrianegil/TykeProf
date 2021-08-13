package cu.cujae.gilsoft.tykeprof.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.service.Login_Service;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    public Login_Service login_service = RetrofitClient.getRetrofit().create(Login_Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    //CONDICIONA EL LANZAMIENTO DE LA APP
    public void init() {
        if (UserHelper.isFirstLaunch(this))
            firstLaunch();
        else
            normalLaunch();
    }

    //SE LANZA SI ES LA PRIMERA VEZ DEL USUARIO
    public void firstLaunch() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 500);
    }

    //SE LANZA SIEMPRE QUE EL USUARIO YA SE HAYA AUTENTICADO
    public void normalLaunch() {
        Login login = UserHelper.getUserLogin(SplashActivity.this);
        Call<ResponseBody> userLoginCall = login_service.userLogin(login);
        userLoginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String token = null;
                    try {
                        token = response.body().string();
                        Log.e("Json Web Token ", token);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Log.e("ERROR TOKEN:  ", e.getMessage());
                    }
                    UserHelper.saveToken(token, SplashActivity.this);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else if (response.code() == 401 || response.code() == 500) {
                    Toast.makeText(SplashActivity.this, getString(R.string.change_user_config), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ToastHelper.showCustomToast(SplashActivity.this, "error", "ERROR EN LA SOLICITUD");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("connected", false);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
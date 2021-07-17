package cu.cujae.gilsoft.tykeprof.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

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

public class LoginActivity extends AppCompatActivity {

    private Login_Service login_service = RetrofitClient.getRetrofit().create(Login_Service.class);
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //PRESIONAR DOS VECES ATRAS PARA SALIR
    @Override
    public void onBackPressed() {
        if (cont == 0) {
            Toast.makeText(LoginActivity.this, getString(R.string.exit_again_toast), Toast.LENGTH_SHORT).show();
            cont++;
        } else {
            super.onBackPressed();
        }
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                cont = 0;
            }
        }.start();
    }

    //AUTENTICACIÓN DEL PROFESOR EN LA PLATAFORMA TYKE
    public void logIn(View view) {
        MaterialCardView materialCardViewIncorrectLogin = findViewById(R.id.cardViewIncorrectLogin);
        materialCardViewIncorrectLogin.setVisibility(View.INVISIBLE);
        ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);
        // TextInputLayout textInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        //TextInputLayout textInputLayoutUserPassword = findViewById(R.id.textInputLayoutUserPassword);
        EditText editTextUserName = findViewById(R.id.editTextUserName);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String userName = editTextUserName.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        if (userName.isEmpty() || userPassword.isEmpty()) {
            if (userName.isEmpty())
                editTextUserName.setError(getString(R.string.required_user));
            if (userPassword.isEmpty())
                editTextPassword.setError(getString(R.string.required_password));
            ToastHelper.showCustomToast(LoginActivity.this, "warning", getString(R.string.must_fill_fields));
        } else {
            progressBarLogin.setVisibility(View.VISIBLE);
            Login login = new Login(userPassword, userName);
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
                           // Log.e("ERROR TOKEN:  ", e.getMessage());
                        }
                        UserHelper.saveToken(token, LoginActivity.this);
                        UserHelper.saveUserLogin(login, LoginActivity.this);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        ToastHelper.showCustomToast(LoginActivity.this, "success", getString(R.string.success_aut));
                        finish();
                    } else if (response.code() == 401 || response.code() == 500) {
                        progressBarLogin.setVisibility(View.INVISIBLE);
                        materialCardViewIncorrectLogin.setVisibility(View.VISIBLE);
                        //textInputLayoutUserName.setError("Error");
                        //textInputLayoutUserPassword.setError("Error");
                        editTextUserName.setError("Error");
                        editTextPassword.setError("Error");
                        editTextUserName.setText("");
                        editTextPassword.setText("");
                        ToastHelper.showCustomToast(LoginActivity.this, "error", getString(R.string.failed_aut));
                    } else {
                        ToastHelper.showCustomToast(LoginActivity.this, "error", "ERROR EN LA SOLICITUD");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, getResources().getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE).setAction("Ok", v -> {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                        // Snackbar.make(v, "Por favor conéctese", Snackbar.LENGTH_LONG).show();
                    }).show();
                }
            });
        }
    }

    //SALIR DE LA APP
    public void exitLoginActivity(View view) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setTitle(R.string.exit_confirm);
        dialog.setMessage(R.string.exit_confirm_description);
        dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
            finish();
        });

        dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
        dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
        dialog.show();
    }
}
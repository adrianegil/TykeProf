package cu.cujae.gilsoft.tykeprof.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.User_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Role;
import cu.cujae.gilsoft.tykeprof.data.entity.User;
import cu.cujae.gilsoft.tykeprof.service.Login_Service;
import cu.cujae.gilsoft.tykeprof.service.User_Service;
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
    private User_Service user_service = RetrofitClient.getRetrofit().create(User_Service.class);

    private int cont;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarLogin = findViewById(R.id.progressBarLogin);
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

    //AUTENTICACIÓN DEL USUARIO EN LA PLATAFORMA TYKE
    public void logIn(View view) {
        MaterialCardView materialCardViewIncorrectLogin = findViewById(R.id.cardViewIncorrectLogin);
        materialCardViewIncorrectLogin.setVisibility(View.INVISIBLE);
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
                        checkRole(login.getUserCredential());
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

    //REVISAR SI EL USUARIO ES PROFESOR
    public void checkRole(String username) {

        User_Dao user_dao = AppDatabase.getDatabase(this).user_dao();
        Call<User> getUser = user_service.getUserByWeb(username);
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    boolean isTeacher = false;
                    User user = response.body();
                    for (Role role : user.getRoles()) {
                        if (role.getId_role() == 2) {
                            isTeacher = true;
                            AppDatabase.databaseWriteExecutor.execute(() -> {
                                //  user_dao.deleteAll();
                                user_dao.saveUser(user);
                            });
                        }
                    }
                    goToMainActivity(isTeacher);
                    Log.e("User: ", user.getUserName() + " " + user.getFullName() + "" + user.getImage_url());
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(LoginActivity.this);

                } else
                    Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

    }

    //SI ES PROFESOR ACCEDE AL MENÚ PRINCIPAL
    public void goToMainActivity(boolean isTeacher) {
        if (isTeacher) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            progressBarLogin.setVisibility(View.INVISIBLE);
            ToastHelper.showCustomToast(LoginActivity.this, "error", getString(R.string.should_register_asteacher));
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
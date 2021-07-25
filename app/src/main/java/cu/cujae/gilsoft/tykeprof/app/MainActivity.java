package cu.cujae.gilsoft.tykeprof.app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration myAppBarConfiguration;
    Clue_Type_Service clue_type_service = RetrofitClient.getRetrofit().create(Clue_Type_Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //VERIFICA SI EL USUARIO SE AUTENTICÓ EN LA PLATAFORMA TYKE Y SI ES LA PRIMERA VEZ QUE LO HIZO
        if (getSharedPreferences("autenticacion", MODE_PRIVATE).getBoolean("firstLaunch", true))
            UserHelper.changefirstLaunch(this);

        String token = UserHelper.getToken(MainActivity.this);
        Login login = UserHelper.getUserLogin(MainActivity.this);
        Log.e("JWT en MainActivity ", token);
        Log.e("User Login ", login.getPassword() + "  " + login.getUserCredential());

        setSupportActionBar(binding.toolbarMainActivity);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,R.id.nav_clueTypeFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_account_item:
                Toast.makeText(this, "Datos de Usuario", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.exit_item:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.exit_confirm);
                dialog.setMessage(R.string.exit_confirm_description);
                dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
                    finish();
                });
                dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
                dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, myAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void deleteClueType(){

        String token = UserHelper.getToken(MainActivity.this);
        AppDatabase db = AppDatabase.getDatabase(this);
        Clue_Type_Dao clue_type_dao = db.clue_type_dao();
        Clue_Type clue_type = new Clue_Type("Para mejorar",20,12);
        clue_type.setId(6);


        Call<Clue_Type> callClueType = clue_type_service.deleteClueTypeByWeb("Bearer " + token,6);
        callClueType.enqueue(new Callback<Clue_Type>() {
            @Override
            public void onResponse(Call<Clue_Type> call, Response<Clue_Type> response) {

                if (response.isSuccessful()) {
                    Clue_Type clueType;
                    clueType = response.body();
                    Log.e("Clue Type ", clueType.getId() + " " + clueType.getType());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.deleteClueType(clueType);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Clue_Type> call, Throwable t) {
            }
        });
    }
}
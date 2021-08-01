package cu.cujae.gilsoft.tykeprof.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Insignia_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Professional_Rol_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.repository.Professional_Rol_Repository;
import cu.cujae.gilsoft.tykeprof.service.Career_Service;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Grant_Service;
import cu.cujae.gilsoft.tykeprof.service.Insignia_Service;
import cu.cujae.gilsoft.tykeprof.service.Professional_Rol_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Topic_Sevice;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration myAppBarConfiguration;
    Career_Service career_service = RetrofitClient.getRetrofit().create(Career_Service.class);
    Topic_Sevice topic_sevice = RetrofitClient.getRetrofit().create(Topic_Sevice.class);
    Professional_Rol_Service professional_rol_service = RetrofitClient.getRetrofit().create(Professional_Rol_Service.class);
    Insignia_Service insignia_service = RetrofitClient.getRetrofit().create(Insignia_Service.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Professional_Rol_Repository professional_rol_repository = new Professional_Rol_Repository(getApplication());
        //professional_rol_repository.getAllProfessionalRol();

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
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,
                R.id.nav_clueTypeFragment, R.id.nav_giftTypeFragment, R.id.nav_grantFragment, R.id.nav_giftFragment, R.id.nav_professionalRolFragment,
                R.id.nav_insigniaFragment, R.id.nav_rankingFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // getAllInsigniaWeb();
        //saveInsignia();
        // updateInsignia();
        // deleteInsignia();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

/*    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }*/

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

    public void getAllInsigniaWeb() {

        String token = UserHelper.getToken(this);
        Professional_Rol_Dao professional_rol_dao = AppDatabase.getDatabase(this).professional_rol_dao();
        Insignia_Dao insignia_dao = AppDatabase.getDatabase(this).insignia_dao();

        Call<List<Insignia>> listCallInsignia = insignia_service.getAllInsigniasByWeb("Bearer " + token);
        listCallInsignia.enqueue(new Callback<List<Insignia>>() {
            @Override
            public void onResponse(Call<List<Insignia>> call, Response<List<Insignia>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Professional_Rol> professional_rol = new ArrayList<>();

                    ArrayList<Insignia> insigniasSave = new ArrayList<>();
                    List<Insignia> insigniasResponse;
                    insigniasResponse = response.body();

                    for (Insignia insignia : insigniasResponse) {

                        insignia.setId_profess_rol(insignia.getProfessional_rol().getId_profess_rol());
                        professional_rol.add(insignia.getProfessional_rol());
                        insigniasSave.add(insignia);
                        Log.e("Insignia ", insignia.getId_insignia() + " " + insignia.getName() + "" + insignia.getProfessional_rol());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        professional_rol_dao.saveAllProfessionalRolList(professional_rol);
                        insignia_dao.deleteAll();
                        insignia_dao.saveAllInsignias(insigniasSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Insignia>> call, Throwable t) {
            }
        });

    }

    public void saveInsignia() {

        String token = UserHelper.getToken(this);
        Professional_Rol_Dao professional_rol_dao = AppDatabase.getDatabase(this).professional_rol_dao();
        Professional_Rol_Model professional_rol_model = new Professional_Rol_Model(1, 2, "Analista de Negocio");

        Insignia_Model insignia_model = new Insignia_Model(1, "Insignia Name Insert", 30, 60);

        Call<Integer> saveInsignia = insignia_service.saveInsigniaByWeb("Bearer " + token, insignia_model);
        saveInsignia.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {

                    getAllInsigniaWeb();
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateInsignia() {

        String token = UserHelper.getToken(this);
        Insignia_Dao insignia_dao = AppDatabase.getDatabase(this).insignia_dao();

        Insignia insignia = new Insignia();
        insignia.setId_insignia(5);
        insignia.setName("Insignia Update 2");
        insignia.setAdvance_points(30);
        insignia.setGrant_points(60);

        Professional_Rol professional_rol = new Professional_Rol();
        professional_rol.setId_profess_rol(21);
        insignia.setProfessional_rol(professional_rol);
        insignia.setId_profess_rol(21);

        Call<Insignia> callupdateInsignia = insignia_service.updateInsignialByWeb("Bearer " + token, insignia);
        callupdateInsignia.enqueue(new Callback<Insignia>() {
            @Override
            public void onResponse(Call<Insignia> call, Response<Insignia> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        insignia_dao.updateInsignia(insignia);
                    });
                    Toast.makeText(MainActivity.this, getString(R.string.edit_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Insignia> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteInsignia() {

        String token = UserHelper.getToken(this);
        Insignia_Dao insignia_dao = AppDatabase.getDatabase(this).insignia_dao();

        Call<ResponseBody> calldeleteInsignia = insignia_service.deleteInsigniaByWeb("Bearer " + token, 5);
        calldeleteInsignia.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        insignia_dao.deleteInsigniaByID(5);
                    });
                    Toast.makeText(MainActivity.this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


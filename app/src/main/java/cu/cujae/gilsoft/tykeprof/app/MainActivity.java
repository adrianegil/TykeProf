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
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Grant_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
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
    Grant_Service grant_service = RetrofitClient.getRetrofit().create(Grant_Service.class);

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
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,
                R.id.nav_clueTypeFragment,R.id.nav_giftTypeFragment,R.id.nav_grantFragment)
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

    public void deleteGrant(){

        String token = UserHelper.getToken(MainActivity.this);
        AppDatabase db = AppDatabase.getDatabase(this);
        Grant_Dao grant_dao = db.grant_dao();
        Grant grant = new Grant(15,"Test Update");
        grant.setId_grant(7);


        Call<ResponseBody> calldeleteGrant= grant_service.deleteGrantByWeb("Bearer " + token, grant.getId_grant());
        calldeleteGrant.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.deleteGrant(grant);
                    });
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void getAllGrantWeb(){

        String token = UserHelper.getToken(MainActivity.this);
        AppDatabase db = AppDatabase.getDatabase(this);
        Grant_Dao grant_dao = db.grant_dao();

        Call<List<Grant>> listCallGrant= grant_service.getAllGrantByWeb("Bearer " + token);
        listCallGrant.enqueue(new Callback<List<Grant>>() {
            @Override
            public void onResponse(Call<List<Grant>> call, Response<List<Grant>> response) {

                if(response.isSuccessful()){
                    List<Grant> grantList;
                    grantList = response.body();
                    for (Grant grant : grantList) {
                        Log.e("Grant ", grant.getId_grant() + " " + grant.getGrant_name());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.deleteAll();
                        grant_dao.saveAllGrant(grantList);
                    });
                }
                else if(response.code()==403){
                    UserHelper.renovateToken(MainActivity.this);
                    //getAllQuestionTypeWeb();
                }
                else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Grant>> call, Throwable t) {
            }
        });
    }

    public  void saveGrantWeb(){
        String token = UserHelper.getToken(MainActivity.this);
        AppDatabase db = AppDatabase.getDatabase(this);
        Grant_Dao grant_dao = db.grant_dao();
        Grant grant = new Grant(25,"Test");

        Call<Grant> saveGrantCall = grant_service.saveGrantByWeb("Bearer " + token, grant);
        saveGrantCall.enqueue(new Callback<Grant>() {
            @Override
            public void onResponse(Call<Grant> call, Response<Grant> response) {

                if(response.isSuccessful()){
                    Grant gran_response;
                    gran_response = response.body();
                    Log.e("Gift Type ", gran_response.getId_grant() + " " + gran_response.getGrant_name());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.saveGrant(gran_response);
                    });
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==403){
                    UserHelper.renovateToken(MainActivity.this);
                }
                else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Grant> call, Throwable t) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void updateGrantWeb(){
        String token = UserHelper.getToken(MainActivity.this);
        AppDatabase db = AppDatabase.getDatabase(this);
        Grant_Dao grant_dao = db.grant_dao();
        Grant grant = new Grant(15,"Test Update");
        grant.setId_grant(7);

        Call<Grant> updateGrantCall = grant_service.updateGrantByWeb("Bearer " + token, grant);
        updateGrantCall.enqueue(new Callback<Grant>() {
            @Override
            public void onResponse(Call<Grant> call, Response<Grant> response) {

                if(response.isSuccessful()){
                    Grant gran_response;
                    gran_response = response.body();
                    Log.e("Grant ", gran_response.getId_grant() + " " + gran_response.getGrant_name());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.updateGrant(gran_response);
                    });
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==403){
                    UserHelper.renovateToken(MainActivity.this);
                }
                else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Grant> call, Throwable t) {
                Toast.makeText(MainActivity.this,getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


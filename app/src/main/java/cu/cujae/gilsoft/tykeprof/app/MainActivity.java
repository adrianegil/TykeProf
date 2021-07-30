package cu.cujae.gilsoft.tykeprof.app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import cu.cujae.gilsoft.tykeprof.data.dao.Professional_Rol_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.Career_Service;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Grant_Service;
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
                R.id.nav_clueTypeFragment, R.id.nav_giftTypeFragment, R.id.nav_grantFragment, R.id.nav_giftFragment, R.id.nav_professionalRolFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //getAllCareerWeb();
        //getAllTopicWeb();
        // getAllProfessionalRolLWeb();
        // saveProfessionalRol();
        // deleteProfessionalRol();
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

    public void getAllCareerWeb() {

        String token = UserHelper.getToken(this);
        Career_Dao career_dao = AppDatabase.getDatabase(this).career_dao();

        Call<List<Career>> listCallCareer = career_service.getAllCareerByWeb("Bearer " + token);
        listCallCareer.enqueue(new Callback<List<Career>>() {
            @Override
            public void onResponse(Call<List<Career>> call, Response<List<Career>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Career> careersSave = new ArrayList<>();
                    List<Career> careerList;
                    careerList = response.body();

                    for (Career career : careerList) {
                        Career careerSave = new Career(career.getId_career(), career.getName(), career.getAcronyms());
                        careersSave.add(careerSave);

                        Log.e("Gift ", careerSave.getId_career() + " " + careerSave.getName() + "" + careerSave.getAcronyms());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        career_dao.deleteAll();
                        career_dao.saveAllCareer(careersSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Career>> call, Throwable t) {
            }
        });

    }

    public void getAllTopicWeb() {

        String token = UserHelper.getToken(this);
        Topic_Dao topic_dao = AppDatabase.getDatabase(this).topic_dao();

        Call<List<Topic>> listCallTopic = topic_sevice.getAllTopicByWeb("Bearer " + token);
        listCallTopic.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Topic> topicsSave = new ArrayList<>();
                    List<Topic> topicListResponse;
                    topicListResponse = response.body();

                    for (Topic topic : topicListResponse) {
                        Topic topicSave = new Topic(topic.getId_topic(), topic.getName(), topic.getDescrip());
                        topicsSave.add(topicSave);

                        Log.e("Gift ", topicSave.getId_topic() + " " + topicSave.getName() + "" + topicSave.getDescrip());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        topic_dao.deleteAll();
                        topic_dao.saveAllTopic(topicsSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
            }
        });

    }

    public void getAllProfessionalRolLWeb() {

        String token = UserHelper.getToken(this);
        Professional_Rol_Dao professional_rol_dao = AppDatabase.getDatabase(this).professional_rol_dao();
        Topic_Dao topic_dao = AppDatabase.getDatabase(this).topic_dao();
        Career_Dao career_dao = AppDatabase.getDatabase(this).career_dao();

        Call<List<Professional_Rol>> listCallProfessionalRol = professional_rol_service.getAllProfessionalRolByWeb("Bearer " + token);
        listCallProfessionalRol.enqueue(new Callback<List<Professional_Rol>>() {
            @Override
            public void onResponse(Call<List<Professional_Rol>> call, Response<List<Professional_Rol>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Career> careers = new ArrayList<>();
                    ArrayList<Topic> topics = new ArrayList<>();
                    ArrayList<Professional_Rol> professional_rolsSave = new ArrayList<>();
                    List<Professional_Rol> professional_rolsResponse;
                    professional_rolsResponse = response.body();

                    for (Professional_Rol professional_rol : professional_rolsResponse) {
                        professional_rolsSave.add(professional_rol);
                        careers.add(professional_rol.getCareer());
                        topics.add(professional_rol.getTopic());
                        Log.e("Gift ", professional_rol.getId_profess_rol() + " " + professional_rol.getName() + "" + professional_rol.getCareer() + " " + professional_rol.getTopic());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        career_dao.saveAllCareer(careers);
                        topic_dao.saveAllTopic(topics);
                        professional_rol_dao.deleteAll();
                        professional_rol_dao.saveAllProfessionalRol(professional_rolsSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Professional_Rol>> call, Throwable t) {
            }
        });

    }

    public void saveProfessionalRol() {

        String token = UserHelper.getToken(this);
        Professional_Rol_Dao professional_rol_dao = AppDatabase.getDatabase(this).professional_rol_dao();
        Professional_Rol_Model professional_rol_model = new Professional_Rol_Model(1, 2, "Analista de Negocio");

        Call<Professional_Rol> saveProfessionalRolCall = professional_rol_service.saveProfessionalRolByWeb("Bearer " + token, professional_rol_model);
        saveProfessionalRolCall.enqueue(new Callback<Professional_Rol>() {
            @Override
            public void onResponse(Call<Professional_Rol> call, Response<Professional_Rol> response) {
                if (response.isSuccessful()) {
                    Professional_Rol professional_rol;
                    professional_rol = response.body();
                    Log.e("Professional Rol ", professional_rol.getId_profess_rol() + " " + professional_rol.getName());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        professional_rol_dao.saveProfessionalRol(professional_rol);
                    });
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);
                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Professional_Rol> call, Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteProfessionalRol() {

        String token = UserHelper.getToken(this);
        Professional_Rol_Dao professional_rol_dao = AppDatabase.getDatabase(this).professional_rol_dao();

        Call<ResponseBody> calldeleteProfessionalRol = professional_rol_service.deleteProfessionalRolByWeb("Bearer " + token, 10);
        calldeleteProfessionalRol.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        professional_rol_dao.deleteProfessionalRolByID(10);
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


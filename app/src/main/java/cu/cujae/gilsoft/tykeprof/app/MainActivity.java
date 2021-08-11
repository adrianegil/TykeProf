package cu.cujae.gilsoft.tykeprof.app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.UserViewModel;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Answer_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Bonus_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Subject_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;
import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.Question_Service;
import cu.cujae.gilsoft.tykeprof.service.Subject_Service;
import cu.cujae.gilsoft.tykeprof.service.User_Service;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration myAppBarConfiguration;
    private NavController navController;
    private ImageView imageViewUser;
    private TextView textViewUserFullNameDrawer, textViewUserEmailDrawer;
    private View view1;

    Subject_Service subject_service = RetrofitClient.getRetrofit().create(Subject_Service.class);
    Question_Service question_service = RetrofitClient.getRetrofit().create(Question_Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //VERIFICANDO EN LA CONSOLA EL USUARIO Y EL TOKEN DE AUTORIZACIÓN
        String token = UserHelper.getToken(MainActivity.this);
        Login login = UserHelper.getUserLogin(MainActivity.this);
        Log.e("JWT en MainActivity ", token);
        Log.e("User Login ", login.getPassword() + "  " + login.getUserCredential());

        //CONFIGURACIÓN DE LA TOOLBAR CON NAVIGATION COMPONENT
        setSupportActionBar(binding.toolbarMainActivity);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,
                R.id.nav_clueTypeFragment, R.id.nav_giftTypeFragment, R.id.nav_grantFragment, R.id.nav_giftFragment, R.id.nav_professionalRolFragment,
                R.id.nav_insigniaFragment, R.id.nav_rankingFragment, R.id.nav_questionFragment)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //COMPROBANDO SI ES LA PRIMERA VEZ QUE EL USUARIO ENTRA EN LA APP
        if (getSharedPreferences("autenticacion", MODE_PRIVATE).getBoolean("firstLaunch", true)) {
            ProfessionalRolViewModel professionalRolViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProfessionalRolViewModel.class);
            ToastHelper.showCustomToast(MainActivity.this, "success", getString(R.string.success_aut));
            UserHelper.changefirstLaunch(this);
        }

        //ACTUALIZANDO INFORMACIÓN EN EL NAVIGATION DRAWER
        view1 = binding.navView.getHeaderView(0);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);
        textViewUserFullNameDrawer = view1.findViewById(R.id.textViewUserFullNameDrawer);
        textViewUserEmailDrawer = view1.findViewById(R.id.textViewUserEmailDrawer);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);

        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            textViewUserEmailDrawer.setText(user.getEmail());
            textViewUserFullNameDrawer.setText(user.getFullName());
            loadUserImage(user.getImage_url());
        });

        //VERIFICANDO SI EL USUARIO ESTA CONECTADO A LA RED
        if (!getIntent().getBooleanExtra("connected", true)) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE).setAction("Ok", v -> {
                //
            }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_item:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.exit_confirm);
                dialog.setMessage(R.string.exit_confirm_description);
                dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
                    finish();
                    // finishAffinity();
                });
                dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
                dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
                dialog.show();
                return true;
            default:
                NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment_container));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, myAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //CARGA LA IMAGEN DE USUARIO Y LA GUARDA EN CACHÉ
    public void loadUserImage(String url) {
        Picasso picasso = Picasso.get();
        //picasso.setIndicatorsEnabled(true);
        if (!url.isEmpty()) {
            picasso
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_gray)
                    // .resize(80,80)
                    //.centerCrop(Gravity.CENTER_VERTICAL)
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        } else {
            picasso
                    .load("url")
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        }
    }

    /*    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }*/

    public void getAllSubject() {

        Subject_Dao subject_dao = AppDatabase.getDatabase(this).subject_dao();
        Call<List<Subject>> listCallSubject = subject_service.getAllSubjectByWeb("Bearer " + UserHelper.getToken(this));
        listCallSubject.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Subject> subjectsSave = new ArrayList<>();
                    List<Subject> subjectsResponse;
                    subjectsResponse = response.body();
                    for (Subject subject : subjectsResponse) {
                        subjectsSave.add(subject);
                        Log.e("Subject ", subject.getId_subject() + " " + subject.getName() + " " + subject.isActive());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        subject_dao.deleteAll();
                        subject_dao.saveAllSubject(subjectsSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
            }
        });
    }

    public void getAllQuestions() {

        Question_Dao question_dao = AppDatabase.getDatabase(this).question_dao();
        Answer_Dao answer_dao = AppDatabase.getDatabase(this).answer_dao();
        Bonus_Dao bonus_dao = AppDatabase.getDatabase(this).bonus_dao();
        Clue_Dao clue_dao = AppDatabase.getDatabase(this).clue_dao();

        Call<List<Question>> listCallQuestions = question_service.getAllQuestionsByWeb("Bearer " + UserHelper.getToken(MainActivity.this));
        listCallQuestions.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Answer> answers = new ArrayList<>();
                    ArrayList<Bonus> bonuses = new ArrayList<>();
                    ArrayList<Clue> clues = new ArrayList<>();

                    ArrayList<Question> questionsSave = new ArrayList<>();
                    List<Question> questionsResponse;
                    questionsResponse = response.body();

                    for (Question question : questionsResponse) {
                        question.setId_question_type(question.getQuestion_type().getId());
                        questionsSave.add(question);
                        for (Bonus bonus : question.getBonusList()) {
                            bonus.setId_question(question.getId_question());
                            bonuses.add(bonus);
                        }
                        for (Answer answer : question.getAnswerList()) {
                            answer.setId_question((int) question.getId_question());
                            answers.add(answer);
                        }
                        for (Clue clue : question.getClueList()) {
                            clue.setId_question((int) question.getId_question());
                            clue.setId_clue_type(clue.getClue_type().getId());
                            clues.add(clue);
                        }
                        Log.e("Question ", question.getId_question() + " " + question.getQuestion_title() + "" + question.getPoints_complete_correctly() + " " + question.getTime_second());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_dao.deleteAll();
                        question_dao.saveAllQuestionList(questionsSave);
                        bonus_dao.saveAllBonuses(bonuses);
                        answer_dao.saveAllAnswerList(answers);
                        clue_dao.saveAllClueList(clues);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
            }
        });
    }

    public void deleteQuestionByID(long id) {

        Question_Dao question_dao = AppDatabase.getDatabase(this).question_dao();
        Call<Question> listCallQuestions = question_service.deleteQuestionsByWeb("Bearer " + UserHelper.getToken(MainActivity.this), id);
        listCallQuestions.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    Question question = response.body();
                    Log.e("Question ", question.getId_question() + " " + question.getQuestion_title() + "" + question.getPoints_complete_correctly() + " " + question.getTime_second());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_dao.deleteQuestionByID(id);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
            }
        });

    }

}


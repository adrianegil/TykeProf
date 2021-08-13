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
import cu.cujae.gilsoft.tykeprof.data.dao.Group_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Strategy_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Subject_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Teacher_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;
import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Topic;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.GroupList_Model;
import cu.cujae.gilsoft.tykeprof.data.model.QuestionID_Model;
import cu.cujae.gilsoft.tykeprof.data.model.TopicList_Model;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.Question_Service;
import cu.cujae.gilsoft.tykeprof.service.Strategy_Service;
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
    Strategy_Service strategy_service = RetrofitClient.getRetrofit().create(Strategy_Service.class);

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
                R.id.nav_insigniaFragment, R.id.nav_rankingFragment, R.id.nav_questionFragment, R.id.nav_strategyFragment)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


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

        // getAllStrategies();
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

    public void getAllStrategies() {

        Strategy_Dao strategy_dao = AppDatabase.getDatabase(this).strategy_dao();
        Topic_Dao topic_dao = AppDatabase.getDatabase(this).topic_dao();
        Group_Dao group_dao = AppDatabase.getDatabase(this).group_dao();
        Teacher_Dao teacher_dao = AppDatabase.getDatabase(this).teacher_dao();


        Call<List<Strategy>> listCallQuestions = strategy_service.getAllStrategiesByWeb("Bearer " + UserHelper.getToken(MainActivity.this));
        listCallQuestions.enqueue(new Callback<List<Strategy>>() {
            @Override
            public void onResponse(Call<List<Strategy>> call, Response<List<Strategy>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Topic> topics = new ArrayList<>();
                    ArrayList<Group> groups = new ArrayList<>();
                    ArrayList<Teacher> teachers = new ArrayList<>();
                    ArrayList<Strategy_Topic> strategy_topics = new ArrayList<>();
                    ArrayList<Strategy_Group> strategy_groups = new ArrayList<>();
                    ArrayList<Strategy_Question> strategy_questions = new ArrayList<>();

                    ArrayList<Strategy> strategiesSave = new ArrayList<>();
                    List<Strategy> strategiesResponse;
                    strategiesResponse = response.body();

                    for (Strategy strategy : strategiesResponse) {
                        strategy.setId_teacher(strategy.getTeacher().getId_teacher());
                        strategiesSave.add(strategy);
                        Teacher teacher = strategy.getTeacher();
                        teacher.setFullName(strategy.getTeacher().getUser().getFullName());
                        teachers.add(teacher);
                        for (GroupList_Model group : strategy.getGroupList_models()) {
                            groups.add(group.getGroup());
                            strategy_groups.add(new Strategy_Group(group.getGroup().getId_group(), strategy.getId_strategy()));
                        }
                        for (TopicList_Model topic : strategy.getTopicList_models()) {
                            topics.add(topic.getTopic());
                            strategy_topics.add(new Strategy_Topic(topic.getTopic().getId_topic(), strategy.getId_strategy()));
                        }
                        for (QuestionID_Model questionID_model : strategy.getQuestionID_models()) {
                            strategy_questions.add(new Strategy_Question(questionID_model.getId_Question(), strategy.getId_strategy()));
                        }
                        Log.e("Strategy ", strategy.getId_strategy() + " " + strategy.getName() + " " + strategy.getDate() + " " + strategy.getPoints());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        strategy_dao.deleteAll();
                        topic_dao.saveAllTopic(topics);
                        group_dao.saveAllGroups(groups);
                        strategy_dao.saveStrategy_GroupList(strategy_groups);
                        strategy_dao.saveStrategy_TopicList(strategy_topics);
                        strategy_dao.saveStrategy_QuestionList(strategy_questions);
                        teacher_dao.saveAllTeacher(teachers);
                        strategy_dao.saveAllStrategiesList(strategiesSave);
                    });
                    Toast.makeText(MainActivity.this, "onResponse", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(MainActivity.this);

                } else
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Strategy>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();

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


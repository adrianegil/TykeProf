package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Group_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Strategy_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Teacher_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Topic;
import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.GroupList_Model;
import cu.cujae.gilsoft.tykeprof.data.model.QuestionID_Model;
import cu.cujae.gilsoft.tykeprof.data.model.TopicList_Model;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitQuestions;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitTopics;
import cu.cujae.gilsoft.tykeprof.service.Strategy_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Strategy_Repository {

    private Strategy_Dao strategy_dao;
    private Topic_Dao topic_dao;
    private Group_Dao group_dao;
    private Teacher_Dao teacher_dao;
    private final AppDatabase db;
    private Strategy_Service strategy_service;
    private Context context;

    public Strategy_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.strategy_dao = db.strategy_dao();
        this.topic_dao = db.topic_dao();
        this.group_dao = db.group_dao();
        this.teacher_dao = db.teacher_dao();
        this.strategy_service = RetrofitClient.getRetrofit().create(Strategy_Service.class);
    }

    public LiveData<List<Strategy>> getAllStrategies() {
        Call<List<Strategy>> listCallQuestions = strategy_service.getAllStrategiesByWeb("Bearer " + UserHelper.getToken(context));
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
                        teacher_dao.deleteAll();
                        teacher_dao.saveAllTeacher(teachers);
                        topic_dao.saveAllTopic(topics);
                        group_dao.deleteAll();
                        group_dao.saveAllGroups(groups);
                        strategy_dao.saveStrategy_TopicList(strategy_topics);
                        strategy_dao.saveStrategy_QuestionList(strategy_questions);
                        strategy_dao.saveStrategy_GroupList(strategy_groups);
                        strategy_dao.saveAllStrategiesList(strategiesSave);
                    });

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Strategy>> call, Throwable t) {
            }
        });

        return strategy_dao.getAllStrategies();
    }

    public LiveData<List<Strategy>> getAllStrategiesList() {
        Call<List<Strategy>> listCallQuestions = strategy_service.getAllStrategiesByWeb("Bearer " + UserHelper.getToken(context));
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
                        //strategy_dao.deleteAll();
                        teacher_dao.saveAllTeacher(teachers);
                        topic_dao.saveAllTopic(topics);
                        group_dao.deleteAll();
                        group_dao.saveAllGroups(groups);
                        strategy_dao.saveStrategy_TopicList(strategy_topics);
                        strategy_dao.saveStrategy_QuestionList(strategy_questions);
                        strategy_dao.saveStrategy_GroupList(strategy_groups);
                        strategy_dao.saveAllStrategiesList(strategiesSave);
                    });

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Strategy>> call, Throwable t) {
            }
        });

        return strategy_dao.getAllStrategies();
    }

    public void deleteStrategyByID(long id) {

        Call<ResponseBody> deleteStrategy = strategy_service.deleteStrategyByWeb("Bearer " + UserHelper.getToken(context), id);
        deleteStrategy.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        strategy_dao.deleteStrategyByID(id);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public long getStrategiesListSize() {
        return getAllStrategiesLocalList().size();
    }

    public List<Strategy> getAllStrategiesLocalList() {
        return strategy_dao.getAllStrategiesList();
    }

    public StrategyWhitQuestions getStrategyWhitQuestionsByStrategyID(long id) {
        return strategy_dao.getStrategyWhitQuestions(id);
    }

    public StrategyWhitTopics getStrategyWhitTopicsByStrategyID(long id) {
        return strategy_dao.getStrategyWhitTopics(id);
    }

}

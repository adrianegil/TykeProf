package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Answer_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Bonus_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Subject_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;
import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.service.Question_Service;
import cu.cujae.gilsoft.tykeprof.service.Subject_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Question_Repository {

    private Question_Dao question_dao;
    private Answer_Dao answer_dao;
    private Bonus_Dao bonus_dao;
    private Clue_Dao clue_dao;
    private final AppDatabase db;
    private Question_Service question_service;
    private Context context;

    public Question_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.question_dao = db.question_dao();
        this.answer_dao = db.answer_dao();
        this.bonus_dao = db.bonus_dao();
        this.clue_dao = db.clue_dao();
        this.question_service = RetrofitClient.getRetrofit().create(Question_Service.class);
    }

    public LiveData<List<Question>> getAllQuestionList() {

        Call<List<Question>> listCallQuestions = question_service.getAllQuestionsByWeb("Bearer " + UserHelper.getToken(context));
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
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
            }
        });
        return question_dao.getAllQuestions();
    }

    public long getQuestionListSize() {
        long size;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Call<List<Question>> listCallQuestions = question_service.getAllQuestionsByWeb("Bearer " + UserHelper.getToken(context));
        try {
            size = listCallQuestions.execute().body().size();
        } catch (IOException e) {
            size = getAllQuestionLocalList().size();
        }
        return size;
    }

    public List<Question> getAllQuestionLocalList() {
        return question_dao.getAllQuestionList();
    }

    public void deleteQuestionByID(long id) {
        Call<Question> listCallQuestions = question_service.deleteQuestionsByWeb("Bearer " + UserHelper.getToken(context), id);
        listCallQuestions.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    Question question = response.body();
                    Log.e("Question ", question.getId_question() + " " + question.getQuestion_title() + " " + question.getPoints_complete_correctly() + " " + question.getTime_second());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_dao.deleteQuestionByID(id);
                    });
                    Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

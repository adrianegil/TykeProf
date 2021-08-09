package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Question_Type_Repository {

    private Question_Type_Dao question_type_dao;
    private LiveData<List<Question_Type>> allQuestionType;
    private final AppDatabase db;
    private Question_Type_Service question_type_service;
    private Context context;

    public Question_Type_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.question_type_dao = db.question_type_dao();
        this.question_type_service = RetrofitClient.getRetrofit().create(Question_Type_Service.class);
        this.allQuestionType = getAllQuestionTypeWeb();
    }

    public LiveData<List<Question_Type>> getAllQuestionTypeWeb() {
        Call<List<Question_Type>> listCallQuestionType = question_type_service.getAllQuestionTypeByWeb("Bearer " + UserHelper.getToken(context));
        listCallQuestionType.enqueue(new Callback<List<Question_Type>>() {
            @Override
            public void onResponse(Call<List<Question_Type>> call, Response<List<Question_Type>> response) {

                if(response.isSuccessful()){
                    List<Question_Type> question_typeList;
                    question_typeList = response.body();
                    for (Question_Type question_type : question_typeList) {
                        Log.e("Question Type ", question_type.getId() + " " + question_type.getType());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_type_dao.deleteAll();
                        question_type_dao.saveAllQuestionType(question_typeList);
                    });
                }
                else if(response.code()==403){
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                }
                else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Question_Type>> call, Throwable t) {
            }
        });

        return question_type_dao.getAllQuestionType();
    }

    public void saveQuestionType(Question_Type question_type){
        Call<Question_Type> saveQuestionTypeCall = question_type_service.saveQuestionTypeByWeb("Bearer " + UserHelper.getToken(context), question_type);
        saveQuestionTypeCall.enqueue(new Callback<Question_Type>() {
            @Override
            public void onResponse(Call<Question_Type> call, Response<Question_Type> response) {

                if(response.isSuccessful()){
                    Question_Type question_type_response;
                    question_type_response = response.body();
                    Log.e("Question Type Response ", question_type_response.getId() + " " + question_type_response.getType());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_type_dao.saveQuestionType(question_type_response);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                else if (response.code()==403){
                    UserHelper.renovateToken(context);
                   // saveQuestionType(question_type);
                }
                else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Question_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void  deleteQuestionType(Question_Type question_type){
        Call<Question_Type> deleteQuestionTypeCall = question_type_service.deleteQuestionTypeByWeb("Bearer " + UserHelper.getToken(context), question_type.getId());
        deleteQuestionTypeCall.enqueue(new Callback<Question_Type>() {
            @Override
            public void onResponse(Call<Question_Type> call, Response<Question_Type> response) {

                if(response.isSuccessful()){
                    Question_Type question_type_response;
                    question_type_response = response.body();
                    Log.e("Question Type Delete ", question_type_response.getId() + " " + question_type_response.getType());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        question_type_dao.deleteQuestionType(question_type_response);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                }
                else if(response.code()==403){
                    UserHelper.renovateToken(context);
                    //deleteQuestionType(question_type);
                }
                else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Question_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public LiveData<List<Question_Type>> getAllQuestionType() {
        return allQuestionType;
    }
}

package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.snackbar.Snackbar;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.model.Strategy_Model;
import cu.cujae.gilsoft.tykeprof.data.model.TeacherSubjectsModel;
import cu.cujae.gilsoft.tykeprof.repository.Strategy_Repository;
import cu.cujae.gilsoft.tykeprof.repository.User_Repository;
import cu.cujae.gilsoft.tykeprof.service.Teacher_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeNewStrategyViewModel extends AndroidViewModel {

    private Strategy_Repository strategy_repository;
    private User_Repository user_repository;
    public TeacherSubjectsModel teacherSubjectsModel;
    private Teacher_Service teacher_service;
    private Context context;
    private long userId;

    public HomeNewStrategyViewModel(@NonNull Application application) {
        super(application);
        this.strategy_repository = new Strategy_Repository(application);
        this.user_repository = new User_Repository(application);
        this.teacher_service = RetrofitClient.getRetrofit().create(Teacher_Service.class);
        this.context = application;
    }

    public void saveStrategy(Strategy_Model strategy_model) {
        strategy_repository.saveStrategy(strategy_model);
    }

    public void getTeacherSubjectsModel(View view) {
        userId = user_repository.getUserIdByUserName(UserHelper.getUserLogin(context).getUserCredential());
        Call<TeacherSubjectsModel> teacherSubjectsModelCall = teacher_service.getTeacherIdWhitSubjectsByUserId("Bearer " + UserHelper.getToken(context), userId);
        teacherSubjectsModelCall.enqueue(new Callback<TeacherSubjectsModel>() {
            @Override
            public void onResponse(Call<TeacherSubjectsModel> call, Response<TeacherSubjectsModel> response) {
                if (response.isSuccessful()) {
                    teacherSubjectsModel = response.body();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<TeacherSubjectsModel> call, Throwable t) {
                Snackbar.make(view, context.getResources().getString(R.string.no_connection), Snackbar.LENGTH_LONG).setAction("Ok", v -> {
                    Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                }).show();
            }
        });
    }

}
package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Subject_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.service.Subject_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Subject_Repository {

    private Subject_Dao subject_dao;
    private final AppDatabase db;
    private Subject_Service subject_service;
    private Context context;

    public Subject_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.subject_dao = db.subject_dao();
        this.subject_service = RetrofitClient.getRetrofit().create(Subject_Service.class);
    }

    public List<Subject> getAllSubjectList() {
        Call<List<Subject>> listCallSubject = subject_service.getAllSubjectByWeb("Bearer " + UserHelper.getToken(context));
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
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
            }
        });
        return subject_dao.getAllSubjectList();
    }

    public List<Subject> getAllSubjectLocalList() {
        return subject_dao.getAllSubjectList();
    }

    public LiveData<List<Subject>> getLiveSubjectLocalList() {
        return subject_dao.getLiveSubjectList();
    }

}

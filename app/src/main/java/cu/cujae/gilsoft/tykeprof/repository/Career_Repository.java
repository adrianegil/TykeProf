package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.service.Career_Service;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Career_Repository {

    private Career_Dao career_dao;
    private final AppDatabase db;
    private Career_Service career_service;
    private Context context;
    private String token;

    public Career_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.career_dao = db.career_dao();
        this.career_service = RetrofitClient.getRetrofit().create(Career_Service.class);
        this.token = UserHelper.getToken(application);
    }

    public List<Career> getAllCareerList() {

        Call<List<Career>> listCallCareer = career_service.getAllCareerByWeb("Bearer " + token);
        listCallCareer.enqueue(new Callback<List<Career>>() {
            @Override
            public void onResponse(Call<List<Career>> call, Response<List<Career>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Career> careersSave = new ArrayList<>();
                    List<Career> careerList;
                    careerList = response.body();

                    for (Career career : careerList) {
                        Career careerSave = new Career(career.getId_career(),career.getName(),career.getAcronyms());
                        careersSave.add(careerSave);

                        Log.e("Career ", careerSave.getId_career() + " " + careerSave.getName() + "" + careerSave.getAcronyms() );
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        career_dao.deleteAll();
                        career_dao.saveAllCareer(careersSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Career>> call, Throwable t) {
            }
        });
        return career_dao.getAllCareerList();
    }

    public List<Career> getAllCareerLocalList() {
        return career_dao.getAllCareerList();
    }

    public Career getCareerbyId(long id) {
        return career_dao.getCareerById(id);
    }

}

package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Punctuation_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;
import cu.cujae.gilsoft.tykeprof.service.Punctuation_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Punctuation_Repository {

    private Punctuation_Dao punctuation_dao;
    private final AppDatabase db;
    private Punctuation_Service punctuation_service;
    private Context context;

    public Punctuation_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.punctuation_dao = db.punctuation_dao();
        this.punctuation_service = RetrofitClient.getRetrofit().create(Punctuation_Service.class);
    }

    public LiveData<List<Punctuation>> getAllPunctuations() {
        Call<List<Punctuation>> listCall = punctuation_service.getAllPunctuationByWeb("Bearer " + UserHelper.getToken(context));
        listCall.enqueue(new Callback<List<Punctuation>>() {
            @Override
            public void onResponse(Call<List<Punctuation>> call, Response<List<Punctuation>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Punctuation> punctuationsSave = new ArrayList<>();
                    List<Punctuation> punctuationsResponse;
                    punctuationsResponse = response.body();
                    for (Punctuation punctuation : punctuationsResponse) {
                        punctuationsSave.add(punctuation);
                        Log.e("Punctuation ", punctuation.getId_punctuation() + " " + punctuation.getPunctuation() + " " + punctuation.getPlayer().getUserName());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        punctuation_dao.saveAllPunctuations(punctuationsSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Punctuation>> call, Throwable t) {
            }
        });
        return punctuation_dao.getAllPunctuations();
    }

    public List<Punctuation> getPunctuationLocalListAsc() {
        return punctuation_dao.getPunctuationListAsc();
    }

    public List<Punctuation> getPunctuationLocalListDesc() {
        return punctuation_dao.getPunctuationListDesc();
    }
}

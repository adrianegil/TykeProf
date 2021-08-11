package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Clue_Type_Repository {

    private Clue_Type_Dao clue_type_dao;
    private final AppDatabase db;
    private Clue_Type_Service clue_type_service;
    private Context context;

    public Clue_Type_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.clue_type_dao = db.clue_type_dao();
        this.clue_type_service = RetrofitClient.getRetrofit().create(Clue_Type_Service.class);
    }

    public LiveData<List<Clue_Type>> getAllClueTypeWeb() {
        Call<List<Clue_Type>> listCallClueType = clue_type_service.getAllClueTypeByWeb("Bearer " + UserHelper.getToken(context));
        listCallClueType.enqueue(new Callback<List<Clue_Type>>() {
            @Override
            public void onResponse(Call<List<Clue_Type>> call, Response<List<Clue_Type>> response) {

                if (response.isSuccessful()) {
                    List<Clue_Type> clue_typeList;
                    clue_typeList = response.body();
                    for (Clue_Type clue_type : clue_typeList) {
                        Log.e("Clue Type ", clue_type.getId() + " " + clue_type.getType());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.deleteAll();
                        clue_type_dao.saveAllClueType(clue_typeList);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Clue_Type>> call, Throwable t) {
            }
        });
        return clue_type_dao.getAllClueType();
    }

    public List<Clue_Type> getAllClueTypeList() {
        Call<List<Clue_Type>> listCallClueType = clue_type_service.getAllClueTypeByWeb("Bearer " + UserHelper.getToken(context));
        listCallClueType.enqueue(new Callback<List<Clue_Type>>() {
            @Override
            public void onResponse(Call<List<Clue_Type>> call, Response<List<Clue_Type>> response) {

                if (response.isSuccessful()) {
                    List<Clue_Type> clue_typeList;
                    clue_typeList = response.body();
                    for (Clue_Type clue_type : clue_typeList) {
                        Log.e("Clue Type ", clue_type.getId() + " " + clue_type.getType());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.saveAllClueTypeList(clue_typeList);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Clue_Type>> call, Throwable t) {
            }
        });
        return clue_type_dao.getAllClueTypeList();
    }

    public Clue_Type getClueTypeByID(long id) {
        Call<Clue_Type> callClueType = clue_type_service.getClueTypeByIdByWeb("Bearer " + UserHelper.getToken(context), id);
        callClueType.enqueue(new Callback<Clue_Type>() {
            @Override
            public void onResponse(Call<Clue_Type> call, Response<Clue_Type> response) {

                if (response.isSuccessful()) {
                    Clue_Type clueType;
                    clueType = response.body();
                    Log.e("Clue Type ", clueType.getId() + " " + clueType.getType());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.saveClueType(clueType);
                    });

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Clue_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });


        return clue_type_dao.getClueTypeById(id);
    }

    public void saveClueType(Clue_Type clue_type) {
        Call<Clue_Type> callClueType = clue_type_service.saveClueTypeByWeb("Bearer " + UserHelper.getToken(context), clue_type);
        callClueType.enqueue(new Callback<Clue_Type>() {
            @Override
            public void onResponse(Call<Clue_Type> call, Response<Clue_Type> response) {

                if (response.isSuccessful()) {
                    Clue_Type clueType;
                    clueType = response.body();
                    Log.e("Clue Type Save ", clueType.getId() + " " + clueType.getType());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.saveClueType(clueType);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Clue_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void updateClueType(Clue_Type clue_type) {
        Call<Clue_Type> callClueType = clue_type_service.updateClueTypeByWeb("Bearer " + UserHelper.getToken(context), clue_type);
        callClueType.enqueue(new Callback<Clue_Type>() {
            @Override
            public void onResponse(Call<Clue_Type> call, Response<Clue_Type> response) {

                if (response.isSuccessful()) {
                    Clue_Type clueType;
                    clueType = response.body();
                    Log.e("Clue Type Update", clueType.getId() + " " + clueType.getType());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.updateClueType(clueType);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Clue_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteClueType(Clue_Type clue_type) {
        Call<Clue_Type> callClueType = clue_type_service.deleteClueTypeByWeb("Bearer " + UserHelper.getToken(context), clue_type.getId());
        callClueType.enqueue(new Callback<Clue_Type>() {
            @Override
            public void onResponse(Call<Clue_Type> call, Response<Clue_Type> response) {
                if (response.isSuccessful()) {
                    Clue_Type clueType;
                    clueType = response.body();
                    Log.e("Clue Type Delete ", clueType.getId() + " " + clueType.getType());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        clue_type_dao.deleteClueType(clueType);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Clue_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Insignia_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Professional_Rol_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.service.Insignia_Service;
import cu.cujae.gilsoft.tykeprof.service.Professional_Rol_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Insignia_Repository {

    private Insignia_Dao insignia_dao;
    private Professional_Rol_Dao professional_rol_dao;
    private final AppDatabase db;
    private Insignia_Service insignia_service;
    private Context context;
    private String token;

    public Insignia_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.insignia_dao = db.insignia_dao();
        this.professional_rol_dao = db.professional_rol_dao();
        this.insignia_service = RetrofitClient.getRetrofit().create(Insignia_Service.class);
        this.token = UserHelper.getToken(application);
    }

    public LiveData<List<Insignia>> getAllInsignias() {

        Call<List<Insignia>> listCallInsignia = insignia_service.getAllInsigniasByWeb("Bearer " + token);
        listCallInsignia.enqueue(new Callback<List<Insignia>>() {
            @Override
            public void onResponse(Call<List<Insignia>> call, Response<List<Insignia>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Professional_Rol> professional_rol = new ArrayList<>();

                    ArrayList<Insignia> insigniasSave = new ArrayList<>();
                    List<Insignia> insigniasResponse;
                    insigniasResponse = response.body();

                    for (Insignia insignia : insigniasResponse) {
                        professional_rol.add(insignia.getProfessional_rol());
                        insignia.setId_profess_rol(insignia.getProfessional_rol().getId_profess_rol());
                        insigniasSave.add(insignia);
                        Log.e("Insignia ", insignia.getId_insignia() + " " + insignia.getName() + "" + insignia.getProfessional_rol());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        insignia_dao.deleteAll();
                        professional_rol_dao.saveAllProfessionalRolList(professional_rol);
                        insignia_dao.saveAllInsignias(insigniasSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Insignia>> call, Throwable t) {
            }
        });
        return insignia_dao.getAllInsignias();
    }

    public Insignia getInsigniaLocalbyId(long id) {
        return insignia_dao.getInsigniaById(id);
    }

    public void saveInsignia(Insignia_Model insignia_model) {

        Call<Integer> saveInsignia = insignia_service.saveInsigniaByWeb("Bearer " + token, insignia_model);
        saveInsignia.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {

                    getAllInsignias();
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateInsignia(Insignia insignia) {

        Call<Insignia> callupdateInsignia = insignia_service.updateInsignialByWeb("Bearer " + token, insignia);
        callupdateInsignia.enqueue(new Callback<Insignia>() {
            @Override
            public void onResponse(Call<Insignia> call, Response<Insignia> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        insignia_dao.updateInsignia(insignia);
                    });
                    Toast.makeText(context, context.getString(R.string.edit_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Insignia> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteInsignia(long id) {

        Call<ResponseBody> calldeleteInsignia = insignia_service.deleteInsigniaByWeb("Bearer " + token, id);
        calldeleteInsignia.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        insignia_dao.deleteInsigniaByID(id);
                    });
                    Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();

                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

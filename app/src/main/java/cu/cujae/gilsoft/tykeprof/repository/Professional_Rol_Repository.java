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
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Professional_Rol_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.service.Professional_Rol_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Professional_Rol_Repository {

    private Career_Dao career_dao;
    private Topic_Dao topic_dao;
    private Professional_Rol_Dao professional_rol_dao;
    private final AppDatabase db;
    private Professional_Rol_Service professional_rol_service;
    private Context context;
    private String token;

    public Professional_Rol_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.career_dao = db.career_dao();
        this.topic_dao = db.topic_dao();
        this.professional_rol_dao = db.professional_rol_dao();
        this.professional_rol_service = RetrofitClient.getRetrofit().create(Professional_Rol_Service.class);
        // this.token = UserHelper.getToken(application);
    }

    public LiveData<List<Professional_Rol>> getAllProfessionalRol() {
        token = UserHelper.getToken(context);
        Call<List<Professional_Rol>> listCallProfessionalRol = professional_rol_service.getAllProfessionalRolByWeb("Bearer " + token);
        listCallProfessionalRol.enqueue(new Callback<List<Professional_Rol>>() {
            @Override
            public void onResponse(Call<List<Professional_Rol>> call, Response<List<Professional_Rol>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Career> careers = new ArrayList<>();
                    ArrayList<Topic> topics = new ArrayList<>();
                    ArrayList<Professional_Rol> professional_rolsSave = new ArrayList<>();
                    List<Professional_Rol> professional_rolsResponse;
                    professional_rolsResponse = response.body();

                    for (Professional_Rol professional_rol : professional_rolsResponse) {
                        professional_rolsSave.add(professional_rol);
                        careers.add(professional_rol.getCareer());
                        topics.add(professional_rol.getTopic());
                        Log.e("Professional Rol ", professional_rol.getId_profess_rol() + " " + professional_rol.getName() + "" + professional_rol.getCareer() + " " + professional_rol.getTopic());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        career_dao.saveAllCareer(careers);
                        topic_dao.saveAllTopic(topics);
                        professional_rol_dao.deleteAll();
                        professional_rol_dao.saveAllProfessionalRol(professional_rolsSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Professional_Rol>> call, Throwable t) {
            }
        });
        return professional_rol_dao.getAllProfessionalRol();
    }

    public List<Professional_Rol> getAllProfessionalRolList() {
        token = UserHelper.getToken(context);
        Call<List<Professional_Rol>> listCallProfessionalRol = professional_rol_service.getAllProfessionalRolByWeb("Bearer " + token);
        listCallProfessionalRol.enqueue(new Callback<List<Professional_Rol>>() {
            @Override
            public void onResponse(Call<List<Professional_Rol>> call, Response<List<Professional_Rol>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Career> careers = new ArrayList<>();
                    ArrayList<Topic> topics = new ArrayList<>();
                    ArrayList<Professional_Rol> professional_rolsSave = new ArrayList<>();
                    List<Professional_Rol> professional_rolsResponse;
                    professional_rolsResponse = response.body();

                    for (Professional_Rol professional_rol : professional_rolsResponse) {
                        professional_rolsSave.add(professional_rol);
                        careers.add(professional_rol.getCareer());
                        topics.add(professional_rol.getTopic());
                        Log.e("Professional Rol ", professional_rol.getId_profess_rol() + " " + professional_rol.getName() + "" + professional_rol.getCareer() + " " + professional_rol.getTopic());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        career_dao.saveAllCareer(careers);
                        topic_dao.saveAllTopic(topics);
                        //professional_rol_dao.deleteAll();
                        professional_rol_dao.saveAllProfessionalRolList(professional_rolsSave);
                    });
                } else if (response.code() == 403) {
                   // UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Professional_Rol>> call, Throwable t) {
            }
        });
        return professional_rol_dao.getAllProfessionalRolList();
    }

    public Professional_Rol getProfessRolLocalbyId(long id) {
        return professional_rol_dao.getProfessionalRolById(id);
    }

    public void saveProfessionalRol(Professional_Rol_Model professional_rol_model) {
        token = UserHelper.getToken(context);
        Call<Professional_Rol> saveProfessionalRolCall = professional_rol_service.saveProfessionalRolByWeb("Bearer " + token, professional_rol_model);
        saveProfessionalRolCall.enqueue(new Callback<Professional_Rol>() {
            @Override
            public void onResponse(Call<Professional_Rol> call, Response<Professional_Rol> response) {
                if (response.isSuccessful()) {
                    Professional_Rol professional_rol;
                    professional_rol = response.body();
                    Log.e("Professional Rol Save ", professional_rol.getId_profess_rol() + " " + professional_rol.getName());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        professional_rol_dao.saveProfessionalRol(professional_rol);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Professional_Rol> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteProfessionalRol(long id) {
        token = UserHelper.getToken(context);
        Call<ResponseBody> calldeleteProfessionalRol = professional_rol_service.deleteProfessionalRolByWeb("Bearer " + token, id);
        calldeleteProfessionalRol.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        professional_rol_dao.deleteProfessionalRolByID(id);
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

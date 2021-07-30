package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.service.Gift_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Grant_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Grant_Repository {

    private Grant_Dao grant_dao;
    private final AppDatabase db;
    private Grant_Service grant_service;
    private Context context;
    private String token;

    public Grant_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.grant_dao = db.grant_dao();
        this.grant_service = RetrofitClient.getRetrofit().create(Grant_Service.class);
        this.token = UserHelper.getToken(application);
    }

    public LiveData<List<Grant>> getAllGrantWeb() {
        Call<List<Grant>> listCallGrant = grant_service.getAllGrantByWeb("Bearer " + token);
        listCallGrant.enqueue(new Callback<List<Grant>>() {
            @Override
            public void onResponse(Call<List<Grant>> call, Response<List<Grant>> response) {
                if (response.isSuccessful()) {
                    List<Grant> grantList;
                    grantList = response.body();
                    for (Grant grant : grantList) {
                        Log.e("Grant ", grant.getId_grant() + " " + grant.getGrant_name());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.deleteAll();
                        grant_dao.saveAllGrant(grantList);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Grant>> call, Throwable t) {
            }
        });
        return grant_dao.getAllGrant();
    }

    public List<Grant> getAllGrantList() {
        Call<List<Grant>> listCallGrant = grant_service.getAllGrantByWeb("Bearer " + token);
        listCallGrant.enqueue(new Callback<List<Grant>>() {
            @Override
            public void onResponse(Call<List<Grant>> call, Response<List<Grant>> response) {
                if (response.isSuccessful()) {
                    //ArrayList<Grant> grantListLocal = (ArrayList<Grant>) grant_dao.getAllGrantList();
                    ArrayList<Grant> grantListResponse = (ArrayList<Grant>) response.body();

                   /* for (int i = 0; i < grantListLocal.size(); i++) {
                        Grant grantLocal = grantListLocal.get(i);
                            if (grantListResponse.contains(grantLocal)) {
                                AppDatabase.databaseWriteExecutor.execute(() -> {
                                    grant_dao.deleteGrantByID(grantLocal.getId_grant());

                                });
                                Toast.makeText(context, "Borrado: " + grantLocal, Toast.LENGTH_SHORT).show();
                            }

                    }*/
                    for (Grant grant : grantListResponse) {
                        Log.e("Grant ", grant.getId_grant() + " " + grant.getGrant_name());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.deleteAll();
                        grant_dao.saveAllGrantList(grantListResponse);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Grant>> call, Throwable t) {
            }
        });
        return grant_dao.getAllGrantList();
    }

    public List<Grant> getAllGrantLocalList() {
        return grant_dao.getAllGrantList();
    }

    public void saveGrant(Grant grant) {
        Call<Grant> saveGrantCall = grant_service.saveGrantByWeb("Bearer " + token, grant);
        saveGrantCall.enqueue(new Callback<Grant>() {
            @Override
            public void onResponse(Call<Grant> call, Response<Grant> response) {

                if (response.isSuccessful()) {
                    Grant gran_response;
                    gran_response = response.body();
                    Log.e("Grant Save ", gran_response.getId_grant() + " " + gran_response.getGrant_name());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.saveGrant(gran_response);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Grant> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateGrant(Grant grant) {

        Call<Grant> updateGrantCall = grant_service.updateGrantByWeb("Bearer " + token, grant);
        updateGrantCall.enqueue(new Callback<Grant>() {
            @Override
            public void onResponse(Call<Grant> call, Response<Grant> response) {

                if (response.isSuccessful()) {
                    Grant gran_response;
                    gran_response = response.body();
                    Log.e("Grant Update ", gran_response.getId_grant() + " " + gran_response.getGrant_name());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.updateGrant(gran_response);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Grant> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteGrant(Grant grant) {

        Call<ResponseBody> calldeleteGrant = grant_service.deleteGrantByWeb("Bearer " + token, grant.getId_grant());
        calldeleteGrant.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.deleteGrant(grant);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
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

    public Grant getGrantLocabyId(long id) {
        return grant_dao.getGrantById(id);
    }

   /* public Grant getGrantLocal(long id) throws ExecutionException, InterruptedException {

        Callable<Grant> callable = new Callable<Grant>() {
            @Override
            public Grant call() throws Exception {
                return grant_dao.getGrantById(id);
            }
        };
        Future<Grant> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }*/
}

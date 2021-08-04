package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Type_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gift_Type_Repository {

    private Gift_Type_Dao gift_type_dao;
    private final AppDatabase db;
    private Gift_Type_Service gift_type_service;
    private Context context;
    private String token;

    public Gift_Type_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.gift_type_dao = db.gift_type_dao();
        this.gift_type_service = RetrofitClient.getRetrofit().create(Gift_Type_Service.class);
        this.token = UserHelper.getToken(application);
    }

    public LiveData<List<Gift_Type>> getAllGiftTypeWeb() {

        Call<List<Gift_Type>> listCallQuestionType = gift_type_service.getAllGiftTypeByWeb("Bearer " + token);
        listCallQuestionType.enqueue(new Callback<List<Gift_Type>>() {
            @Override
            public void onResponse(Call<List<Gift_Type>> call, Response<List<Gift_Type>> response) {
                if (response.isSuccessful()) {
                    List<Gift_Type> gift_typeList;
                    gift_typeList = response.body();
                    for (Gift_Type gift_type : gift_typeList) {
                        Log.e("Gift Type ", gift_type.getId_gift_type() + " " + gift_type.getName());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        gift_type_dao.deleteAll();
                        gift_type_dao.saveAllGiftType(gift_typeList);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Gift_Type>> call, Throwable t) {
            }
        });
        return gift_type_dao.getAllGiftType();
    }

    public List<Gift_Type> getAllGiftTypeList() {

        Call<List<Gift_Type>> listCallQuestionType = gift_type_service.getAllGiftTypeByWeb("Bearer " + token);
        listCallQuestionType.enqueue(new Callback<List<Gift_Type>>() {
            @Override
            public void onResponse(Call<List<Gift_Type>> call, Response<List<Gift_Type>> response) {
                if (response.isSuccessful()) {
                    // ArrayList<Gift_Type> giftTypeListLocal = (ArrayList<Gift_Type>) gift_type_dao.getAllGiftListType();
                    ArrayList<Gift_Type> giftTypeListResponse = (ArrayList<Gift_Type>) response.body();

               /*     for (int i = 0; i < giftTypeListLocal.size(); i++) {
                        Gift_Type giftTypeLocal = giftTypeListLocal.get(i);
                            if (giftTypeListResponse.contains(giftTypeLocal)) {
                                AppDatabase.databaseWriteExecutor.execute(() -> {
                                    gift_type_dao.deleteGiftTypeByID(giftTypeLocal.getId_gift_type());
                                });
                                Toast.makeText(context, "Borrado: " + giftTypeLocal, Toast.LENGTH_SHORT).show();
                            }
                    }*/
                    for (Gift_Type gift_type : giftTypeListResponse) {
                        Log.e("Gift Type ", gift_type.getId_gift_type() + " " + gift_type.getName());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        gift_type_dao.deleteAll();
                        gift_type_dao.saveAllGiftTypelist(giftTypeListResponse);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Gift_Type>> call, Throwable t) {
            }
        });

        return gift_type_dao.getAllGiftTypeList();
    }

    public List<Gift_Type> getAllGiftTypeLocalList() {
        return gift_type_dao.getAllGiftTypeList();
    }

    public Gift_Type getGiftTypeLocalbyId(long id) {
        return gift_type_dao.getGiftTypeById(id);
    }

    public void saveGiftType(Gift_Type gift_type) {

        Call<Gift_Type> saveGiftTypeCall = gift_type_service.saveGiftTypeByWeb("Bearer " + token, gift_type);
        saveGiftTypeCall.enqueue(new Callback<Gift_Type>() {
            @Override
            public void onResponse(Call<Gift_Type> call, Response<Gift_Type> response) {
                if (response.isSuccessful()) {
                    Gift_Type giftType_response;
                    giftType_response = response.body();
                    Log.e("Gift Type Save ", giftType_response.getId_gift_type() + " " + giftType_response.getName());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        gift_type_dao.saveGiftType(giftType_response);
                    });
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Gift_Type> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteGiftType(Gift_Type gift_type) {

        Call<ResponseBody> calldeleteGiftType = gift_type_service.deleteGiftTypeByWeb("Bearer " + token, gift_type.getId_gift_type());
        calldeleteGiftType.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        gift_type_dao.deleteGiftType(gift_type);
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

}

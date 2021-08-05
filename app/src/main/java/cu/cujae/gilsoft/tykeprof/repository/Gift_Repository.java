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
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.service.Clue_Type_Service;
import cu.cujae.gilsoft.tykeprof.service.Gift_Service;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gift_Repository {

    private Gift_Dao gift_dao;
    Gift_Type_Dao gift_type_dao;
    Grant_Dao grant_dao;
    private final AppDatabase db;
    private Gift_Service gift_service;
    private Context context;
    private String token;

    public Gift_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.gift_dao = db.gift_dao();
        this.gift_type_dao = db.gift_type_dao();
        this.grant_dao = db.grant_dao();
        this.gift_service = RetrofitClient.getRetrofit().create(Gift_Service.class);
        //this.token = UserHelper.getToken(application);
    }

    public LiveData<List<Gift>> getAllGiftWeb() {
        token = UserHelper.getToken(context);
        Call<List<Gift>> listCallGift = gift_service.getAllGiftByWeb("Bearer " + token);
        listCallGift.enqueue(new Callback<List<Gift>>() {
            @Override
            public void onResponse(Call<List<Gift>> call, Response<List<Gift>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Gift> giftListSave = new ArrayList<>();
                    ArrayList<Grant> grantArrayList = new ArrayList<>();
                    ArrayList<Gift_Type> gift_typeArrayList = new ArrayList<>();
                    List<Gift> giftList;
                    giftList = response.body();

                    for (Gift gift : giftList) {
                        Gift giftSave = new Gift(gift.getId_gift(), gift.getDescrip(), gift.getGift_type().getId_gift_type(), gift.getGrant().getId_grant());
                        giftListSave.add(giftSave);
                        grantArrayList.add(gift.getGrant());
                        gift_typeArrayList.add(gift.getGift_type());
                        Log.e("Gift ", giftSave.getId_gift() + " " + giftSave.getDescrip() + "" + giftSave.getId_gift() + " " + gift.getGrant().getId_grant());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.saveAllGrant(grantArrayList);
                        gift_type_dao.saveAllGiftType(gift_typeArrayList);
                        gift_dao.deleteAll();
                        gift_dao.saveAllGift(giftListSave);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                    //getAllQuestionTypeWeb();
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Gift>> call, Throwable t) {
            }
        });
        return gift_dao.getAllGift();
    }

    public void saveGift(Gift_Model gift_model) {
        token = UserHelper.getToken(context);
        Call<ResponseBody> saveGiftCall = gift_service.saveGiftByWeb("Bearer " + token, gift_model);
        saveGiftCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, context.getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                    getAllGiftWeb();
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

    public void updateGiftWeb(Gift_Model gift_model) {
        token = UserHelper.getToken(context);
        Call<Gift> updateGiftCall = gift_service.updateGiftByWeb("Bearer " + token, gift_model);
        updateGiftCall.enqueue(new Callback<Gift>() {
            @Override
            public void onResponse(Call<Gift> call, Response<Gift> response) {

                if (response.isSuccessful()) {
                    Gift gift_response = response.body();
                    Grant grant = gift_response.getGrant();
                    Gift_Type gift_type = gift_response.getGift_type();
                    Gift giftUpdate = new Gift(gift_response.getId_gift(), gift_response.getDescrip(), gift_response.getGift_type().getId_gift_type(), gift_response.getGrant().getId_grant());
                    Log.e("Gift Update ", giftUpdate.getId_gift() + " " + giftUpdate.getDescrip());
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        grant_dao.saveGrant(grant);
                        gift_type_dao.saveGiftType(gift_type);
                        gift_dao.updateGift(giftUpdate);
                    });
                    Toast.makeText(context, context.getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Gift> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteGift(long id) {
        token = UserHelper.getToken(context);
        Call<ResponseBody> calldeleteGift = gift_service.deleteGiftByWeb("Bearer " + token, id);
        calldeleteGift.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        gift_dao.deleteGiftByID(id);
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

    public Gift getGiftbyId(long id) {
        return gift_dao.getGiftById(id);
    }
}

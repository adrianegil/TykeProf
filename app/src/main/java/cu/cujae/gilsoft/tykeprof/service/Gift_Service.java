package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Gift_Service {

    @GET("tyke-strategy-service/api/v1/regalo/all")
    Call<List<Gift>> getAllGiftByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/regalo/save")
    Call<ResponseBody> saveGiftByWeb(@Header("Authorization") String token, @Body Gift_Model gift_model);

    @POST("tyke-strategy-service/api/v1/regalo/update")
    Call<Gift> updateGiftByWeb(@Header("Authorization") String token, @Body Gift_Model gift_model);

    @DELETE("tyke-strategy-service/api/v1/regalo/delete/id")
    Call<ResponseBody> deleteGiftByWeb(@Header("Authorization") String token, @Query("id") long id);
}

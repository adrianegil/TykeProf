package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Gift_Type_Service {

    @GET("tyke-strategy-service/api/v1/tipo-regalo/all")
    Call<List<Gift_Type>> getAllGiftTypeByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/tipo-regalo/save")
    Call<Gift_Type> saveGiftTypeByWeb(@Header("Authorization") String token, @Body Gift_Type gift_type);

    @DELETE("tyke-strategy-service/api/v1/tipo-regalo/delete/id")
    Call<ResponseBody> deleteGiftTypeByWeb(@Header("Authorization") String token, @Query("id") long id);
}

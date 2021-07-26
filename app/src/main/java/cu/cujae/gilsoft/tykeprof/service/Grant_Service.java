package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Grant_Service {

    @GET("tyke-strategy-service/api/v1/otorgamiento/all")
    Call<List<Grant>> getAllGrantByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/otorgamiento/save")
    Call<Grant> saveGrantByWeb(@Header("Authorization") String token, @Body Grant grant);

    @POST("tyke-strategy-service/api/v1/otorgamiento/update")
    Call<Grant> updateGrantByWeb(@Header("Authorization") String token, @Body Grant grant);

    @DELETE("tyke-strategy-service/api/v1/otorgamiento/delete/id")
    Call<ResponseBody> deleteGrantByWeb(@Header("Authorization") String token, @Query("id") long id);
}

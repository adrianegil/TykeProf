package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Insignia_Service {

    @GET("tyke-strategy-service/api/v1/insignia/all")
    Call<List<Insignia>> getAllInsigniasByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/insignia/save")
    Call<Integer> saveInsigniaByWeb(@Header("Authorization") String token, @Body Insignia_Model insignia_model);

    @POST("tyke-strategy-service/api/v1/insignia/update")
    Call<Insignia> updateInsignialByWeb(@Header("Authorization") String token, @Body Insignia insignia);

    @DELETE("tyke-strategy-service/api/v1/insignia/delete/id")
    Call<ResponseBody> deleteInsigniaByWeb(@Header("Authorization") String token, @Query("id") long id);
}

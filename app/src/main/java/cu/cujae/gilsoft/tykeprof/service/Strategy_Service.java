package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.model.Strategy_Model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Strategy_Service {

    @GET("tyke-strategy-service/api/v1/estrategia/all")
    Call<List<Strategy>> getAllStrategiesByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/estrategia/save/all")
    Call<ResponseBody> saveStrategyByWeb(@Header("Authorization") String token, @Body Strategy_Model strategy_model);

    @DELETE("tyke-strategy-service/api/v1/estrategia/delete/id")
    Call<ResponseBody> deleteStrategyByWeb(@Header("Authorization") String token, @Query("id") long id);

}

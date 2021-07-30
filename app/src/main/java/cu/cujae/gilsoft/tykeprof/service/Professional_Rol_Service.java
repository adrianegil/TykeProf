package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.model.Gift_Model;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Professional_Rol_Service {

    @GET("tyke-strategy-service/api/v1/rol-profesional/all")
    Call<List<Professional_Rol>> getAllProfessionalRolByWeb(@Header("Authorization") String token);

    @POST("tyke-strategy-service/api/v1/rol-profesional/save")
    Call<Professional_Rol> saveProfessionalRolByWeb(@Header("Authorization") String token, @Body Professional_Rol_Model professional_rol_model);

    @DELETE("tyke-strategy-service/api/v1/rol-profesional/delete/id")
    Call<ResponseBody> deleteProfessionalRolByWeb(@Header("Authorization") String token, @Query("id") long id);

}

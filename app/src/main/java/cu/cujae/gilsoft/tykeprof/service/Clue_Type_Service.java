package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Clue_Type_Service {

    @GET("question-service/api/tipo-pista")
    Call<List<Clue_Type>> getAllClueTypeByWeb(@Header("Authorization") String token);

    @GET("question-service/api/tipo-pista/id")
    Call<Clue_Type> getClueTypeByIdByWeb(@Header("Authorization") String token, @Query("id") long id);

    @POST("question-service/api/tipo-pista/save")
    Call<Clue_Type> saveClueTypeByWeb(@Header("Authorization") String token, @Body Clue_Type clue_type);

    @POST("question-service/api/tipo-pista/update")
    Call<Clue_Type> updateClueTypeByWeb(@Header("Authorization") String token, @Body Clue_Type clue_type);

    @DELETE("question-service/api/tipo-pista/delete/id")
    Call<Clue_Type> deleteClueTypeByWeb(@Header("Authorization") String token, @Query("id") long id);
}

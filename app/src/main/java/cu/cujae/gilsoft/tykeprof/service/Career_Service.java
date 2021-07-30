package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Career_Service {

    @GET("schoolar-management-service/api/carrera")
    Call<List<Career>> getAllCareerByWeb(@Header("Authorization") String token);
}

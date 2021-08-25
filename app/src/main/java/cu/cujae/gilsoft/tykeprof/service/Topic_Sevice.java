package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Topic_Sevice {

    @GET("schoolar-management-service/api/tema")
    Call<List<Topic>> getAllTopicByWeb(@Header("Authorization") String token);

    @GET("schoolar-management-service/api/tema/asignatura")
    Call<List<Topic>> getTopicsBySubjectName(@Header("Authorization") String token,
                                             @Query("asignatura") String subjectName);

}

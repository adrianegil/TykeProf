package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Topic_Sevice {

    @GET("schoolar-management-service/api/tema")
    Call<List<Topic>> getAllTopicByWeb(@Header("Authorization") String token);
}

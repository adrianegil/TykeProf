package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Subject_Service {

    @GET("schoolar-management-service/api/asignatura")
    Call<List<Subject>> getAllSubjectByWeb(@Header("Authorization") String token);
}

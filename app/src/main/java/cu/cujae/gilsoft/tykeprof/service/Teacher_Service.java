package cu.cujae.gilsoft.tykeprof.service;

import cu.cujae.gilsoft.tykeprof.data.model.TeacherSubjectsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Teacher_Service {

    @GET("schoolar-management-service/api/profesor/user/id")
    Call<TeacherSubjectsModel> getTeacherIdWhitSubjectsByUserId(@Header("Authorization") String token, @Query("id") long id);
}

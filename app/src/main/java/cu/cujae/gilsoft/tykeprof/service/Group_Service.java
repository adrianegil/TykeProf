package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Group_Service {

    @GET("schoolar-management-service/api/grupo/all/asignatura-profesor")
    Call<List<Group>> getGroupsBySubjectNameAndTeacherId(@Header("Authorization") String token,
                                                  @Query("asignatura") String subjectName,
                                                  @Query("idProfesor") long teacherId);
}

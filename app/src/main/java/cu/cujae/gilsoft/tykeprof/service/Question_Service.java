package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Question_Service {

    @GET("question-service/api/pregunta")
    Call<List<Question>> getAllQuestionsByWeb(@Header("Authorization") String token);

    @DELETE("question-service/api/pregunta/delete/id")
    Call<Question> deleteQuestionsByWeb(@Header("Authorization") String token, @Query("id") long id);

}

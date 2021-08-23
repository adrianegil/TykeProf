package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.model.Question_Model;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Question_Service {

    @GET("question-service/api/pregunta")
    Call<List<Question>> getAllQuestionsByWeb(@Header("Authorization") String token);

    @POST("question-service/api/pregunta/save/complete")
    Call<Question> saveQuestionByWeb(@Header("Authorization") String token, @Body Question_Model question_model);

    @DELETE("question-service/api/pregunta/delete/id")
    Call<Question> deleteQuestionsByWeb(@Header("Authorization") String token, @Query("id") long id);
}

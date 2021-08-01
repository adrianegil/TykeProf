package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Question_Type_Service {

    @GET("question-service/api/tipo-pregunta")
    Call<List<Question_Type>> getAllQuestionTypeByWeb(@Header("Authorization") String token);

    @POST("question-service/api/tipo-pregunta/save")
    Call<Question_Type> saveQuestionTypeByWeb(@Header("Authorization") String token, @Body Question_Type question_type);

    @DELETE("question-service/api/tipo-pregunta/delete/id")
    Call<Question_Type> deleteQuestionTypeByWeb(@Header("Authorization") String token, @Query("id") long id);
}

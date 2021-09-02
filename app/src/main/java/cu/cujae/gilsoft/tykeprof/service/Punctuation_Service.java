package cu.cujae.gilsoft.tykeprof.service;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Punctuation_Service {

    @GET("tyke-strategy-service/api/v1/puntuacion/all")
    Call<List<Punctuation>> getAllPunctuationByWeb(@Header("Authorization") String token);
}

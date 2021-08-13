package cu.cujae.gilsoft.tykeprof.service;

import cu.cujae.gilsoft.tykeprof.util.Login;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Login_Service {

    /*@Headers({
            "Content-Type: application/json",
    })
    @FormUrlEncoded
    @POST("login-service/api/login")
    Call<ResponseBody> userLogin(@Field("password") String password, @Field("userCredential") String userCredential);*/

    //ENDPOINT DE AUTENTICACIÓN DE USUARIO http://localhost:9092/login-service/api/login
    @POST("login-service/api/login")
    Call<ResponseBody> userLogin(@Body Login login);
}

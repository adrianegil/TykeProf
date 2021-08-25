package cu.cujae.gilsoft.tykeprof.service;

import cu.cujae.gilsoft.tykeprof.data.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface User_Service {

    @GET("user-service/api/user/fetch-user/{username}")
    Call<User> getUserByWeb(@Path("username") String username);

    @POST("user-service/api/user/update")
    Call<Integer> updateUserByWeb(@Header("Authorization") String token, @Body User user);

    @POST("user-service/api/user/change-password")
    Call<Integer> changePasswordByWeb(@Header("Authorization") String token, @Body User user);

}

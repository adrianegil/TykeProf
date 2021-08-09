package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Role_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.User_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Role;
import cu.cujae.gilsoft.tykeprof.data.entity.User;
import cu.cujae.gilsoft.tykeprof.service.User_Service;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Repository {

    private Role_Dao role_dao;
    private User_Dao user_dao;
    private final AppDatabase db;
    private User_Service user_service;
    private Context context;

    public User_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.role_dao = db.role_dao();
        this.user_dao = db.user_dao();
        this.user_service = RetrofitClient.getRetrofit().create(User_Service.class);
    }

    public LiveData<User> getUser() {
        Call<User> getUser = user_service.getUserByWeb(UserHelper.getUserLogin(context).getUserCredential());
        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    ArrayList<Role> roles = new ArrayList<>();
                    User user = response.body();
                    for (Role role : user.getRoles()) {
                        role.setId_user(user.getId_user());
                        roles.add(role);
                    }
                    Log.e("User: ", user.getUserName() + " " + user.getFullName() + "" + user.getImage_url());

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        //  user_dao.deleteAll();
                        user_dao.saveUser(user);
                        role_dao.deleteAll();
                        role_dao.saveAllRoles(roles);
                    });
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        return user_dao.getLiveDataUser();
    }

    public LiveData<User> getLiveUserLocal() {
        return user_dao.getLiveDataUser();
    }

    public void updateUser(User user) {
        user.setRoles(role_dao.getAllRoleList(user.getId_user()));

       /* user.setFullName("Humberto Cabrera Dominguez");
        user.setEmail("hcabrera@gmail.com");
        Date date = new Date(1965 - 1900, 11, 15);
        String fech = String.valueOf(date.getTime());
        user.setDob(fech);*/

        Call<Integer> callupdateUser = user_service.updateUserByWeb("Bearer " + UserHelper.getToken(context), user);
        callupdateUser.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    getUser();
                    Toast.makeText(context, context.getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword(User user) {
        List<Role> roleList = role_dao.getAllRoleList(user.getId_user());
        user.setRoles(roleList);
        Login login = UserHelper.getUserLogin(context);

        Call<Integer> callchangePasswordUser = user_service.changePasswordByWeb("Bearer " + UserHelper.getToken(context), user);
        callchangePasswordUser.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    login.setPassword(user.getPassword());
                    UserHelper.saveUserLogin(login, context);
                    getUser();
                    Toast.makeText(context, context.getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(context);
                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

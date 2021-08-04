package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.User;
import cu.cujae.gilsoft.tykeprof.repository.Insignia_Repository;
import cu.cujae.gilsoft.tykeprof.repository.User_Repository;

public class UserViewModel extends AndroidViewModel {

    private User_Repository user_repository;
    private final LiveData<User> user;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.user_repository = new User_Repository(application);
        this.user = user_repository.getUser();
    }

    public void updateUser(User user) {
        user_repository.updateUser(user);
    }

    public void changePassword(User user) {
        user_repository.changePassword(user);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<User> getLiveUserLocal() {
        return user_repository.getLiveUserLocal();
    }
}
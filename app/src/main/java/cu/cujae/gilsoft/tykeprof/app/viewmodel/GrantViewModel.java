package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.repository.Grant_Repository;

public class GrantViewModel extends AndroidViewModel {

    private Grant_Repository grant_repository;
    private final LiveData<List<Grant>> allGrant;

    public GrantViewModel(@NonNull Application application) {
        super(application);
        this.grant_repository = new Grant_Repository(application);
        this.allGrant = grant_repository.getAllGrantWeb();
    }

    public void saveGrant(Grant grant) {
        grant_repository.saveGrant(grant);
    }

    public void updateGrant(Grant grant) {
        grant_repository.updateGrant(grant);
    }

    public void deleteGrant(Grant grant) {
        grant_repository.deleteGrant(grant);
    }

    public LiveData<List<Grant>> getAllGrant() {
        return allGrant;
    }
}
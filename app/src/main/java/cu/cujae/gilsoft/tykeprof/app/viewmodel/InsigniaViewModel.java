package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.model.Insignia_Model;
import cu.cujae.gilsoft.tykeprof.repository.Insignia_Repository;

public class InsigniaViewModel extends AndroidViewModel {

    private Insignia_Repository insignia_repository;
    private final LiveData<List<Insignia>> allInsignias;

    public InsigniaViewModel(@NonNull Application application) {
        super(application);
        this.insignia_repository = new Insignia_Repository(application);
        this.allInsignias = insignia_repository.getAllInsignias();
    }

    public void saveInsignia(Insignia_Model insignia_model) {
        insignia_repository.saveInsignia(insignia_model);
    }

    public void updateInsignia(Insignia insignia) {
        insignia_repository.updateInsignia(insignia);
    }

    public void deleteInsignia(long id) {
        insignia_repository.deleteInsignia(id);
    }

    public LiveData<List<Insignia>> getAllInsignias() {
        return allInsignias;
    }
}
package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.repository.Clue_Type_Repository;
import cu.cujae.gilsoft.tykeprof.repository.Question_Type_Repository;

public class ClueTypeViewModel extends AndroidViewModel {

    private Clue_Type_Repository clue_type_repository;
    private final LiveData<List<Clue_Type>> allClueType;

    public ClueTypeViewModel(@NonNull Application application) {
        super(application);
        this.clue_type_repository = new Clue_Type_Repository(application);
        this.allClueType = clue_type_repository.getAllClueTypeWeb();
    }

    public Clue_Type getClueTypeById(long id) {
        return clue_type_repository.getClueTypeByID(id);
    }

    public void saveClueType(Clue_Type clue_type) {
        clue_type_repository.saveClueType(clue_type);
    }

    public void updateClueType(Clue_Type clue_type) {
        clue_type_repository.updateClueType(clue_type);
    }

    public void deleteClueType(Clue_Type clue_type) {
        clue_type_repository.deleteClueType(clue_type);
    }

    public LiveData<List<Clue_Type>> getAllClueType() {
        return allClueType;
    }
}
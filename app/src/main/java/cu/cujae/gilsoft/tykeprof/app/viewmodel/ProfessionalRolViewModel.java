package cu.cujae.gilsoft.tykeprof.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.model.Professional_Rol_Model;
import cu.cujae.gilsoft.tykeprof.repository.Professional_Rol_Repository;

public class ProfessionalRolViewModel extends AndroidViewModel {
    private Professional_Rol_Repository professional_rol_repository;
    private final LiveData<List<Professional_Rol>> allProfessionalRol;

    public ProfessionalRolViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.professional_rol_repository = new Professional_Rol_Repository(application);
        this.allProfessionalRol = professional_rol_repository.getAllProfessionalRol();
    }

    public void saveProfessionalRol(Professional_Rol_Model professional_rol_model) {
        professional_rol_repository.saveProfessionalRol(professional_rol_model);
    }

    public void deleteProfessionalRol(long id) {
        professional_rol_repository.deleteProfessionalRol(id);
    }

    public LiveData<List<Professional_Rol>> getAllProfessionalRol() {
        return allProfessionalRol;
    }
}



package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;

@Dao
public interface Professional_Rol_Dao {

    @Query("SELECT * FROM professional_rol")
    LiveData<List<Professional_Rol>> getAllProfessionalRol();

    @Query("SELECT * FROM professional_rol")
    List<Professional_Rol> getAllProfessionalRolList();

    @Query("SELECT * FROM professional_rol WHERE id_profess_rol =:id")
    Professional_Rol getProfessionalRolById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllProfessionalRol(List<Professional_Rol> professionalRolList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllProfessionalRolList(List<Professional_Rol> professionalRolList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveProfessionalRol(Professional_Rol professional_rol);

    @Delete
    void deleteProfessionalRol(Professional_Rol professional_rol);

    @Query("DELETE FROM professional_rol WHERE id_profess_rol = :id")
    void deleteProfessionalRolByID(long id);

    @Query("DELETE FROM professional_rol")
    void deleteAll();

}

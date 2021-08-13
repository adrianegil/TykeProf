package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;

@Dao
public interface Clue_Type_Dao {

    @Query("SELECT * FROM clue_type")
    LiveData<List<Clue_Type>> getAllClueType();

    @Query("SELECT * FROM clue_type")
    List<Clue_Type> getAllClueTypeList();

    @Query("SELECT * FROM clue_type WHERE id =:id")
    Clue_Type getClueTypeById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllClueType(List<Clue_Type> clue_typeList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllClueTypeList(List<Clue_Type> clue_typeList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveClueType(Clue_Type clue_type);

    @Update
    void updateClueType(Clue_Type clue_type);

    @Delete
    void deleteClueType(Clue_Type clue_type);

    @Query("DELETE FROM clue_type WHERE id = :id")
    void deleteClueTypeByID(long id);

    @Query("DELETE FROM clue_type")
    void deleteAll();
}

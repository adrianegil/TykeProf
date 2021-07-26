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
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;

@Dao
public interface Grant_Dao {

    @Query("SELECT * FROM grant")
    LiveData<List<Grant>> getAllGrant();

    @Query("SELECT * FROM grant WHERE id_grant =:id")
    Grant getGrantById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllGrant(List<Grant> grantList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveGrant(Grant grant);

    @Update
    void updateGrant(Grant grant);

    @Delete
    void deleteGrant(Grant grant);

    @Query("DELETE FROM grant WHERE id_grant = :id")
    void deleteGrantByID(long id);

    @Query("DELETE FROM grant")
    void deleteAll();
}

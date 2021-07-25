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
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;

@Dao
public interface Gift_Type_Dao {

    @Query("SELECT * FROM gift_type")
    LiveData<List<Gift_Type>> getAllGiftType();

    @Query("SELECT * FROM gift_type WHERE id_gift_type =:id")
    Gift_Type getGiftTypeById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllGiftType(List<Gift_Type> gift_typeList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveGiftType(Gift_Type gift_type);

    @Delete
    void deleteGiftType(Gift_Type gift_type);

    @Query("DELETE FROM gift_type WHERE id_gift_type = :id")
    void deleteGiftTypeByID(long id);

    @Query("DELETE FROM gift_type")
    void deleteAll();
}

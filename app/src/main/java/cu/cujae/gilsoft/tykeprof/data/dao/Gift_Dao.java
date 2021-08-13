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

@Dao
public interface Gift_Dao {

    @Query("SELECT * FROM gift")
    LiveData<List<Gift>> getAllGift();

    @Query("SELECT * FROM gift WHERE id_gift =:id")
    Gift getGiftById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllGift(List<Gift> giftList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveGift(Gift gift);

    @Update
    void updateGift(Gift gift);

    @Delete
    void deleteGift(Gift gift);

    @Query("DELETE FROM gift WHERE id_gift = :id")
    void deleteGiftByID(long id);

    @Query("DELETE FROM gift")
    void deleteAll();

}

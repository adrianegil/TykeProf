package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;

@Dao
public interface Insignia_Dao {

    @Query("SELECT * FROM insignia")
    LiveData<List<Insignia>> getAllInsignias();

    @Query("SELECT * FROM insignia WHERE id_insignia =:id")
    Insignia getInsigniaById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllInsignias(List<Insignia> insigniaList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveInsignia(Insignia insignia);

    @Update
    void updateInsignia(Insignia insignia);

    @Delete
    void deleteInsignia(Insignia insignia);

    @Query("DELETE FROM insignia WHERE id_insignia = :id")
    void deleteInsigniaByID(long id);

    @Query("DELETE FROM insignia")
    void deleteAll();

}

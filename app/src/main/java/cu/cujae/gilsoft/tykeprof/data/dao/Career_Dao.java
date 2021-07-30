package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;

@Dao
public interface Career_Dao {

    @Query("SELECT * FROM career")
    List<Career> getAllCareerList();

    @Query("SELECT * FROM career WHERE id_career =:id")
    Career getCareerById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllCareer(List<Career> careerList);

    @Query("DELETE FROM career WHERE id_career = :id")
    void deleteCareerByID(long id);

    @Query("DELETE FROM career")
    void deleteAll();
}

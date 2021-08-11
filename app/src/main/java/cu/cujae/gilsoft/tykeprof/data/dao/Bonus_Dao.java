package cu.cujae.gilsoft.tykeprof.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;

@Dao
public interface Bonus_Dao {

    @Query("SELECT * FROM bonus WHERE id_question =:id ")
    List<Bonus> getBonusListByQuestionID(long id);

    @Query("SELECT * FROM bonus WHERE id_bonus =:id")
    Bonus getBonusById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllBonuses(List<Bonus> bonusList);

    @Query("DELETE FROM bonus WHERE id_bonus = :id")
    void deleteBonusByID(long id);

    @Query("DELETE FROM bonus")
    void deleteAll();

}

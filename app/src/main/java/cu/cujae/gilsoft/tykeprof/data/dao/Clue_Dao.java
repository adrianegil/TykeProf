package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;

@Dao
public interface Clue_Dao {

    @Query("SELECT * FROM clue WHERE id_question =:id ")
    List<Clue> getClueListByQuestionID(long id);

    @Query("SELECT * FROM clue WHERE id_clue =:id")
    Clue getClueById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllClueList(List<Clue> clueList);

    @Query("DELETE FROM clue WHERE id_clue = :id")
    void deleteClueByID(long id);

    @Query("DELETE FROM clue")
    void deleteAll();

}

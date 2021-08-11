package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Answer;

@Dao
public interface Answer_Dao {

    @Query("SELECT * FROM answer WHERE id_question =:id ")
    List<Answer> getAnswerListByQuestionID(long id);

    @Query("SELECT * FROM answer WHERE id_answer =:id")
    Answer getAnswerById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllAnswerList(List<Answer> responseList);

    @Query("DELETE FROM answer WHERE id_answer = :id")
    void deleteAnswerByID(long id);

    @Query("DELETE FROM answer")
    void deleteAll();

}

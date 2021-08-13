package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question;

@Dao
public interface Question_Dao {

    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM question")
    List<Question> getAllQuestionList();

    @Query("SELECT * FROM question WHERE id_question =:id")
    Question getQuestionById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllQuestionList(List<Question> questionList);

    @Query("DELETE FROM question WHERE id_question = :id")
    void deleteQuestionByID(long id);

    @Query("DELETE FROM question")
    void deleteAll();

}

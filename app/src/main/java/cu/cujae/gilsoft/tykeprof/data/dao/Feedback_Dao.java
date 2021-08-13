package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Feedback;

@Dao
public interface Feedback_Dao {

    @Query("SELECT * FROM feedback WHERE id_question =:id ")
    List<Feedback> getFeedbackListByQuestionID(long id);

    @Query("SELECT * FROM feedback WHERE id_feedback =:id")
    Feedback getFeedbackById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllFeedbacks(List<Feedback> bonusList);

    @Query("DELETE FROM feedback WHERE id_feedback = :id")
    void deleteFeedbackByID(long id);

    @Query("DELETE FROM feedback")
    void deleteAll();

}

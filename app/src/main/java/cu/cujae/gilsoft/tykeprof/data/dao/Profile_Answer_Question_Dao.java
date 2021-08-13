package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Profile_Answer_Question;

@Dao
public interface Profile_Answer_Question_Dao {

    @Query("SELECT * FROM profile_answer_question WHERE id_profile_ans_quest =:id")
    Profile_Answer_Question getProfile_Answer_QuestionById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllProfile_Answer_Question(List<Profile_Answer_Question> profile_answer_questions);

    @Query("DELETE FROM profile_answer_question WHERE id_profile_ans_quest = :id")
    void deleteProfile_Answer_QuestionByID(long id);

    @Query("DELETE FROM profile_answer_question")
    void deleteAll();
}

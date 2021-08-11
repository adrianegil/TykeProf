package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;

@Dao
public interface Question_Type_Dao {

    @Query("SELECT * FROM question_type")
    LiveData<List<Question_Type>> getAllQuestionType();

    @Query("SELECT * FROM question_type")
    List<Question_Type> getAllQuestionTypeList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveQuestionType(Question_Type question_type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllQuestionType(List<Question_Type> question_typeList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllQuestionTypelist(List<Question_Type> question_typeList);

    @Delete
    void deleteQuestionType(Question_Type question_type);

    @Query("DELETE FROM question_type WHERE id = :id")
    void deleteQuestionTypeById(long id);

    @Query("DELETE FROM question_type")
    void deleteAll();
}

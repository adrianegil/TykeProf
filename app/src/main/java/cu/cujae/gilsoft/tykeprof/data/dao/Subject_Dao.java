package cu.cujae.gilsoft.tykeprof.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;

@Dao
public interface Subject_Dao {

    @Query("SELECT * FROM subject")
    List<Subject> getAllSubjectList();

    @Query("SELECT * FROM subject WHERE id_subject =:id")
    Subject getSubjectById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllSubject(List<Subject> subjectList);

    @Query("DELETE FROM subject WHERE id_subject = :id")
    void deleteSubjectByID(long id);

    @Query("DELETE FROM subject")
    void deleteAll();

}

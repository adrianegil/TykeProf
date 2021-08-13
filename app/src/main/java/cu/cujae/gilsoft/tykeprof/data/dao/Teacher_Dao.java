package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;

@Dao
public interface Teacher_Dao {

    @Query("SELECT * FROM teacher")
    List<Teacher> getAllTeacherList();

    @Query("SELECT * FROM teacher WHERE id_teacher =:id")
    Teacher getTeacherById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllTeacher(List<Teacher> teacherList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTeacher(Teacher teacher);

    @Query("DELETE FROM teacher WHERE id_teacher = :id")
    void deleteTeacherByID(long id);

    @Query("DELETE FROM teacher")
    void deleteAll();
}

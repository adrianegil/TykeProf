package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;

import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Teacher_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;

public class Teacher_Repository {

    private Teacher_Dao teacher_dao;
    private final AppDatabase db;
    private Context context;

    public Teacher_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.teacher_dao = db.teacher_dao();
    }

    public Teacher getTeacherLocalById(long id){
        return teacher_dao.getTeacherById(id);
    }

}

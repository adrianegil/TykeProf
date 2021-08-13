package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Answer_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;

public class Answer_Repository {

    private Answer_Dao answer_dao;
    private final AppDatabase db;
    private Context context;

    public Answer_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.answer_dao = db.answer_dao();
    }

    public List<Answer> getAnswerListByQuestionID(long id) {
        return answer_dao.getAnswerListByQuestionID(id);
    }
}

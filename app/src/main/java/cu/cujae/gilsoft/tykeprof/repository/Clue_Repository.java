package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;

public class Clue_Repository {

    private Clue_Dao clue_dao;
    private final AppDatabase db;
    private Context context;

    public Clue_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.clue_dao = db.clue_dao();
    }

    public List<Clue> getClueListByQuestionID(long id) {
        return clue_dao.getClueListByQuestionID(id);
    }

}

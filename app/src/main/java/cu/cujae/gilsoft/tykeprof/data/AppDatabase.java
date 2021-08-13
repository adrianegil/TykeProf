package cu.cujae.gilsoft.tykeprof.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cu.cujae.gilsoft.tykeprof.data.converter.DateConverter;
import cu.cujae.gilsoft.tykeprof.data.dao.Answer_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Bonus_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Feedback_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Group_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Insignia_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Professional_Rol_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Profile_Answer_Question_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Role_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Strategy_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Subject_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Teacher_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.User_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Bonus;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Feedback;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Insignia;
import cu.cujae.gilsoft.tykeprof.data.entity.Player_Challenge;
import cu.cujae.gilsoft.tykeprof.data.entity.Player_Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Professional_Rol;
import cu.cujae.gilsoft.tykeprof.data.entity.Profile_Answer_Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Answer;
import cu.cujae.gilsoft.tykeprof.data.entity.Role;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Topic;
import cu.cujae.gilsoft.tykeprof.data.entity.Subject;
import cu.cujae.gilsoft.tykeprof.data.entity.Teacher;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.data.entity.User;

@Database(entities = {Question_Type.class, Clue_Type.class, Gift_Type.class, Grant.class, Gift.class, Career.class, Topic.class,
        Professional_Rol.class, Insignia.class, Role.class, User.class, Subject.class, Bonus.class, Clue.class, Answer.class,
        Feedback.class, Profile_Answer_Question.class, Question.class, Group.class, Player_Challenge.class, Player_Strategy.class,
        Strategy.class, Strategy_Group.class, Strategy_Question.class, Strategy_Topic.class, Teacher.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Question_Type_Dao question_type_dao();

    public abstract Clue_Type_Dao clue_type_dao();

    public abstract Gift_Type_Dao gift_type_dao();

    public abstract Grant_Dao grant_dao();

    public abstract Gift_Dao gift_dao();

    public abstract Career_Dao career_dao();

    public abstract Topic_Dao topic_dao();

    public abstract Professional_Rol_Dao professional_rol_dao();

    public abstract Insignia_Dao insignia_dao();

    public abstract Role_Dao role_dao();

    public abstract User_Dao user_dao();

    public abstract Subject_Dao subject_dao();

    public abstract Bonus_Dao bonus_dao();

    public abstract Clue_Dao clue_dao();

    public abstract Answer_Dao answer_dao();

    public abstract Feedback_Dao feedback_dao();

    public abstract Profile_Answer_Question_Dao profile_answer_question_dao();

    public abstract Question_Dao question_dao();

    public abstract Group_Dao group_dao();

    public abstract Strategy_Dao strategy_dao();

    public abstract Teacher_Dao teacher_dao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "tykeprof_db")
                            .allowMainThreadQueries()
                            //.addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                /*Question_Type_Dao question_type_dao = INSTANCE.question_type_dao();
                question_type_dao.deleteAll();

                Clue_Type_Dao clue_type_dao = INSTANCE.clue_type_dao();
                clue_type_dao.deleteAll();

                Gift_Type_Dao gift_type_dao = INSTANCE.gift_type_dao();
                gift_type_dao.deleteAll();*/
            });
        }
    };

}

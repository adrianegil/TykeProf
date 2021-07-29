package cu.cujae.gilsoft.tykeprof.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cu.cujae.gilsoft.tykeprof.data.dao.Clue_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Gift_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Grant_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Question_Type_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Clue_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift;
import cu.cujae.gilsoft.tykeprof.data.entity.Gift_Type;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Question_Type;

@Database(entities = {Question_Type.class, Clue_Type.class, Gift_Type.class, Grant.class, Gift.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Question_Type_Dao question_type_dao();

    public abstract Clue_Type_Dao clue_type_dao();

    public abstract Gift_Type_Dao gift_type_dao();

    public abstract Grant_Dao grant_dao();

    public abstract Gift_Dao gift_dao();


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

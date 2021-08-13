package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Topic;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitGroups;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitQuestions;
import cu.cujae.gilsoft.tykeprof.data.relation.StrategyWhitTopics;

@Dao
public interface Strategy_Dao {

    @Query("SELECT * FROM strategy")
    LiveData<List<Strategy>> getAllStrategies();

    @Query("SELECT * FROM strategy")
    List<Strategy> getAllStrategiesList();

    @Query("SELECT * FROM strategy WHERE id_strategy =:id")
    Strategy getStrategyById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllStrategiesList(List<Strategy> strategyList);

    @Query("DELETE FROM strategy WHERE id_strategy = :id")
    void deleteStrategyByID(long id);

    @Query("DELETE FROM strategy")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_Group(Strategy_Group strategy_group);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_GroupList(List<Strategy_Group> strategyGroupList);

    @Transaction
    @Query("SELECT * FROM strategy WHERE id_strategy = :id ")
    StrategyWhitGroups getStrategyWhitGroups(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_Topic(Strategy_Topic strategy_topic);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_TopicList(List<Strategy_Topic> strategyTopicList);

    @Transaction
    @Query("SELECT * FROM strategy WHERE id_strategy = :id ")
    StrategyWhitTopics getStrategyWhitTopics(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_Question(Strategy_Question strategy_question);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveStrategy_QuestionList(List<Strategy_Question> strategyQuestionList);

    @Transaction
    @Query("SELECT * FROM strategy WHERE id_strategy = :id ")
    StrategyWhitQuestions getStrategyWhitQuestions(long id);

}

package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Topic;

@Dao
public interface Topic_Dao {

    @Query("SELECT * FROM topic")
    List<Topic> getAllTopicList();

    @Query("SELECT * FROM topic WHERE id_topic =:id")
    Topic getTopicById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllTopic(List<Topic> topicList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllTopicReplace(List<Topic> topicList);

    @Query("DELETE FROM topic WHERE id_topic = :id")
    void deleteTopicByID(long id);

    @Query("DELETE FROM topic")
    void deleteAll();
}

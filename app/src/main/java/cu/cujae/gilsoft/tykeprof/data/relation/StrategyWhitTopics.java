package cu.cujae.gilsoft.tykeprof.data.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Topic;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;

public class StrategyWhitTopics {

    @Embedded
    public Strategy strategy;

    @Relation(
            parentColumn = "id_strategy",
            entityColumn = "id_topic",
            associateBy = @Junction(Strategy_Topic.class)
    )
    public List<Topic> topicList;
}

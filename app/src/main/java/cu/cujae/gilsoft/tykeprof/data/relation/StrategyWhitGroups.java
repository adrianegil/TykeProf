package cu.cujae.gilsoft.tykeprof.data.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Group;

public class StrategyWhitGroups {

    @Embedded
    public Strategy strategy;

    @Relation(
            parentColumn = "id_strategy",
            entityColumn = "id_group",
            associateBy = @Junction(Strategy_Group.class)
    )
    public List<Group> groupList;
}

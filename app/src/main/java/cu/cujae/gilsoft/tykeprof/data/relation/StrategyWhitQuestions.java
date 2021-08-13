package cu.cujae.gilsoft.tykeprof.data.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy;
import cu.cujae.gilsoft.tykeprof.data.entity.Strategy_Question;

public class StrategyWhitQuestions {

    @Embedded
    public Strategy strategy;

    @Relation(
            parentColumn = "id_strategy",
            entityColumn = "id_question",
            associateBy = @Junction(Strategy_Question.class)
    )
    public List<Question> questionList;
}

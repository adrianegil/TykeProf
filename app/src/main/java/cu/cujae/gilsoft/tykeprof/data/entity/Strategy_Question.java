package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "strategy_question", primaryKeys = {"id_question", "id_strategy"},
        indices = {@Index(value = {"id_question"}), @Index(value = "id_strategy")})
public class Strategy_Question {

    @ColumnInfo(name = "id_question")
    public long id_question;

    @ColumnInfo(name = "id_strategy")
    public long id_strategy;

    public Strategy_Question() {
    }

    @Ignore
    public Strategy_Question(long id_question, long id_strategy) {
        this.id_question = id_question;
        this.id_strategy = id_strategy;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }
}

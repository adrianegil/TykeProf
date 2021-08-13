package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "strategy_topic",primaryKeys = {"id_topic", "id_strategy"},
        indices = {@Index(value = {"id_topic"}), @Index(value = "id_strategy")})
public class Strategy_Topic {

    @ColumnInfo(name = "id_topic")
    public long id_topic;

    @ColumnInfo(name = "id_strategy")
    public long id_strategy;

    public Strategy_Topic() {
    }

    @Ignore
    public Strategy_Topic(long id_topic, long id_strategy) {
        this.id_topic = id_topic;
        this.id_strategy = id_strategy;
    }

    public long getId_topic() {
        return id_topic;
    }

    public void setId_topic(long id_topic) {
        this.id_topic = id_topic;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }
}

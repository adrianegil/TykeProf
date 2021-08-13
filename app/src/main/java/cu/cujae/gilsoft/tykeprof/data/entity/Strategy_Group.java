package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(tableName = "strategy_group", primaryKeys = {"id_group", "id_strategy"},
        indices = {@Index(value = {"id_group"}), @Index(value = "id_strategy")})
public class Strategy_Group {

    @ColumnInfo(name = "id_group")
    public long id_group;

    @ColumnInfo(name = "id_strategy")
    public long id_strategy;

    public Strategy_Group() {
    }

    @Ignore
    public Strategy_Group(long id_group, long id_strategy) {
        this.id_group = id_group;
        this.id_strategy = id_strategy;
    }

    public long getId_group() {
        return id_group;
    }

    public void setId_group(long id_group) {
        this.id_group = id_group;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }
}

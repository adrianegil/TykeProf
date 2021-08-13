package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_strategy")
public class Player_Strategy {

    @PrimaryKey
    @ColumnInfo(name = "id_player_strategy")
    private long id_player_strategy;

    @ColumnInfo(name = "id_strategy")
    private long id_strategy;

    @ColumnInfo(name = "id_player")
    private long id_player;

    @ColumnInfo(name = "score_obtained")
    private Integer score_obtained;

    @ColumnInfo(name = "completed")
    private boolean completed;

    public Player_Strategy() {
    }

    public long getId_player_strategy() {
        return id_player_strategy;
    }

    public void setId_player_strategy(long id_player_strategy) {
        this.id_player_strategy = id_player_strategy;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }

    public long getId_player() {
        return id_player;
    }

    public void setId_player(long id_player) {
        this.id_player = id_player;
    }

    public Integer getScore_obtained() {
        return score_obtained;
    }

    public void setScore_obtained(Integer score_obtained) {
        this.score_obtained = score_obtained;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

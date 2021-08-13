package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_challenge")
public class Player_Challenge {

    @PrimaryKey
    @ColumnInfo(name = "id_player_challenge")
    private long id_player_challenge;

    @ColumnInfo(name = "id_player_challenging")
    private long id_player_challenging;

    @ColumnInfo(name = "id_player_challenged")
    private long id_player_challenged;

    @ColumnInfo(name = "challenge_committed")
    private boolean challenge_committed;

    @ColumnInfo(name = "challenge_confirmed")
    private boolean challenge_confirmed;

    @ColumnInfo(name = "id_strategy")
    private long id_strategy;

    public Player_Challenge() {
    }

    public long getId_player_challenge() {
        return id_player_challenge;
    }

    public void setId_player_challenge(long id_player_challenge) {
        this.id_player_challenge = id_player_challenge;
    }

    public long getId_player_challenging() {
        return id_player_challenging;
    }

    public void setId_player_challenging(long id_player_challenging) {
        this.id_player_challenging = id_player_challenging;
    }

    public long getId_player_challenged() {
        return id_player_challenged;
    }

    public void setId_player_challenged(long id_player_challenged) {
        this.id_player_challenged = id_player_challenged;
    }

    public boolean isChallenge_committed() {
        return challenge_committed;
    }

    public void setChallenge_committed(boolean challenge_committed) {
        this.challenge_committed = challenge_committed;
    }

    public boolean isChallenge_confirmed() {
        return challenge_confirmed;
    }

    public void setChallenge_confirmed(boolean challenge_confirmed) {
        this.challenge_confirmed = challenge_confirmed;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }
}

package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "clue_type")
public class Clue_Type {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "type")
    @SerializedName("tipo")
    private String type;

    @ColumnInfo(name = "clue_points")
    @SerializedName("puntosPista")
    private Integer CluePoints;

    @ColumnInfo(name = "game_points")
    @SerializedName("puntosJuego")
    private Integer GamePoints;

    public Clue_Type() {
    }

    @Ignore
    public Clue_Type(String type, Integer cluePoints, Integer gamePoints) {
        this.type = type;
        CluePoints = cluePoints;
        GamePoints = gamePoints;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCluePoints() {
        return CluePoints;
    }

    public void setCluePoints(Integer cluePoints) {
        CluePoints = cluePoints;
    }

    public Integer getGamePoints() {
        return GamePoints;
    }

    public void setGamePoints(Integer gamePoints) {
        GamePoints = gamePoints;
    }
}

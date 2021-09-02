package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "punctuation")
public class Punctuation {

    @PrimaryKey
    @ColumnInfo(name = "id_punctuation")
    @SerializedName("idPuntuacion")
    private Long id_punctuation;

    @ColumnInfo(name = "punctuation")
    @SerializedName("puntuacion")
    private Long punctuation;

    @Embedded
    @SerializedName("usuario")
    private Player player;

    public Punctuation() {
    }

    public Long getId_punctuation() {
        return id_punctuation;
    }

    public void setId_punctuation(Long id_punctuation) {
        this.id_punctuation = id_punctuation;
    }

    public Long getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Long punctuation) {
        this.punctuation = punctuation;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

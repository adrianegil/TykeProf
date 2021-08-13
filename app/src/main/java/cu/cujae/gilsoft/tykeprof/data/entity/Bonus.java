package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "bonus", foreignKeys = {
        @ForeignKey(entity = Question.class,
                parentColumns = "id_question",
                childColumns = "id_question", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "id_question")
        })
public class Bonus {

    @PrimaryKey
    @ColumnInfo(name = "id_bonus")
    @SerializedName("id")
    private long id_bonus;

    @ColumnInfo(name = "time_less")
    @SerializedName("tiempoMenor")
    private Integer time_less;

    @ColumnInfo(name = "bonus_cant")
    @SerializedName("bonificacion")
    private Integer bonus_cant;

    @ColumnInfo(name = "id_question")
    private long id_question;

    public Bonus() {
    }

    public long getId_bonus() {
        return id_bonus;
    }

    public void setId_bonus(long id_bonus) {
        this.id_bonus = id_bonus;
    }

    public Integer getTime_less() {
        return time_less;
    }

    public void setTime_less(Integer time_less) {
        this.time_less = time_less;
    }

    public Integer getBonus_cant() {
        return bonus_cant;
    }

    public void setBonus_cant(Integer bonus_cant) {
        this.bonus_cant = bonus_cant;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }
}

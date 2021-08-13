package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "clue", foreignKeys = {
        @ForeignKey(entity = Clue_Type.class,
                parentColumns = "id",
                childColumns = "id_clue_type", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Question.class,
                parentColumns = "id_question",
                childColumns = "id_question", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"id_clue_type"}), @Index(value = "id_question")
        })
public class Clue {

    @PrimaryKey
    @ColumnInfo(name = "id_clue")
    @SerializedName("id")
    private long id_clue;

    @ColumnInfo(name = "help_text")
    @SerializedName("textoAyuda")
    private String help_text;

    @ColumnInfo(name = "id_clue_type")
    private long id_clue_type;

    @ColumnInfo(name = "id_question")
    private long id_question;

    @Ignore
    @SerializedName("tipoPistaByIdTipoPista")
    private Clue_Type clue_type;

    public Clue() {
    }

    public long getId_clue() {
        return id_clue;
    }

    public void setId_clue(long id_clue) {
        this.id_clue = id_clue;
    }

    public String getHelp_text() {
        return help_text;
    }

    public void setHelp_text(String help_text) {
        this.help_text = help_text;
    }

    public long getId_clue_type() {
        return id_clue_type;
    }

    public void setId_clue_type(long id_clue_type) {
        this.id_clue_type = id_clue_type;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }

    public Clue_Type getClue_type() {
        return clue_type;
    }

    public void setClue_type(Clue_Type clue_type) {
        this.clue_type = clue_type;
    }
}

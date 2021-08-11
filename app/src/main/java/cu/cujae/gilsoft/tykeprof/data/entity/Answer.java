package cu.cujae.gilsoft.tykeprof.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "answer", foreignKeys = {
        @ForeignKey(entity = Question.class,
                parentColumns = "id_question",
                childColumns = "id_question", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "id_question")
        })
public class Answer {

    @PrimaryKey
    @ColumnInfo(name = "id_answer")
    @SerializedName("id")
    private long id_answer;

    @ColumnInfo(name = "text")
    @SerializedName("texto")
    private String text;

    @ColumnInfo(name = "punctuation")
    @SerializedName("puntuacion")
    private Integer punctuation;

    @ColumnInfo(name = "correct")
    @SerializedName("correcta")
    private boolean correct;

    @ColumnInfo(name = "order")
    @SerializedName("orden")
    private Integer order;

    @ColumnInfo(name = "text_link")
    @SerializedName("textoEnlazar")
    private String text_link;

    @ColumnInfo(name = "id_question")
    @SerializedName("idPregunta")
    private Integer id_question;

    public Answer() {
    }

    public long getId_answer() {
        return id_answer;
    }

    public void setId_answer(long id_answer) {
        this.id_answer = id_answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Integer punctuation) {
        this.punctuation = punctuation;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getText_link() {
        return text_link;
    }

    public void setText_link(String text_link) {
        this.text_link = text_link;
    }

    public Integer getId_question() {
        return id_question;
    }

    public void setId_question(Integer id_question) {
        this.id_question = id_question;
    }
}

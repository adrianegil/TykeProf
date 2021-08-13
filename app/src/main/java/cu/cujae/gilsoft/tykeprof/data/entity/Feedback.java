package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback", foreignKeys = {
        @ForeignKey(entity = Question.class,
                parentColumns = "id_question",
                childColumns = "id_question", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "id_question")
        })
public class Feedback {

    @PrimaryKey
    @ColumnInfo(name = "id_feedback")
    private long id_feedback;

    @ColumnInfo(name = "feedback_text")
    private String feedback_text;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "id_question")
    private long id_question;

    public Feedback() {
    }

    public long getId_feedback() {
        return id_feedback;
    }

    public void setId_feedback(long id_feedback) {
        this.id_feedback = id_feedback;
    }

    public String getFeedback_text() {
        return feedback_text;
    }

    public void setFeedback_text(String feedback_text) {
        this.feedback_text = feedback_text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }
}

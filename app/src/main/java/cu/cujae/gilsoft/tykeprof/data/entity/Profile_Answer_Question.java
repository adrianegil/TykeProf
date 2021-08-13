package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_answer_question", foreignKeys = {
        @ForeignKey(entity = Answer.class,
                parentColumns = "id_answer",
                childColumns = "id_answer", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Question.class,
                parentColumns = "id_question",
                childColumns = "id_question", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"id_answer"}), @Index(value = "id_question")
        })
public class Profile_Answer_Question {

    @PrimaryKey
    @ColumnInfo(name = "id_profile_ans_quest")
    private long id_profile_ans_quest;

    @ColumnInfo(name = "time_second")
    private Integer time_second;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "id_answer")
    private long id_answer;

    @ColumnInfo(name = "id_question")
    private long id_question;

    public Profile_Answer_Question() {
    }

    public long getId_profile_ans_quest() {
        return id_profile_ans_quest;
    }

    public void setId_profile_ans_quest(long id_profile_ans_quest) {
        this.id_profile_ans_quest = id_profile_ans_quest;
    }

    public Integer getTime_second() {
        return time_second;
    }

    public void setTime_second(Integer time_second) {
        this.time_second = time_second;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId_answer() {
        return id_answer;
    }

    public void setId_answer(long id_answer) {
        this.id_answer = id_answer;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }
}

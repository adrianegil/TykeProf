package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "question", foreignKeys = {
        @ForeignKey(entity = Question_Type.class,
                parentColumns = "id",
                childColumns = "id_question_type", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "id_question_type")
        })
public class Question {

    @PrimaryKey
    @ColumnInfo(name = "id_question")
    @SerializedName("id")
    private long id_question;

    @ColumnInfo(name = "question_title")
    @SerializedName("tituloPregunta")
    private String question_title;

    @ColumnInfo(name = "time_second")
    @SerializedName("tiempoEnSegundos")
    private Integer time_second;

    @ColumnInfo(name = "points_complete_correctly")
    @SerializedName("cantPuntosCompletarCorrectamente")
    private Integer points_complete_correctly;

    @ColumnInfo(name = "subject_name")
    @SerializedName("asignatura")
    private String subject_name;

    @Ignore
    @SerializedName("bonificacionPreguntaTiemposById")
    private List<Bonus> bonusList;

    @Ignore
    @SerializedName("feedbacksById")
    private List<Feedback> feedbackList;

    @Ignore
    @SerializedName("pistasById")
    private List<Clue> clueList;

    @ColumnInfo(name = "id_question_type")
    private long id_question_type;

    @Ignore
    @SerializedName("tipoPreguntaByIdTipoPregunta")
    private Question_Type question_type;

    @Ignore
    @SerializedName("profileRespuestaPreguntasById")
    private List<Profile_Answer_Question> profile_answer_questionList;

    @Ignore
    @SerializedName("respuestasById")
    private List<Answer> answerList;

    @Ignore
    @SerializedName("asignaturaObject")
    private Subject subject;

    @Ignore
    private boolean contenExpandable;

    public Question() {
    }

    public Question_Type getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Question_Type question_type) {
        this.question_type = question_type;
    }

    public long getId_question() {
        return id_question;
    }

    public void setId_question(long id_question) {
        this.id_question = id_question;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public Integer getTime_second() {
        return time_second;
    }

    public void setTime_second(Integer time_second) {
        this.time_second = time_second;
    }

    public Integer getPoints_complete_correctly() {
        return points_complete_correctly;
    }

    public void setPoints_complete_correctly(Integer points_complete_correctly) {
        this.points_complete_correctly = points_complete_correctly;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public List<Bonus> getBonusList() {
        return bonusList;
    }

    public void setBonusList(List<Bonus> bonusList) {
        this.bonusList = bonusList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<Clue> getClueList() {
        return clueList;
    }

    public void setClueList(List<Clue> clueList) {
        this.clueList = clueList;
    }

    public long getId_question_type() {
        return id_question_type;
    }

    public void setId_question_type(long id_question_type) {
        this.id_question_type = id_question_type;
    }

    public List<Profile_Answer_Question> getProfile_answer_questionList() {
        return profile_answer_questionList;
    }

    public void setProfile_answer_questionList(List<Profile_Answer_Question> profile_answer_questionList) {
        this.profile_answer_questionList = profile_answer_questionList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public boolean isContenExpandable() {
        return contenExpandable;
    }

    public void setContenExpandable(boolean contenExpandable) {
        this.contenExpandable = contenExpandable;
    }
}

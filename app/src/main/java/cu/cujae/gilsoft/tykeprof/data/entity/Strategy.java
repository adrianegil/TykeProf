package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.model.GroupList_Model;
import cu.cujae.gilsoft.tykeprof.data.model.QuestionID_Model;
import cu.cujae.gilsoft.tykeprof.data.model.TopicList_Model;

@Entity(tableName = "strategy", foreignKeys = {
        @ForeignKey(entity = Teacher.class,
                parentColumns = "id_teacher",
                childColumns = "id_teacher")},
        indices = {@Index(value = "id_teacher")
        })
public class Strategy {

    @PrimaryKey
    @ColumnInfo(name = "id_strategy")
    @SerializedName("idEstrategia")
    private long id_strategy;

    @ColumnInfo(name = "points")
    @SerializedName("puntos")
    private Integer points;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "date")
    @SerializedName("fecha")
    private String date;

    @ColumnInfo(name = "enabled")
    @SerializedName("habilitada")
    private boolean enabled;

    @ColumnInfo(name = "evaluated")
    @SerializedName("evaluada")
    private boolean evaluated;

    @Ignore
    @SerializedName("profesor")
    private Teacher teacher;

    @Ignore
    @SerializedName("grupos")
    private List<GroupList_Model> groupList_models;

    @Ignore
    @SerializedName("temas")
    private List<TopicList_Model> topicList_models;

    @Ignore
    @SerializedName("estrategiaPreguntasByIdEstrategia")
    private List<QuestionID_Model> questionID_models;

    @ColumnInfo(name = "id_teacher")
    private long id_teacher;

    @Ignore
    private boolean contenExpandable;

    public Strategy() {
    }

    public boolean isContenExpandable() {
        return contenExpandable;
    }

    public void setContenExpandable(boolean contenExpandable) {
        this.contenExpandable = contenExpandable;
    }

    public long getId_strategy() {
        return id_strategy;
    }

    public void setId_strategy(long id_strategy) {
        this.id_strategy = id_strategy;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<GroupList_Model> getGroupList_models() {
        return groupList_models;
    }

    public void setGroupList_models(List<GroupList_Model> groupList_models) {
        this.groupList_models = groupList_models;
    }

    public List<TopicList_Model> getTopicList_models() {
        return topicList_models;
    }

    public void setTopicList_models(List<TopicList_Model> topicList_models) {
        this.topicList_models = topicList_models;
    }

    public List<QuestionID_Model> getQuestionID_models() {
        return questionID_models;
    }

    public void setQuestionID_models(List<QuestionID_Model> questionID_models) {
        this.questionID_models = questionID_models;
    }

    public long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(long id_teacher) {
        this.id_teacher = id_teacher;
    }
}

package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;

public class Strategy_Model {

    @SerializedName("cantPuntos")
    private Integer cantPoints;

    @SerializedName("evaluada")
    private Boolean evaluated;

    @SerializedName("grupos")
    private ArrayList<Group> groupsList;

    @SerializedName("habilitada")
    private Boolean enabled;

    @SerializedName("idProfesor")
    private long id_teacher;

    @SerializedName("nombre")
    private String name;

    @SerializedName("pregunta")
    private ArrayList<Question> questionsList;

    @SerializedName("temas")
    private ArrayList<Topic> topicsList;

    private String subjectName;

    public Strategy_Model() {
        this.groupsList = new ArrayList<>();
        this.questionsList = new ArrayList<>();
        this.topicsList = new ArrayList<>();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCantPoints() {
        return cantPoints;
    }

    public void setCantPoints(Integer cantPoints) {
        this.cantPoints = cantPoints;
    }

    public Boolean getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(Boolean evaluated) {
        this.evaluated = evaluated;
    }

    public ArrayList<Group> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(ArrayList<Group> groupsList) {
        this.groupsList = groupsList;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(long id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public ArrayList<Topic> getTopicsList() {
        return topicsList;
    }

    public void setTopicsList(ArrayList<Topic> topicsList) {
        this.topicsList = topicsList;
    }
}

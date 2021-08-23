package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Question_Model {

    @SerializedName("asignatura")
    private String subject_name;

    @SerializedName("bonificaciones")
    private ArrayList<Bonus_Model> bonusList;

    @SerializedName("pistas")
    private ArrayList<Clue_Model> clueModelList;

    @SerializedName("puntuacion")
    private Integer punctuation;

    @SerializedName("respuestas")
    private ArrayList<Answer_Model> answerModelList;

    @SerializedName("tiempoMenor")
    private Integer time_second;

    @SerializedName("tipoPregunta")
    private String questionType;

    @SerializedName("tituloPregunta")
    private String questionTitle;

    public Question_Model() {
        this.bonusList = new ArrayList<>();
        this.clueModelList = new ArrayList<>();
        this.answerModelList = new ArrayList<>();
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public ArrayList<Bonus_Model> getBonusList() {
        return bonusList;
    }

    public void setBonusList(ArrayList<Bonus_Model> bonusList) {
        this.bonusList = bonusList;
    }

    public ArrayList<Clue_Model> getClueModelList() {
        return clueModelList;
    }

    public void setClueModelList(ArrayList<Clue_Model> clueModelList) {
        this.clueModelList = clueModelList;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Integer punctuation) {
        this.punctuation = punctuation;
    }

    public ArrayList<Answer_Model> getAnswerModelList() {
        return answerModelList;
    }

    public void setAnswerModelList(ArrayList<Answer_Model> answerModelList) {
        this.answerModelList = answerModelList;
    }

    public Integer getTime_second() {
        return time_second;
    }

    public void setTime_second(Integer time_second) {
        this.time_second = time_second;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
}

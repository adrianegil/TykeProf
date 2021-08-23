package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Answer_Model {

    @SerializedName("correcta")
    private boolean correct;

    @SerializedName("fraseFinal")
    private String final_phrase;

    @SerializedName("orden")
    private Integer order;

    @SerializedName("pregunta")
    private String question;

    @SerializedName("puntuacion")
    private Integer punctuation;

    @SerializedName("respuesta")
    private String answer;

    public Answer_Model() {
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getInitial_phrase() {
        return final_phrase;
    }

    public void setInitial_phrase(String final_phrase) {
        this.final_phrase = final_phrase;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(Integer punctuation) {
        this.punctuation = punctuation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class QuestionID_Model {

    @SerializedName("idPregunta")
    private long id_Question;

    public QuestionID_Model() {
    }

    public long getId_Question() {
        return id_Question;
    }

    public void setId_Question(long id_Question) {
        this.id_Question = id_Question;
    }
}

package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Clue_Model {

    @SerializedName("pistaName")
    private String clue_name;

    @SerializedName("tipoPistaSelected")
    private String clue_type_selected;

    public Clue_Model() {
    }

    public Clue_Model(String clue_name, String clue_type_selected) {
        this.clue_name = clue_name;
        this.clue_type_selected = clue_type_selected;
    }

    public String getClue_name() {
        return clue_name;
    }

    public void setClue_name(String clue_name) {
        this.clue_name = clue_name;
    }

    public String getClue_type_selected() {
        return clue_type_selected;
    }

    public void setClue_type_selected(String clue_type_selected) {
        this.clue_type_selected = clue_type_selected;
    }
}

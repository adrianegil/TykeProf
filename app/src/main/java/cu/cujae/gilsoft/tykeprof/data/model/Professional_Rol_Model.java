package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Professional_Rol_Model {

    @SerializedName("idCarrera")
    private long id_career;

    @SerializedName("idTema")
    private long id_topic;

    @SerializedName("nombre")
    private String name_profess_rol;

    public Professional_Rol_Model() {

    }

    public Professional_Rol_Model(long id_career, long id_topic, String name_profess_rol) {
        this.id_career = id_career;
        this.id_topic = id_topic;
        this.name_profess_rol = name_profess_rol;
    }

    public long getId_career() {
        return id_career;
    }

    public void setId_career(long id_career) {
        this.id_career = id_career;
    }

    public long getId_topic() {
        return id_topic;
    }

    public void setId_topic(long id_topic) {
        this.id_topic = id_topic;
    }

    public String getName_profess_rol() {
        return name_profess_rol;
    }

    public void setName_profess_rol(String name_profess_rol) {
        this.name_profess_rol = name_profess_rol;
    }
}

package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Insignia_Model {

    @SerializedName("idRolProfesional")
    private long id_profess_rol;

    @SerializedName("nombre")
    private String name;

    @SerializedName("puntosAvance")
    private Integer advance_points;

    @SerializedName("puntosOtorgar")
    private Integer grant_points;

    public Insignia_Model() {
    }

    public Insignia_Model(long id_profess_rol, String name, Integer advance_points, Integer grant_points) {
        this.id_profess_rol = id_profess_rol;
        this.name = name;
        this.advance_points = advance_points;
        this.grant_points = grant_points;
    }

    public long getId_profess_rol() {
        return id_profess_rol;
    }

    public void setId_profess_rol(long id_profess_rol) {
        this.id_profess_rol = id_profess_rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAdvance_points() {
        return advance_points;
    }

    public void setAdvance_points(Integer advance_points) {
        this.advance_points = advance_points;
    }

    public Integer getGrant_points() {
        return grant_points;
    }

    public void setGrant_points(Integer grant_points) {
        this.grant_points = grant_points;
    }
}

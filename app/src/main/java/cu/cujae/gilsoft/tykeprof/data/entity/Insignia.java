package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "insignia", foreignKeys = {
        @ForeignKey(entity = Professional_Rol.class,
                parentColumns = "id_profess_rol",
                childColumns = "id_profess_rol",onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "id_profess_rol")
        })
public class Insignia {

    @PrimaryKey
    @ColumnInfo(name = "id_insignia")
    @SerializedName("idInsignia")
    private long id_insignia;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "advance_points")
    @SerializedName("puntosAvance")
    private Integer advance_points;

    @ColumnInfo(name = "grant_points")
    @SerializedName("puntosOtorgar")
    private Integer grant_points;

    @ColumnInfo(name = "id_profess_rol")
    private long id_profess_rol;

    @Ignore
    @SerializedName("rolProfesional")
    private Professional_Rol professional_rol;

    @Ignore
    private boolean contenExpandable;

    public Insignia() {
    }

    public long getId_insignia() {
        return id_insignia;
    }

    public void setId_insignia(long id_insignia) {
        this.id_insignia = id_insignia;
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

    public long getId_profess_rol() {
        return id_profess_rol;
    }

    public void setId_profess_rol(long id_profess_rol) {
        this.id_profess_rol = id_profess_rol;
    }

    public Professional_Rol getProfessional_rol() {
        return professional_rol;
    }

    public void setProfessional_rol(Professional_Rol professional_rol) {
        this.professional_rol = professional_rol;
    }

    public boolean isContenExpandable() {
        return contenExpandable;
    }

    public void setContenExpandable(boolean contenExpandable) {
        this.contenExpandable = contenExpandable;
    }
}

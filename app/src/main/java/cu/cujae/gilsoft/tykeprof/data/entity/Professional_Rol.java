package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "professional_rol", foreignKeys = {
        @ForeignKey(entity = Topic.class,
                parentColumns = "id_topic",
                childColumns = "id_topic",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Career.class,
                parentColumns = "id_career",
                childColumns = "id_career",onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"id_topic"}), @Index(value = "id_career")
        })
public class Professional_Rol {

    @PrimaryKey
    @ColumnInfo(name = "id_profess_rol")
    @SerializedName("idRolProfesional")
    private long id_profess_rol;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "id_career")
    @SerializedName("idCarrera")
    private long id_career;

    @ColumnInfo(name = "id_topic")
    @SerializedName("idTema")
    private long id_topic;

    @Ignore
    @SerializedName("tema")
    private Topic topic;

    @Ignore
    @SerializedName("carrera")
    private Career career;

    @Ignore
    private boolean contenExpandable;

    @Ignore
    @Override
    public String toString() {
        return name;
    }

    public Professional_Rol() {
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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public boolean isContenExpandable() {
        return contenExpandable;
    }

    public void setContenExpandable(boolean contenExpandable) {
        this.contenExpandable = contenExpandable;
    }
}

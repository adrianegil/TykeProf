package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "subject")
public class Subject {

    @PrimaryKey
    @ColumnInfo(name = "id_subject")
    @SerializedName("id")
    private long id_subject;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "active")
    @SerializedName("activo")
    private boolean active;

    @Ignore
    @Override
    public String toString() {
        return name;
    }

    public Subject() {
    }

    @Ignore
    public Subject(long id_subject, String name, boolean active) {
        this.id_subject = id_subject;
        this.name = name;
        this.active = active;
    }

    public long getId_subject() {
        return id_subject;
    }

    public void setId_subject(long id_subject) {
        this.id_subject = id_subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

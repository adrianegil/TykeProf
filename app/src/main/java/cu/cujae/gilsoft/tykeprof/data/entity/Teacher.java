package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "teacher")
public class Teacher {

    @PrimaryKey
    @ColumnInfo(name = "id_teacher")
    @SerializedName("id")
    private long id_teacher;

    @ColumnInfo(name = "scientific_grade")
    @SerializedName("gradoCientifico")
    private String scientific_grade;

    @ColumnInfo(name = "teaching_grade")
    @SerializedName("gradoDocente")
    private String teaching_grade;

    @Ignore
    @SerializedName("usuario")
    private User user;

    @ColumnInfo(name = "fullName")
    private String fullName;

    public Teacher() {
    }

    public long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(long id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getScientific_grade() {
        return scientific_grade;
    }

    public void setScientific_grade(String scientific_grade) {
        this.scientific_grade = scientific_grade;
    }

    public String getTeaching_grade() {
        return teaching_grade;
    }

    public void setTeaching_grade(String teaching_grade) {
        this.teaching_grade = teaching_grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

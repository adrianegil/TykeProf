package cu.cujae.gilsoft.tykeprof.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "career")
public class Career {

    @PrimaryKey
    @ColumnInfo(name = "id_career")
    @SerializedName("id")
    private long id_career;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "acronyms")
    @SerializedName("siglas")
    private String acronyms;

    @Ignore
    @Override
    public String toString() {
        return name;
    }

    public Career() {
    }

    @Ignore
    public Career(long id_career, String name, String acronyms) {
        this.id_career = id_career;
        this.name = name;
        this.acronyms = acronyms;
    }

    public long getId_career() {
        return id_career;
    }

    public void setId_career(long id_career) {
        this.id_career = id_career;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }
}

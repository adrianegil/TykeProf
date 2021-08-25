package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "topic")
public class Topic {

    @PrimaryKey
    @ColumnInfo(name = "id_topic")
    @SerializedName("id")
    private long id_topic;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    @ColumnInfo(name = "descrip")
    @SerializedName("description")
    private String descrip;

    @Ignore
    private boolean selected;

    @Ignore
    @Override
    public String toString() {
        return name;
    }

    public Topic() {
        this.selected = false;
    }

    @Ignore
    public Topic(long id_topic, String name, String descrip) {
        this.id_topic = id_topic;
        this.name = name;
        this.descrip = descrip;
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getId_topic() {
        return id_topic;
    }

    public void setId_topic(long id_topic) {
        this.id_topic = id_topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}

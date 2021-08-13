package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "group")
public class Group {

    @PrimaryKey
    @ColumnInfo(name = "id_group")
    @SerializedName("id")
    private long id_group;

    @ColumnInfo(name = "group_name")
    @SerializedName("grupo")
    private String group_name;

    public Group() {
    }

    @Ignore
    public Group(long id_group, String group_name) {
        this.id_group = id_group;
        this.group_name = group_name;
    }

    public long getId_group() {
        return id_group;
    }

    public void setId_group(long id_group) {
        this.id_group = id_group;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}

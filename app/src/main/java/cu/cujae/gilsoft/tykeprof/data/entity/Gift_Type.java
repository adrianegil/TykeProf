package cu.cujae.gilsoft.tykeprof.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gift_type")
public class Gift_Type {

    @PrimaryKey
    @ColumnInfo(name = "id_gift_type")
    @SerializedName("idTipoRegalo")
    private long id_gift_type;

    @ColumnInfo(name = "name")
    @SerializedName("nombre")
    private String name;

    public Gift_Type() {
    }

    @Ignore
    public Gift_Type(String name) {
        this.name = name;
    }

    public long getId_gift_type() {
        return id_gift_type;
    }

    public void setId_gift_type(long id_gift_type) {
        this.id_gift_type = id_gift_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "grant")
public class Grant {

    @PrimaryKey
    @ColumnInfo(name = "id_grant")
    @SerializedName("idOtorgamiento")
    private long id_grant;

    @ColumnInfo(name = "cant_points")
    @SerializedName("cantPuntos")
    private Integer cant_points;

    @ColumnInfo(name = "grant_name")
    @SerializedName("otorgamiento")
    private String grant_name;

    @Ignore
    @Override
    public String toString() {
        return grant_name;
    }

    public Grant() {
    }

    @Ignore
    public Grant(Integer cant_points, String grant_name) {
        this.cant_points = cant_points;
        this.grant_name = grant_name;
    }

    public long getId_grant() {
        return id_grant;
    }

    public void setId_grant(long id_grant) {
        this.id_grant = id_grant;
    }

    public Integer getCant_points() {
        return cant_points;
    }

    public void setCant_points(Integer cant_points) {
        this.cant_points = cant_points;
    }

    public String getGrant_name() {
        return grant_name;
    }

    public void setGrant_name(String grant_name) {
        this.grant_name = grant_name;
    }
}

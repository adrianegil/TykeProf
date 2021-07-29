package cu.cujae.gilsoft.tykeprof.data.entity;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "gift", foreignKeys = {
        @ForeignKey(entity = Gift_Type.class,
                parentColumns = "id_gift_type",
                childColumns = "id_gift_type",onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Grant.class,
                parentColumns = "id_grant",
                childColumns = "id_grant",onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"id_gift_type"}), @Index(value = "id_grant")
        })
public class Gift {

    @PrimaryKey
    @ColumnInfo(name = "id_gift")
    @SerializedName("idRegalo")
    private long id_gift;

    @ColumnInfo(name = "descrip")
    @SerializedName("descripcion")
    private String descrip;

    @ColumnInfo(name = "id_gift_type")
    private long id_gift_type;

    @ColumnInfo(name = "id_grant")
    private long id_grant;

    @Ignore
    @SerializedName("tipoRegalo")
    private Gift_Type gift_type;

    @Ignore
    @SerializedName("otorgamiento")
    private Grant grant;

    @Ignore
    private boolean contenExpandable;

    public Gift() {
        this.contenExpandable = false;
    }

    @Ignore
    public Gift(long id_gift, String descrip, long id_gift_type, long id_grant) {
        this.id_gift = id_gift;
        this.descrip = descrip;
        this.id_gift_type = id_gift_type;
        this.id_grant = id_grant;
        this.contenExpandable = false;
    }

    public long getId_gift() {
        return id_gift;
    }

    public void setId_gift(long id_gift) {
        this.id_gift = id_gift;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public long getId_gift_type() {
        return id_gift_type;
    }

    public void setId_gift_type(long id_gift_type) {
        this.id_gift_type = id_gift_type;
    }

    public long getId_grant() {
        return id_grant;
    }

    public void setId_grant(long id_grant) {
        this.id_grant = id_grant;
    }

    public Gift_Type getGift_type() {
        return gift_type;
    }

    public void setGift_type(Gift_Type gift_type) {
        this.gift_type = gift_type;
    }

    public Grant getGrant() {
        return grant;
    }

    public void setGrant(Grant grant) {
        this.grant = grant;
    }

    public boolean isContenExpandable() {
        return contenExpandable;
    }

    public void setContenExpandable(boolean contenExpandable) {
        this.contenExpandable = contenExpandable;
    }
}

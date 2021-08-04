package cu.cujae.gilsoft.tykeprof.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "role", foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id_user",
                childColumns = "id_user")},
        indices = {@Index(value = "id_user")
        })
public class Role {

    @PrimaryKey
    @ColumnInfo(name = "id_role")
    @SerializedName("id")
    private long id_role;

    @ColumnInfo(name = "roleName")
    @SerializedName("roleName")
    private String roleName;

    @ColumnInfo(name = "descrip")
    @SerializedName("description")
    private String descrip;

    @ColumnInfo(name = "id_user")
    private long id_user;

    public Role() {
    }

    public long getId_role() {
        return id_role;
    }

    public void setId_role(long id_role) {
        this.id_role = id_role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}

package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Gift_Model {

    @SerializedName("idRegalo")
    private long id_gift;

    @SerializedName("descripcion")
    private String descrip;

    @SerializedName("idTipoRegalo")
    private String id_gift_typeName;

    @SerializedName("idOtorgamiento")
    private String id_grant_Name;

    public Gift_Model() {
    }

    public Gift_Model(String descrip, String id_gift_typeName, String id_grant_Name) {
        this.descrip = descrip;
        this.id_gift_typeName = id_gift_typeName;
        this.id_grant_Name = id_grant_Name;
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

    public String getId_gift_typeName() {
        return id_gift_typeName;
    }

    public void setId_gift_typeName(String id_gift_typeName) {
        this.id_gift_typeName = id_gift_typeName;
    }

    public String getId_grant_Name() {
        return id_grant_Name;
    }

    public void setId_grant_Name(String id_grant_Name) {
        this.id_grant_Name = id_grant_Name;
    }
}

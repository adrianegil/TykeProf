package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;

public class GroupList_Model {

    @SerializedName("grupo")
    private Group group;

    public GroupList_Model() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

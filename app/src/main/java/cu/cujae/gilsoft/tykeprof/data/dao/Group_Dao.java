package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Group;

@Dao
public interface Group_Dao {

    @Query("SELECT * FROM `group`")
    List<Group> getAllGroupList();

    @Query("SELECT * FROM `group` WHERE id_group =:id")
    Group getGroupById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllGroups(List<Group> groupList);

    @Query("DELETE FROM `group` WHERE id_group = :id")
    void deleteGroupByID(long id);

    @Query("DELETE FROM `group`")
    void deleteAll();

}

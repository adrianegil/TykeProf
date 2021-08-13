package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Role;

@Dao
public interface Role_Dao {

    @Query("SELECT * FROM role WHERE id_user =:id ")
    List<Role> getAllRoleList(long id);

    @Query("SELECT * FROM role WHERE id_role =:id")
    Role getRoleById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveAllRoles(List<Role> roleList);

    @Query("DELETE FROM role WHERE id_role = :id")
    void deleteRoleByID(long id);

    @Query("DELETE FROM role")
    void deleteAll();
}

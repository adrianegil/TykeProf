package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import cu.cujae.gilsoft.tykeprof.data.entity.User;

@Dao
public interface User_Dao {

    @Query("SELECT * FROM user")
    LiveData<User> getLiveDataUser();

    @Query("SELECT * FROM user WHERE id_user =:id")
    User getUserById(long id);

    @Query("SELECT * FROM user")
    User getUser();

    @Query("SELECT * FROM user WHERE id_user =:id")
    LiveData<User> getLiveDataUserById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUser(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveUserIgnore(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM gift_type")
    void deleteAll();
}

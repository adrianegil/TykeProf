package cu.cujae.gilsoft.tykeprof.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import cu.cujae.gilsoft.tykeprof.data.entity.Punctuation;

@Dao
public interface Punctuation_Dao {

    @Query("SELECT * FROM punctuation ORDER BY punctuation DESC")
    LiveData<List<Punctuation>> getAllPunctuations();

    @Query("SELECT * FROM punctuation ORDER BY punctuation ASC")
    List<Punctuation> getPunctuationListAsc();

    @Query("SELECT * FROM punctuation ORDER BY punctuation DESC")
    List<Punctuation> getPunctuationListDesc();

    @Query("SELECT * FROM punctuation WHERE id_punctuation =:id")
    Punctuation getPunctuationById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllPunctuations(List<Punctuation> punctuationList);

    @Query("DELETE FROM punctuation WHERE id_punctuation = :id")
    void deletePunctuationByID(long id);

    @Query("DELETE FROM punctuation")
    void deleteAll();

}

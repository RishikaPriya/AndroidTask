package task.avantari.com.optimizedatabase.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import task.avantari.com.optimizedatabase.model.Dictionary;

@Dao
public interface DictionaryDao {
    @Query("SELECT * FROM dictionary")
    List<Dictionary> getAll();

    @Insert
    void insertAll(Dictionary... dictionary);

    @Query("DELETE FROM dictionary")
    void deleteTable();
}

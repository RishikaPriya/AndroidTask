package task.avantari.com.optimizedatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import task.avantari.com.optimizedatabase.dao.DictionaryDao;
import task.avantari.com.optimizedatabase.model.Dictionary;

@Database(entities = {Dictionary.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase instance;

    public abstract DictionaryDao dictionaryDao();

    public static ApplicationDatabase getAppDB(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ApplicationDatabase.class,"dictionary-database")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

}

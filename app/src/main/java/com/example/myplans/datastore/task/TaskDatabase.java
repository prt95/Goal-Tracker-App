package com.example.myplans.datastore.task;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 2)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

    private static final String DB_NAME = "task";
    private static volatile TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
//kept for testing purpose
//            instance.taskDao().deleteAll();
        }
        return instance;
    }

    public TaskDatabase() {};

    private static TaskDatabase create(final Context context) {
        //fallback will loose all data
        //when changing tables schema provide a migration
         return  Room.databaseBuilder(context, TaskDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

    }
}

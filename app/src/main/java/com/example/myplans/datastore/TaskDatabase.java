package com.example.myplans.datastore;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

    private static final String DB_NAME = "task";
    private static volatile TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public TaskDatabase() {};

    private static TaskDatabase create(final Context context) {
         return  Room.databaseBuilder(context, TaskDatabase.class, DB_NAME).allowMainThreadQueries().build();

    }
}

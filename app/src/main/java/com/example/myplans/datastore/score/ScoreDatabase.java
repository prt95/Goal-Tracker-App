package com.example.myplans.datastore.score;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Score.class}, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();

    private static final String DB_NAME = "score";
    private static volatile ScoreDatabase instance;

    public static synchronized ScoreDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public ScoreDatabase() {};

    private static ScoreDatabase create(final Context context) {
         return  Room.databaseBuilder(context, ScoreDatabase.class, DB_NAME).allowMainThreadQueries().build();

    }
}

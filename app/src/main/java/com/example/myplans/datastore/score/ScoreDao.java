package com.example.myplans.datastore.score;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score")
    List<Score> getAll();

    @Query("SELECT * FROM score WHERE name IS :taskName")
    List<Score> loadAllByIds(String taskName);


    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);
}


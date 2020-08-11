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
    List<Score> getScoreByName(String taskName);

    @Query("SELECT * FROM score WHERE name IS :taskName and epoch between :epoch and :epoch+86400000")
    Score getScoreForDay(String taskName, long epoch);

    @Query("UPDATE score SET score=:score WHERE name IS :taskName and epoch IS :epoch")
    void updateScore(String taskName, int score, long epoch);

    @Query("SELECT * FROM score WHERE name IS :taskName and epoch between :startEpoch and :endEpoch")
    List<Score> getScoresInRange(String taskName, long startEpoch, long endEpoch);


    @Insert
    void insertAll(Score... scores);

    @Delete
    void delete(Score score);

    @Query("DELETE FROM score")
    void deleteAll();
}


package com.example.myplans.datastore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE name IS :taskName")
    List<Task> loadAllByIds(String taskName);


    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task user);
}


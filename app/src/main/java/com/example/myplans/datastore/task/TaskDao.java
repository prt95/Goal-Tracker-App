package com.example.myplans.datastore.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myplans.datastore.task.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE name IS :taskName")
    Task getTaskByName(String taskName);


    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task user);

    @Query("DELETE FROM task")
    void deleteAll();

    @Query("UPDATE task SET name=:newTaskName , notes=:notes , goal=:goal , reminder_time=:reminderTime WHERE name IS :taskName")
    void update(String newTaskName, String notes, int goal, long reminderTime,String taskName);

}


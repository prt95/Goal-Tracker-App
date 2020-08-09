package com.example.myplans.datastore.task;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
    @PrimaryKey
    @NonNull
    private String name;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "goal")
    private Integer goal;

    @ColumnInfo(name = "reminder_time")
    private Long reminderTime;


    public Long getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Long reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }
}

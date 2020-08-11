package com.example.myplans.datastore.score;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"name", "epoch"})
public class Score {
    @NonNull
    private String name;

    @NonNull
    @ColumnInfo(name = "epoch")
    private Long epoch;

    @NonNull
    @ColumnInfo(name = "score")
    private Integer score;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEpoch() {
        return epoch;
    }

    public void setEpoch(Long epoch) {
        this.epoch = epoch;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

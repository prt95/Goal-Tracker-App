package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myplans.datastore.task.Task;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        ((TextView)findViewById(R.id.detail_task_name)).setText(task.getName());
        ((TextView)findViewById(R.id.detail_task_notes)).setText(task.getNotes());
        ((TextView)findViewById(R.id.detail_task_goal)).setText("Goal score: "+String.valueOf(task.getGoal()));
        Long timeVal = task.getReminderTime();
        long num =(timeVal / 60);
        long hr = num/60;
        long min = num%60;

        ((TextView)findViewById(R.id.detail_reminder_time)).setText("Reminder At: "+hr+ ":"+min);
    }

}
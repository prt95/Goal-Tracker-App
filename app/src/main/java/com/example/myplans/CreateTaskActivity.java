package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myplans.datastore.Task;
import com.example.myplans.datastore.TaskDatabase;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
    }

    public void listTasks(View v){
        Task task = new Task();
        task.setName(((EditText)findViewById(R.id.task_name)).getText().toString());
        task.setNotes(((EditText)findViewById(R.id.task_notes)).getText().toString());
        task.setGoal(Integer.valueOf(((EditText)findViewById(R.id.task_goal)).getText().toString()));
        TaskDatabase.getInstance(getApplicationContext()).taskDao().insertAll(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myplans.adapters.TaskListAdapter;
import com.example.myplans.datastore.Task;
import com.example.myplans.datastore.TaskDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.task_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // specify an adapter (see also next example)
        List<Task> tasksList = TaskDatabase.getInstance(getApplicationContext()).taskDao().getAll();
        List<String> list = new ArrayList<>();
        for(Task task: tasksList){
            list.add(task.getName());
        }
        list.add("last");
        recyclerView.setAdapter(new TaskListAdapter(list));
    }

    public void createTask(View v){
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }
}
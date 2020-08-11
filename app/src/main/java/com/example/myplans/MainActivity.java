package com.example.myplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myplans.adapters.TaskListAdapter;
import com.example.myplans.datastore.task.Task;
import com.example.myplans.datastore.task.TaskDatabase;

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
        recyclerView.setAdapter(new TaskListAdapter(list));
    }

    public void createTask(View v){
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }
    public void showTask(View v){
        String taskName="";
        if(v instanceof TextView)
         taskName = (String) ((TextView)v).getText();
        else
           taskName= (String) ((TextView)((LinearLayout)v).getChildAt(0)).getText();
        Task task = TaskDatabase.getInstance(getApplicationContext()).taskDao().getTaskByName(taskName);

        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }
}
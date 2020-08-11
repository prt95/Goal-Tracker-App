package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.util.StringUtil;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myplans.datastore.task.Task;
import com.example.myplans.datastore.task.TaskDatabase;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private Task task;

    public Task getTask() {

        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Intent intent = getIntent();
        Task task = new Task();
        if(intent.hasExtra("task")){
            task = (Task) intent.getSerializableExtra("task");
            setTask(task);
            ((EditText)findViewById(R.id.create_task_name)).setText(task.getName());
            ((EditText)findViewById(R.id.task_notes)).setText(task.getNotes());
            ((EditText)findViewById(R.id.task_goal)).setText(String.valueOf(task.getGoal()));
            Long timeVal = task.getReminderTime();
            long num = (timeVal / 60);
            long hr = num / 60;
            long min = num % 60;
            ((TextView) findViewById(R.id.task_reminder_time)).setText(hr + ":" + min);
        }
    }

    public void saveTask(View v){
        Task task = getTask();
        boolean isEdit = false;
        String oldName = "";
        if(task != null){
            isEdit = true;
            oldName = task.getName();
        }
        else {
            task = new Task();
        }
        task.setName(((EditText)findViewById(R.id.create_task_name)).getText().toString());
        task.setNotes(((EditText)findViewById(R.id.task_notes)).getText().toString());
        task.setGoal(Integer.valueOf(((EditText)findViewById(R.id.task_goal)).getText().toString()));
        String time = (String) ((TextView)findViewById(R.id.task_reminder_time)).getText();
        String [] timeAr = time.split(":");
        Long timeVal = Long.valueOf(timeAr[0])*3600 + 60* Long.valueOf(timeAr[1]);
        task.setReminderTime(timeVal);
        if(!isEdit)
        TaskDatabase.getInstance(getApplicationContext()).taskDao().insertAll(task);
        else {
            TaskDatabase.getInstance(getApplicationContext()).taskDao().update(task.getName(),task.getNotes(),task.getGoal(),task.getReminderTime(), oldName);
        }
        Intent intent = new Intent(this, TaskCalendarActivity.class);
        startActivity(intent);
    }

    public  void pickTime(View v){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        ((TextView)findViewById(R.id.task_reminder_time)).setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}
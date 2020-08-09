package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;

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
        String time = (String) ((TextView)findViewById(R.id.task_reminder_time)).getText();
        String [] timeAr = time.split(":");
        Long timeVal = Long.valueOf(timeAr[0])*3600 + 60* Long.valueOf(timeAr[1]);
        task.setReminderTime(timeVal);
        TaskDatabase.getInstance(getApplicationContext()).taskDao().insertAll(task);
        Intent intent = new Intent(this, MainActivity.class);
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
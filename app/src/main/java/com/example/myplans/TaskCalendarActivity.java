package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myplans.datastore.score.Score;
import com.example.myplans.datastore.score.ScoreDao;
import com.example.myplans.datastore.score.ScoreDatabase;
import com.example.myplans.datastore.task.Task;
import com.example.myplans.datastore.task.TaskDao;
import com.example.myplans.datastore.task.TaskDatabase;
import com.example.myplans.pojo.DateHolder;
import com.example.myplans.util.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;

public class TaskCalendarActivity extends AppCompatActivity {

    private List<Task> allTasks;
    private int leftidx = 0;
    private int rightidx = 0;
    private String currentTaskName;
    private Task currentTask;

    private ScoreDao scoreDao;
    private TaskDao taskDao;

    private CalendarView taskCalendar;
    private SeekBar seekBar;

    ImageButton leftShiftButtonView;
    ImageButton rightShiftButtonView;
    TextView taskNameView;
    TextView notesView;
    TextView goalView;


    private static DateHolder currentDate;

    static {
        currentDate = Utils.getCurrentDateHolder();
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
        setCurrentTaskName(currentTask.getName());
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public String getCurrentTaskName() {
        return currentTaskName;
    }

    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_calendar);

        scoreDao = ScoreDatabase.getInstance(getApplicationContext()).scoreDao();
        taskDao = TaskDatabase.getInstance(getApplicationContext()).taskDao();
        seekBar = ((SeekBar) findViewById(R.id.scorebar));
        taskCalendar = ((CalendarView) findViewById(R.id.calendarView));
        initializeUIComponents();
        initializeListeners();

        allTasks = taskDao.getAll();

        if (allTasks.size() == 0) {
            hideWhenNoTask();
        } else {
            showWhenNonZeroTasks();
            setCurrentTask(allTasks.get(0));
            setInitialLimits(currentTaskName);
            selectedTask(taskDao.getTaskByName(currentTaskName));
        }


    }

    private void initializeUIComponents() {
        leftShiftButtonView = ((ImageButton) findViewById(R.id.calendar_leftshift_button));
        rightShiftButtonView = ((ImageButton) findViewById(R.id.calendar_rightshift_button));
        taskNameView = ((TextView) findViewById(R.id.create_task_name));
        goalView = ((TextView) findViewById(R.id.goal_text));
        notesView = ((TextView) findViewById(R.id.notes_text));
    }

    private void hideWhenNoTask() {
        int childCount = ((ConstraintLayout) findViewById(R.id.main_page_layout)).getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = ((ConstraintLayout) findViewById(R.id.main_page_layout)).getChildAt(i);
            v.setVisibility(View.INVISIBLE);
        }
        ((ImageButton) findViewById(R.id.score_save)).setVisibility(View.INVISIBLE);
        ((FloatingActionButton) findViewById(R.id.addTask)).setVisibility(View.VISIBLE);
        notesView.setText(R.string.add_first);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notesView.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
        notesView.setVisibility(View.VISIBLE);

    }

    private void showWhenNonZeroTasks() {
        int childCount = ((ConstraintLayout) findViewById(R.id.main_page_layout)).getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = ((ConstraintLayout) findViewById(R.id.main_page_layout)).getChildAt(i);
            v.setVisibility(View.VISIBLE);
        }
        ((ImageButton) findViewById(R.id.score_save)).setVisibility(View.INVISIBLE);
    }


    private void initializeListeners() {

        taskCalendar.setOnDateChangeListener((CalendarView view, int year,
                                              int month, int dayOfMonth) -> {
            currentDate = new DateHolder(year, month, dayOfMonth);

            calendarClicked(view, year, month, dayOfMonth);

        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView) findViewById(R.id.score_text)).setText("Score: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void setInitialLimits(String taskName) {

        int idx = 0;
        for (Task task : allTasks) {
            if (task.getName().equals(taskName)) {
                break;
            }
            idx++;
        }
        leftidx = idx - 1;
        rightidx = idx + 1;
        seekBar.setEnabled(false);
    }

    public void leftShiftTask(View v) {
        leftidx--;
        rightidx--;
        selectedTask(allTasks.get(leftidx + 1));

    }

    public void rightShiftTask(View v) {
        rightidx++;
        leftidx++;
        selectedTask(allTasks.get(rightidx - 1));

    }

    private void updateShiftButtonStatus() {
        if (leftidx < 0) {
            leftShiftButtonView.setClickable(false);
        } else {
            leftShiftButtonView.setClickable(true);
        }
        if (rightidx >= allTasks.size()) {
            rightShiftButtonView.setClickable(false);

        } else {
            rightShiftButtonView.setClickable(true);
        }
    }

    private void selectedTask(Task task) {
        setCurrentTask(task);
        setCurrentTaskName(task.getName());
        taskNameView.setText(task.getName());
        goalView.setText("Goal: " + task.getGoal());
        notesView.setText(task.getNotes());


        updateShiftButtonStatus();
        seekBar.setMax(task.getGoal());
        updateScore();
        seekBar.setActivated(false);
    }

    public void calendarClicked(CalendarView v, int year, int month, int dayOfMonth) {
        updateScore();
    }

    public void editScore(View v) {
        seekBar.setEnabled(true);
        ((ImageButton) v).setVisibility(View.INVISIBLE);
        ((ImageButton) findViewById(R.id.score_save)).setVisibility(View.VISIBLE);
    }

    public void saveScore(View v) {
        seekBar.setEnabled(false);
        int progress = seekBar.getProgress();
        long epoch = getEpochOfStartOfTheDay(currentDate);
        Score score = scoreDao.getScoreForDay(getCurrentTaskName(), epoch);
        if (score == null) {
            score = new Score();
            score.setName(getCurrentTaskName());
            score.setScore(progress);
            score.setEpoch(epoch);
            scoreDao.insertAll(score);
        } else {
            score.setScore(progress);
            scoreDao.updateScore(getCurrentTaskName(), progress, score.getEpoch());
        }

        ((TextView) findViewById(R.id.score_text)).setText("Score: " + score.getScore());

        ((ImageButton) v).setVisibility(View.INVISIBLE);
        ((ImageButton) findViewById(R.id.edit_score)).setVisibility(View.VISIBLE);

    }

    private void updateScore() {
        Score score = scoreDao.getScoreForDay(getCurrentTaskName(), getEpochOfStartOfTheDay(currentDate));
        int progress = score == null ? 0 : score.getScore();

        seekBar.setProgress(progress);
        ((TextView) findViewById(R.id.score_text)).setText("Score: " + progress);
    }

    private long getEpochOfStartOfTheDay(DateHolder date) {
        return Utils.getEpochOfStartOfTheDay(date);
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    public void editTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra("task", getCurrentTask());
        startActivity(intent);
    }
}
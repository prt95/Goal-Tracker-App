package com.example.myplans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myplans.datastore.score.Score;
import com.example.myplans.datastore.score.ScoreDao;
import com.example.myplans.datastore.score.ScoreDatabase;
import com.example.myplans.datastore.task.Task;
import com.example.myplans.pojo.DateHolder;
import com.example.myplans.util.Utils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.myplans.Constants.IST_TIME_DIFF;
import static com.example.myplans.Constants.ONE_DAY;

public class TaskDetailActivity extends AppCompatActivity {

    private Task task;
    private ScoreDao scoreDao;
    private static DateHolder currentDate;

    static {
        currentDate = Utils.getCurrentDateHolder();
    }

    public void setTask(Task task) {
        this.task = task;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        scoreDao = ScoreDatabase.getInstance(getApplicationContext()).scoreDao();

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        setTask(task);
        showTaskDetails(task);

        createGraph(5);
    }

    private void showTaskDetails(Task task) {
        ((TextView) findViewById(R.id.detail_task_name)).setText(task.getName());
        ((TextView) findViewById(R.id.detail_task_notes)).setText(task.getNotes());
        ((TextView) findViewById(R.id.detail_task_goal)).setText("Goal score: " + String.valueOf(task.getGoal()));
        Long timeVal = task.getReminderTime();
        long num = (timeVal / 60);
        long hr = num / 60;
        long min = num % 60;
        ((TextView) findViewById(R.id.detail_reminder_time)).setText("Reminder At: " + hr + ":" + min);
    }

    private void createGraph(int days) {
        long start = Utils.getEpochOfStartOfTheDay(currentDate);
        Calendar calendar = Utils.getCalendarInstanceFromDateHolder(currentDate);
        calendar.add(Calendar.DATE, days);
        long end = Utils.getEpochOfStartOfTheDay(Utils.getDateHolderFromCalendar(calendar));

        List<Score> scores = scoreDao.getScoresInRange(task.getName(), start, end);
        Collections.sort(scores, (s1, s2)->{
            long diff = (s1.getEpoch()-s2.getEpoch());
            if(diff>0) return 1;
            if(diff<0) return -1;
            return 0;
        });
        int minScore = Integer.MAX_VALUE, maxScore = 0;

        for(Score score: scores){
            if (score.getScore() > maxScore) {
                maxScore = score.getScore();
            }
            if (score.getScore() < minScore) {
                minScore = score.getScore();
            }
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        try {


            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            int idx =0;
            for (int i = 0; i < days; i++) {
                long epoch = start + i * ONE_DAY;
                int scoreVal = 0;
                if(idx< scores.size() && scores.get(idx).getEpoch()== epoch){
                    scoreVal =  scores.get(idx).getScore();
                    idx++;
                }
                series.appendData(new DataPoint(new Date(epoch), scoreVal), true, 5);

            }
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));

            graph.addSeries(series);
            graph.getViewport().setMinX(start);
            graph.getViewport().setMaxX(end);
            graph.getViewport().setXAxisBoundsManual(true);

//            graph.getViewport()

            if (minScore == Integer.MAX_VALUE) {
                minScore = 0;
            }
            graph.getViewport().setMinY(minScore);
            graph.getViewport().setMaxY(maxScore);

//            graph.getViewport().ste
            graph.getViewport().setYAxisBoundsManual(true);

            graph.getGridLabelRenderer().setHumanRounding(false);

        } catch (IllegalArgumentException e) {
        }

    }

    public void showCalendarView(View view) {
        Intent intent = new Intent(this, TaskCalendarActivity.class);
        intent.putExtra("taskName", task.getName());
        startActivity(intent);
    }

}
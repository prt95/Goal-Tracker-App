package com.example.myplans.util;

import com.example.myplans.TaskCalendarActivity;
import com.example.myplans.pojo.DateHolder;

import java.util.Calendar;

public class Utils {
    private Utils(){}


    public static long getEpochOfStartOfTheDay(DateHolder date) {
        int year = date.getYear();
        int month = date.getMonth();
        int dayOfMonth = date.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTimeInMillis();
    }

    public static DateHolder getCurrentDateHolder(){
        Calendar calendar = Calendar.getInstance();
        return new DateHolder(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static Calendar getCalendarInstanceFromDateHolder(DateHolder date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        return calendar;
    }

    public static DateHolder getDateHolderFromCalendar(Calendar calendar){
        DateHolder dateHolder = new DateHolder(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return dateHolder;
    }

}

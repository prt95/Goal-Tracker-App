package com.example.myplans.pojo;

    public class DateHolder {
        int month;
        int year;
        int dayOfMonth;

        public DateHolder(int year, int month, int dayOfMonth) {
            this.year = year;
            this.month = month;
            this.dayOfMonth = dayOfMonth;
        }

        public int getMonth() {
            return month;
        }


        public int getYear() {
            return year;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }
    }

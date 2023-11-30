package com.example.medicationapp;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromBooleanArray(boolean[] days) {
        if (days == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (boolean b : days) {
            sb.append(b ? "1" : "0").append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static boolean[] toBooleanArray(String daysString) {
        if (daysString == null) {
            return null;
        }
        String[] dayStringArray = daysString.split(",");
        boolean[] days = new boolean[dayStringArray.length];
        for (int i = 0; i < dayStringArray.length; i++) {
            days[i] = dayStringArray[i].equals("1");
        }
        return days;
    }
}

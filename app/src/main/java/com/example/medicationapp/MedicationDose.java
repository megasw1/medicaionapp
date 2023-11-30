package com.example.medicationapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "medication_dose")
public class MedicationDose {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String medicationName;
    private boolean[] selectedDays = new boolean[7];
    private int hour;
    private int minute;

    // medicationName에 대한 getter와 setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    // selectedDays에 대한 getter와 setter
    public boolean[] getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(boolean[] selectedDays) {
        this.selectedDays = selectedDays;
    }

    // hour에 대한 getter와 setter
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    // minute에 대한 getter와 setter
    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    // ... 기존 필드들에 대한 getter와 setter
}

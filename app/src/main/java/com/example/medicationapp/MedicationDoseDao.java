package com.example.medicationapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MedicationDoseDao {
    @Insert
    void insert(MedicationDose medicationDose);

    @Query("SELECT * FROM medication_dose")
    List<MedicationDose> getAll();
}

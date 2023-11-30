package com.example.medicationapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MedicationRecordActivity extends AppCompatActivity {
    private ListView medicationRecordListView;
    private ArrayAdapter<String> adapter;
    private List<String> medicationRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_record);

        medicationRecordListView = findViewById(R.id.medicationRecordListView);
        medicationRecordList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicationRecordList);
        medicationRecordListView.setAdapter(adapter);

        loadMedicationRecords();
    }

    private void loadMedicationRecords() {
        new AsyncTask<Void, Void, List<MedicationDose>>() {
            @Override
            protected List<MedicationDose> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                return db.medicationDoseDao().getAll();
            }

            @Override
            protected void onPostExecute(List<MedicationDose> medicationDoses) {
                for (MedicationDose dose : medicationDoses) {
                    String record = formatMedicationRecord(dose);
                    medicationRecordList.add(record);
                }
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private String formatMedicationRecord(MedicationDose dose) {
        // 요일 텍스트로 변환
        String daysText = convertDaysToText(dose.getSelectedDays());

        // 시간 형식 설정
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", dose.getHour(), dose.getMinute());

        return dose.getMedicationName() + ", " + daysText + ", " + timeFormatted;
    }

    private String convertDaysToText(boolean[] selectedDays) {
        String[] dayNames = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};
        StringBuilder daysText = new StringBuilder();

        for (int i = 0; i < selectedDays.length; i++) {
            if (selectedDays[i]) {
                daysText.append(dayNames[i]).append(", ");
            }
        }

        // 마지막 쉼표 제거
        if (daysText.length() > 0) {
            daysText.setLength(daysText.length() - 2);
        }

        return daysText.toString();
    }
}

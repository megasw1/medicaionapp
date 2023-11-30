package com.example.medicationapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.Calendar;

public class MedicationReminderActivity extends AppCompatActivity {
    private EditText medicationNameEditText;
    private CheckBox checkBoxMonday, checkBoxTuesday, checkBoxWednesday,
            checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday;
    private TimePicker timePicker;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_reminder);

        // 약 이름 입력 EditText 연결
        medicationNameEditText = findViewById(R.id.medicationNameEditText);

        // 요일 선택 CheckBox 연결

        checkBoxSunday = findViewById(R.id.checkBoxSunday);
        checkBoxMonday = findViewById(R.id.checkBoxMonday);
        checkBoxTuesday = findViewById(R.id.checkBoxTueday);
        checkBoxWednesday = findViewById(R.id.checkBoxWedday);
        checkBoxThursday = findViewById(R.id.checkBoxTruday);
        checkBoxFriday = findViewById(R.id.checkBoxFriday);
        checkBoxSaturday = findViewById(R.id.checkBoxSatday);


        // 알람 시간 설정 TimePicker 연결
        timePicker = findViewById(R.id.timePicker);

        // 설정 저장 버튼 연결 및 클릭 리스너 설정
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicationReminder();
            }
        });
    }

    private void saveMedicationReminder() {
        // UI에서 데이터 추출
        String medicationName = medicationNameEditText.getText().toString();
        boolean[] daysSelected = new boolean[7];
        daysSelected[0] = checkBoxMonday.isChecked();
        daysSelected[1] = checkBoxTuesday.isChecked();
        daysSelected[2] = checkBoxWednesday.isChecked();
        daysSelected[3] = checkBoxThursday.isChecked();
        daysSelected[4] = checkBoxFriday.isChecked();
        daysSelected[5] = checkBoxSaturday.isChecked();
        daysSelected[6] = checkBoxSunday.isChecked();

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        MedicationDose dose = new MedicationDose();
        dose.setMedicationName(medicationName);
        dose.setSelectedDays(daysSelected);
        dose.setHour(hour);
        dose.setMinute(minute);

        saveMedicationDose(dose);
    }

    private void saveMedicationDose(MedicationDose dose) {
        // 데이터베이스 인스턴스 및 DAO 가져오기
        new AsyncTask<MedicationDose, Void, Void>() {
            @Override
            protected Void doInBackground(MedicationDose... doses) {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.medicationDoseDao().insert(doses[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(MedicationReminderActivity.this, "약 알림 저장됨", Toast.LENGTH_SHORT).show();
            }
        }.execute(dose);
    }
    private void setMedicationReminder(Calendar reminderTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderTime.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime.getTimeInMillis(), pendingIntent);
        }
    }
}
package com.katza.calmind;

import android.os.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_add_event);

        EditText title = findViewById(R.id.etTitle);
        DatePicker dp = findViewById(R.id.datePicker);
        TimePicker tp = findViewById(R.id.timePicker);
        Button save = findViewById(R.id.btnSave);

        save.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            c.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
                    tp.getHour(), tp.getMinute());

            new Thread(() -> {
                try {
                    CalendarHelper.addEvent(this,
                            title.getText().toString(), c.getTime());
                    runOnUiThread(() ->
                            Toast.makeText(this, "נוסף ליומן ✔", Toast.LENGTH_SHORT).show()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}

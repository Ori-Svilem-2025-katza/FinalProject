package com.katza.calmind;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_home);

        TextView tv = findViewById(R.id.tvName);
        Button add = findViewById(R.id.btnAdd);
        Button out = findViewById(R.id.btnLogout);

        tv.setText("שלום " + getIntent().getStringExtra("name"));

        add.setOnClickListener(v ->
                startActivity(new Intent(this, AddEventActivity.class)));

        out.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
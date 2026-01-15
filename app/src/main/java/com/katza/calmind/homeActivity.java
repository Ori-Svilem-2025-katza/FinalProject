package com.katza.calmind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

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
            startActivity(new Intent(this, loginActivity.class));
            finish();
        });
    }
}
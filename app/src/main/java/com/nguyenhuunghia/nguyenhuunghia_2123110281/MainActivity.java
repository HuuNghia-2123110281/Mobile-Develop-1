package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvGreeting;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnBack = findViewById(R.id.btnLogin);
        tvGreeting = findViewById(R.id.txtWelcome);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if (username != null) {
            tvGreeting.setText("Chào mừng, " + username + " !");
        }

        btnBack.setOnClickListener(v ->
        {
            Intent backIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(backIntent);
            finish();
        });
    }
}

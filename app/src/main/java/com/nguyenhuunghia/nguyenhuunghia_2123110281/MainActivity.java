package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnBack = findViewById(R.id.btnBack);
        tvGreeting = findViewById(R.id.tvGreeting);

        // Nhận dữ liệu từ LoginActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        if (username != null) {
            tvGreeting.setText("Chào mừng, " + username + "!");
        }

        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(backIntent);
            finish();
        });
    }
}

package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvGreeting;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    ArrayList<Product> productList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        btnBack = findViewById(R.id.btnBack);
        tvGreeting = findViewById(R.id.txtWelcome);
        recyclerView = findViewById(R.id.rvProducts);

        // Lấy username từ Intent
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.trim().isEmpty()) {
            tvGreeting.setText("Chào mừng, " + username + " !");
        } else {
            tvGreeting.setText("Chào mừng bạn!");
        }

        // Danh sách sản phẩm mẫu
        productList = new ArrayList<>();
        productList.add(new Product("iPhone 15", 15490000));
        productList.add(new Product("iPhone 15 ProMax", 27390000));

        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(backIntent);
            finish();
        });
    }
}

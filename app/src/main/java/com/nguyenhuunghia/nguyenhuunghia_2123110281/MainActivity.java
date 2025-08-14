package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    Button btnBack;
    TextView tvGreeting;
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Xin quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }

        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        tvGreeting = findViewById(R.id.txtWelcome);
        rvProducts = findViewById(R.id.rvProducts); // Đảm bảo ID trong layout là rvProducts

        // Cấu hình RecyclerView
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2)); // 2 cột


        // Tạo dữ liệu sản phẩm
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("iPhone 15", 17390000 ,"128GB/6.1in/60hz"));
        products.add(new Product("iPhone 15 PROMAX", 27490000 ,"256GB/6.7in/120hz"));
        products.add(new Product("LapTop ASUS Gaming", 32490000,"FA506NFR R7 7435HS/16GB/512GB/4GB RTX2050/144Hz/Win11"));
        products.add(new Product("LapTop ASUS Vivobook", 27390000 ,"OLED A1505VA i5 13500H/16GB/512GB/120Hz/Win11"));

        // Gán adapter cho RecyclerView
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(products);
        rvProducts.setAdapter(adapter);

        // Hiển thị lời chào
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            tvGreeting.setText("Chào mừng, " + username + " !");
        } else {
            tvGreeting.setText("Chào mừng, khách !");
        }

        // Nút quay lại
        btnBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(backIntent);
            finish();
        });
    }
}

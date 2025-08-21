package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvGreeting;
    Button btnBack;
    EditText edtSearch;
    RecyclerView rv;
    ProductAdapter adapter;
    ArrayList<Product> products;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvGreeting = findViewById(R.id.txtWelcome);
        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        rv = findViewById(R.id.rvProducts);

        // Hiển thị lời chào
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.trim().isEmpty()) {
            tvGreeting.setText("Chào mừng, " + username + " !");
        } else {
            tvGreeting.setText("Chào mừng bạn!");
        }

        // Khởi tạo danh sách sản phẩm
        products = new ArrayList<>();
        adapter = new ProductAdapter(products, new ProductAdapter.OnProductActionListener() {
            @Override
            public void onAddToCart(Product p) {
                CartManager.getInstance().addToCart(p, 1);
                Toast.makeText(MainActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenDetail(Product p) {
                Intent i = new Intent(MainActivity.this, ProductDetailActivity.class);
                i.putExtra("product", p);
                startActivity(i);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        loadProductsFromApi();

        // Nút quay lại Login
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Icon giỏ hàng
        LinearLayout ivCartLayout = findViewById(R.id.ivCart);
        ivCartLayout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CartActivity.class);
            startActivity(i);
        });

        // Icon hồ sơ
        LinearLayout ivPersonLayout = findViewById(R.id.ivPerson);
        ivPersonLayout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ActivityProfile.class);
            startActivity(i);
        });

        // Tìm kiếm sản phẩm theo tên
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadProductsFromApi() {
        ApiService.apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.clear();
                    products.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Không lấy được sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lọc danh sách sản phẩm theo tên
    private void filterProducts(String keyword) {
        List<Product> filteredList = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(p);
            }
        }
        adapter.updateData(filteredList);
    }
}

package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    Button btnBack, btnTatCa, btnDienThoai, btnLaptop, btnTablet, btnPhuKien;
    EditText edtSearch;
    RecyclerView rv;
    ProductAdapter adapter;
    ArrayList<Product> products;

    private String selectedCategory = "";

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FindViewById
        tvGreeting = findViewById(R.id.txtWelcome);
        btnBack = findViewById(R.id.btnBack);
        edtSearch = findViewById(R.id.edtSearch);
        rv = findViewById(R.id.rvProducts);

        btnTatCa = findViewById(R.id.btnTatCa);
        btnDienThoai = findViewById(R.id.btnDienThoai);
        btnLaptop = findViewById(R.id.btnLaptop);
        btnTablet = findViewById(R.id.btnTablet);
        btnPhuKien = findViewById(R.id.btnPhuKien);

        // Greeting
        String username = getIntent().getStringExtra("username");
        tvGreeting.setText(username != null && !username.trim().isEmpty() ?
                "Chào mừng, " + username + " !" : "Chào mừng bạn!");

        // RecyclerView
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

        // Nút đăng xuất
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Icon giỏ hàng
        LinearLayout ivCartLayout = findViewById(R.id.ivCart);
        ivCartLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));

        // Icon hồ sơ
        LinearLayout ivPersonLayout = findViewById(R.id.ivPerson);
        ivPersonLayout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityProfile.class)));

        // Text Search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Button “Tất cả”
        btnTatCa.setOnClickListener(v -> {
            adapter.updateData(products);
        });


        // Category Buttons
        btnDienThoai.setOnClickListener(v -> {
            selectedCategory = "Điện thoại";
            filterProducts(edtSearch.getText().toString());
        });
        btnLaptop.setOnClickListener(v -> {
            selectedCategory = "Laptop";
            filterProducts(edtSearch.getText().toString());
        });
        btnTablet.setOnClickListener(v -> {
            selectedCategory = "Tablet";
            filterProducts(edtSearch.getText().toString());
        });
        btnPhuKien.setOnClickListener(v -> {
            selectedCategory = "Phụ kiện";
            filterProducts(edtSearch.getText().toString());
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
                Toast.makeText(MainActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(String keyword) {
        List<Product> filteredList = new ArrayList<>();
        for (Product p : products) {
            boolean matchesName = p.getName().toLowerCase().contains(keyword.toLowerCase());

            boolean matchesCategory;
            if (selectedCategory.isEmpty()) {
                matchesCategory = true; // hiển thị tất cả nếu category rỗng
            } else {
                // Nếu product có category từ API
                matchesCategory = p.getCategory().equalsIgnoreCase(selectedCategory);
            }

            if (matchesName && matchesCategory) {
                filteredList.add(p);
            }
        }
        adapter.updateData(filteredList);
    }
}

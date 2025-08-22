package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryProductActivity extends AppCompatActivity {

    private RecyclerView rvCategoryProducts;
    private CategoryProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();

    private ApiService apiService;
    private String category = "all"; // mặc định tất cả

    // Nút chọn danh mục
    private Button btnAll, btnPhone, btnLaptop, btnTablet;

    // Thanh bottom navigation
    private LinearLayout ivHome, ivCart, ivPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        // Lấy category từ Intent
        if (getIntent() != null && getIntent().hasExtra("category")) {
            category = getIntent().getStringExtra("category");
        }

        // Ánh xạ RecyclerView
        rvCategoryProducts = findViewById(R.id.rvCategoryProducts);
        rvCategoryProducts.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter
        adapter = new CategoryProductAdapter(productList, this);
        rvCategoryProducts.setAdapter(adapter);

        // Ánh xạ các nút
        btnAll = findViewById(R.id.btnAll);
        btnPhone = findViewById(R.id.btnPhone);
        btnLaptop = findViewById(R.id.btnLaptop);
        btnTablet = findViewById(R.id.btnTablet);

        ivHome = findViewById(R.id.ivHome);
        ivCart = findViewById(R.id.ivCart);
        ivPerson = findViewById(R.id.ivPerson);

        // Sự kiện khi nhấn các nút
        btnAll.setOnClickListener(v -> {
            // Quay về MainActivity (Trang chủ)
            Intent intent = new Intent(CategoryProductActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnPhone.setOnClickListener(v -> changeCategory("phone"));
        btnLaptop.setOnClickListener(v -> changeCategory("laptop"));
        btnTablet.setOnClickListener(v -> changeCategory("tablet"));

        // Bottom navigation
        ivHome.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryProductActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        ivCart.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryProductActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ivPerson.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryProductActivity.this, ActivityProfile.class);
            startActivity(intent);
        });

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://68931a76c49d24bce869717c.mockapi.io/") // MockAPI
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Load dữ liệu
        loadProducts();
    }

    private void changeCategory(String newCategory) {
        category = newCategory;
        loadProducts();
    }

    private void loadProducts() {
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> allProducts = response.body();
                    productList.clear();

                    if (category.equals("all")) {
                        productList.addAll(allProducts);
                    } else {
                        for (Product p : allProducts) {
                            if (p.getCategory() != null && p.getCategory().equalsIgnoreCase(category)) {
                                productList.add(p);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CategoryProductActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(CategoryProductActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

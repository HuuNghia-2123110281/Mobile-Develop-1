package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

    private TextView tvGreeting;
    private Button btnBack;
    private EditText edtSearch;
    private RecyclerView rv;
    private ProductAdapter adapter;
    private ArrayList<Product> products;

    FrameLayout frameCategory1, frameCategory2, frameCategory3, frameCategory4;
    TextView txtCategory1, txtCategory2, txtCategory3, txtCategory4;

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

        frameCategory2 = findViewById(R.id.frameCategory2);
        txtCategory2   = findViewById(R.id.txtCategory2);
        frameCategory3 = findViewById(R.id.frameCategory3);
        txtCategory3   = findViewById(R.id.txtCategory3);
        frameCategory4 = findViewById(R.id.frameCategory4);
        txtCategory4   = findViewById(R.id.txtCategory4);

        // Greeting user
        String username = getIntent().getStringExtra("username");
        tvGreeting.setText(username != null && !username.trim().isEmpty()
                ? "Chào mừng, " + username + " !" : "Chào mừng bạn!");

        // RecyclerView setup
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

        // Load sản phẩm từ API
        loadProductsFromApi();

        // Đăng xuất
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Giỏ hàng
        LinearLayout ivCartLayout = findViewById(R.id.ivCart);
        ivCartLayout.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CartActivity.class)));

        // Hồ sơ
        LinearLayout ivPersonLayout = findViewById(R.id.ivPerson);
        ivPersonLayout.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ActivityProfile.class)));

        // Tìm kiếm theo tên
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

        // Lọc theo danh mục
        frameCategory2.setOnClickListener(v -> openCategory(txtCategory2.getText().toString()));
        frameCategory3.setOnClickListener(v -> openCategory(txtCategory3.getText().toString()));
        frameCategory4.setOnClickListener(v -> openCategory(txtCategory4.getText().toString()));
    }
    private void openCategory(String categoryName) {
        Intent intent = new Intent(MainActivity.this, CategoryProductActivity.class);
        intent.putExtra("category", categoryName); // key phải trùng "category"
        startActivity(intent);
    }

    private void loadProductsFromApi() {
        ApiService.apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.clear();
                    products.addAll(response.body());
                    selectedCategory = ""; // reset về tất cả
                    filterProducts("");    // hiển thị toàn bộ
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
            boolean matchesCategory = selectedCategory.isEmpty()
                    || p.getCategory().equalsIgnoreCase(selectedCategory);

            if (matchesName && matchesCategory) {
                filteredList.add(p);
            }
        }
        adapter.updateData(filteredList);
    }
}

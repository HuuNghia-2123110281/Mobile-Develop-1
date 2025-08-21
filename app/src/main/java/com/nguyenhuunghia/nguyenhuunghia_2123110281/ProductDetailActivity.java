package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView img;
    private TextView tvName, tvPrice, tvDescription;
    private Button btnAddToCart, btnAddBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        img = findViewById(R.id.imgProductDetail);
        tvName = findViewById(R.id.tvProductNameDetail);
        tvPrice = findViewById(R.id.tvProductPriceDetail);
        tvDescription = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddBill = findViewById(R.id.btnAddBill);

        // Nhận Product từ Intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            tvName.setText(product.getName());
            tvDescription.setText(product.getDesc());

            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            if (product.getPrice() > 0) {
                tvPrice.setText(formatter.format(product.getPrice()));
            } else {
                tvPrice.setText("Liên hệ");
            }

            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.ic_placehoder)
                    .error(R.drawable.ic_error)
                    .into(img);

            // 👇 Xử lý nút Thêm vào giỏ hàng
            btnAddToCart.setOnClickListener(v -> {
                // TODO: Thêm sản phẩm vào giỏ hàng (local list, database, ViewModel,...)
                // Ví dụ: gửi qua Intent đến CartActivity
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            });

            // 👇 Xử lý nút Thanh toán ngay
            btnAddBill.setOnClickListener(v -> {
                Intent intent = new Intent(ProductDetailActivity.this, CheckoutActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            });
        }
    }
}


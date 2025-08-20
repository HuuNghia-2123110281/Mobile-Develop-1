package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView img;
    TextView tvName, tvPrice, tvDesc;
    Button btnAddToCart, btnBuyNow;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        img = findViewById(R.id.imgProductDetail);
        tvName = findViewById(R.id.tvProductNameDetail);
        tvPrice = findViewById(R.id.tvProductPriceDetail);
        tvDesc = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnAddBill);

        Product p = (Product) getIntent().getSerializableExtra("product");
        if (p != null) {
            currentProduct = p;

            // Load ảnh từ URL
            Glide.with(this)
                    .load(p.getImageUrl())
                    .into(img);

            tvName.setText(p.getName());
            tvPrice.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(p.getPrice()));
            tvDesc.setText("Mô tả sản phẩm đang cập nhật...");
        }


        // Thêm vào giỏ
        btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(currentProduct);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });


        // Mua ngay → sang trang thanh toán
        btnBuyNow.setOnClickListener(v -> {
            if (currentProduct != null) {
                CartManager.getInstance().addToCart(currentProduct, 1);
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
}

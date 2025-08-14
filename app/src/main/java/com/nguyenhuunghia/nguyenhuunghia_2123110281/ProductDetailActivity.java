package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView tvName, tvPrice;

    TextView tvDescription;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProductDetail);
        tvName = findViewById(R.id.tvProductNameDetail);
        tvPrice = findViewById(R.id.tvProductPriceDetail);
        TextView tvDescription;

        tvDescription = findViewById(R.id.tvProductDescription);

        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price", 0);
        String description = getIntent().getStringExtra("description"); // ✅ Nhận mô tả

        tvName.setText(name);
        tvPrice.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price));
        tvDescription.setText(description); // ✅ Hiển thị mô tả


        // Gắn ảnh sản phẩm (đơn giản dựa trên tên)
        String nameLower = name.toLowerCase();
        if (nameLower.contains("promax")) {
            imgProduct.setImageResource(R.drawable.iphone15promax);
        } else if (nameLower.contains("14")) {
            imgProduct.setImageResource(R.drawable.iphone14promax);
        } else if (nameLower.contains("vivobook")) {
        imgProduct.setImageResource(R.drawable.laptopasusvivobook);
        } else if (nameLower.contains("gaming")) {
        imgProduct.setImageResource(R.drawable.laptopasusgaming);
        }else {
            imgProduct.setImageResource(R.drawable.iphone15);
        }
    }
}

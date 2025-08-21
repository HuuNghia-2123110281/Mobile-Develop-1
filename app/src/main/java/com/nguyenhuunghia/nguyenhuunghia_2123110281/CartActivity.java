package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private TextView tvTotalPrice;
    private Button btnCheckout;

    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Lấy danh sách giỏ hàng
        cartItems = CartManager.getInstance().getCartItems();

        // Hiển thị giỏ hàng bằng adapter
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, () -> updateTotalPrice());
        recyclerViewCart.setAdapter(cartAdapter);


        // Cập nhật tổng tiền
        updateTotalPrice();

        // Nút thanh toán
        btnCheckout.setOnClickListener(v -> {
            double totalPrice = CartManager.getInstance().getTotalPrice();
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("totalPrice", totalPrice);
            startActivity(intent);
        });
    }

    private void updateTotalPrice() {
        double total = CartManager.getInstance().getTotalPrice();
        String formattedPrice = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total);
        tvTotalPrice.setText("Tổng tiền: " + formattedPrice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại khi quay về từ CheckoutActivity
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }
}

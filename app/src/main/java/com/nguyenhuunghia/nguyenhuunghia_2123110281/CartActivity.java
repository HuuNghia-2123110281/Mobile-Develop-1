package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotal;
    private CartAdapter adapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerCart);
        tvTotal = findViewById(R.id.tvTotal);

        // Lấy giỏ hàng
        cartItems = CartManager.getInstance().getCartItems();

        // Gắn adapter
        adapter = new CartAdapter(this, cartItems, this::updateTotal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Hiển thị tổng tiền ban đầu
        updateTotal();
    }

    private void updateTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        tvTotal.setText("Tổng tiền: " + total + " VND");
    }
}

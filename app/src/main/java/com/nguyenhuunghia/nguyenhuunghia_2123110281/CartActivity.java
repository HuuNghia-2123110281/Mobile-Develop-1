package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Lấy danh sách giỏ hàng từ CartManager
        cartItems = CartManager.getInstance().getCartItems();

        // Adapter và RecyclerView
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, this::updateTotalPrice);
        recyclerViewCart.setAdapter(cartAdapter);

        // Hiển thị tổng tiền
        updateTotalPrice();

        // Xử lý nút Thanh toán
        btnCheckout.setOnClickListener(v -> {
            double totalAmount = calculateTotalAmount();
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("total_amount", totalAmount);
            startActivity(intent);
        });
    }

    private double calculateTotalAmount() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    private void updateTotalPrice() {
        double total = calculateTotalAmount();
        String formattedPrice = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total);
        tvTotalPrice.setText("Tổng tiền: " + formattedPrice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }
}

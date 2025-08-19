package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter adapter;
    private static final List<CartItem> cartList = new ArrayList<>();

    public static List<CartItem> getCartList() {
        return cartList;
    }

    public static void addToCart(Product product) {
        for (CartItem item : cartList) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.increase();
                return;
            }
        }
        cartList.add(new CartItem(product));
    }

    public static void clearCart() {
        cartList.clear();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        tvTotal = findViewById(R.id.tvTotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        adapter = new CartAdapter(cartList, new CartAdapter.OnCartChange() {
            @Override
            public void onRemove(int position) {
                cartList.remove(position);
                adapter.notifyItemRemoved(position);
                updateTotal();
            }

            @Override
            public void onDataChanged() {
                updateTotal();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            double total = calculateTotalPrice();
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("totalPrice", total);
            startActivity(intent);
        });
    }

    private void updateTotal() {
        long total = 0;
        for (CartItem item : cartList) {
            total += item.getSubtotal();
        }
        NumberFormat f = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTotal.setText("Tổng: " + f.format(total));
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (CartItem item : cartList) {
            total += item.getSubtotal();
        }
        return total;
    }
}

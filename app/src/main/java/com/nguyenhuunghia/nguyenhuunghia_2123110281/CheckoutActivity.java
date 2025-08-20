package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private TextView tvCheckoutSummary;
    private EditText edtAddress;
    private Button btnConfirmPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvCheckoutSummary = findViewById(R.id.tvCheckoutSummary);
        edtAddress = findViewById(R.id.edtAddress);
        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);

        // Lấy tổng tiền từ Intent
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0);
        tvCheckoutSummary.setText("Tổng tiền: " +
                NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice));

        btnConfirmPayment.setOnClickListener(v -> {
            String address = edtAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();
                CartManager.getInstance().clearCart(); // Xóa giỏ hàng sau khi thanh toán
                finish();
            }
        });
    }
}

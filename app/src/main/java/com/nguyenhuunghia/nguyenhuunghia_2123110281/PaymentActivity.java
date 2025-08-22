package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private ImageView qrImageView;
    private TextView tvTotalPrice;
    private Button btnDone;

    // Thông tin ngân hàng của bạn
    private final String BANK_CODE = "970407"; // Techcombank
    private final String ACCOUNT_NO = "50977451512";
    private final String ADD_INFO = "ThanhToanDonHang"; // nội dung chuyển khoản

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        qrImageView = findViewById(R.id.qrImageView);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnDone = findViewById(R.id.btnDone);

        // Lấy tổng tiền từ giỏ hàng
        double totalPrice = CartManager.getInstance().getTotalPrice();

        // Format tiền thành VND hiển thị
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTotalPrice.setText("Tổng tiền: " + formatter.format(totalPrice));

        // Tạo QR động theo chuẩn VietQR
        String qrUrl = "https://img.vietqr.io/image/"
                + BANK_CODE + "-" + ACCOUNT_NO
                + "-compact.png?amount=" + (int) totalPrice
                + "&addInfo=" + ADD_INFO;

        // Load QR vào ImageView
        Glide.with(this).load(qrUrl).into(qrImageView);

        // Nút hoàn tất thanh toán
        btnDone.setOnClickListener(v -> {
            CartManager.getInstance().clearCart(); // xóa giỏ
            finish(); // quay lại
        });
    }
}

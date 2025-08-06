package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etConfirmPassword, etPhoneNumber;
    Button btnRegister;
    ImageView ivTogglePassword;
    boolean isPasswordVisible = false;

    String apiUrl = "https://68931a76c49d24bce869717c.mockapi.io/users";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etNewUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);

        // Toggle hiển thị mật khẩu
        ivTogglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_cloesd); // ẩn
            } else {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivTogglePassword.setImageResource(R.drawable.ic_eye_open); // hiện
            }
            etPassword.setSelection(etPassword.length());
            isPasswordVisible = !isPasswordVisible;
        });

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String phone = etPhoneNumber.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Mật khẩu yếu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidPhoneNumber(phone)) {
                Toast.makeText(this, "SĐT không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo JSON object
            JSONObject user = new JSONObject();
            try {
                user.put("username", username);
                user.put("password", password);
                user.put("phone", phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Gửi request lên MockAPI
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, user,
                    response -> {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    },
                    error -> Toast.makeText(this,
                            "Lỗi đăng ký: " + error.getMessage(),
                            Toast.LENGTH_SHORT).show()
            );
            queue.add(request);
        });
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,24}$");
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^(03|05|07|08|09)\\d{8}$");
    }
}

package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ActivityProfile extends AppCompatActivity {

    TextView tvUsername, tvPhone;
    Button btnEdit, btnLogout;

    String apiUrl = "https://68931a76c49d24bce869717c.mockapi.io/users/";
    String userId; // Lấy từ Intent

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUser);
        tvPhone = findViewById(R.id.tvPhone);
        btnEdit = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Lấy userId từ Intent (truyền từ LoginActivity/RegisterActivity)
        userId = getIntent().getStringExtra("userId");

        if (userId != null) {
            loadUserProfile(userId);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Chuyển sang trang EditProfile
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityProfile.this, EditActivityProfile.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // Logout -> xóa dữ liệu + quay lại LoginActivity
        btnLogout.setOnClickListener(v -> {
            getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
            startActivity(new Intent(ActivityProfile.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUserProfile(String id) {
        String url = apiUrl + id;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String username = response.getString("username");
                        String phone = response.getString("phone");

                        tvUsername.setText("Tên: " + username);
                        tvPhone.setText("SĐT: " + phone);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Lỗi tải hồ sơ: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}

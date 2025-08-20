package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfile extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView tvName, tvEmail, tvPhone;
    private Button btnEdit, btnLogout;

    private ApiService apiService;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgAvatar = findViewById(R.id.imgAvatar);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);

        apiService = APIClient.getClient().create(ApiService.class);

        // Lấy userId đã lưu khi đăng nhập
        SharedPreferences prefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        userId = prefs.getInt("USER_ID", -1);

        if (userId != -1) {
            loadUserProfile(userId);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
        }

        // Bấm vào Edit
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityProfile.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Bấm vào Logout
        btnLogout.setOnClickListener(v -> {
            // Xóa session
            prefs.edit().clear().apply();

            Intent intent = new Intent(ActivityProfile.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadUserProfile(int userId) {
        apiService.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    tvName.setText(user.getName());

                    Glide.with(ActivityProfile.this)
                            .load(user.getAvatar())
                            .placeholder(R.drawable.ic_placehoder)
                            .error(R.drawable.ic_error)
                            .into(imgAvatar);
                } else {
                    Toast.makeText(ActivityProfile.this, "Không tải được hồ sơ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ActivityProfile.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class EditProfileActivity {
    }
}

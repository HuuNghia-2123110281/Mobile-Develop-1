package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivityProfile extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPhone;
    private Button btnSave, btnCancel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Ánh xạ view
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Nhận user từ intent
        user = (User) getIntent().getSerializableExtra("user");

        if (user != null) {
            edtName.setText(user.getName());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());
        }

        // ✅ Nút Lưu
        btnSave.setOnClickListener(v -> {
            String newName = edtName.getText().toString().trim();
            String newEmail = edtEmail.getText().toString().trim();
            String newPhone = edtPhone.getText().toString().trim();

            if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(EditActivityProfile.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật object user
            user.setName(newName);
            user.setEmail(newEmail);
            user.setPhone(newPhone);

            // Gọi API update
            ApiService apiService = APIClient.getClient().create(ApiService.class);
            Call<User> call = apiService.updateUser(user.getId(), user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User updatedUser = response.body();

                        Toast.makeText(EditActivityProfile.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        // Gửi user mới về MainActivity
                        Intent intent = new Intent();
                        intent.putExtra("updatedUser", updatedUser);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(EditActivityProfile.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditActivityProfile.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // ✅ Nút Hủy
        btnCancel.setOnClickListener(v -> finish());
    }
}

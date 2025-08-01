package com.nguyenhuunghia.nguyenhuunghia_2123110281;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etNewUsername, etNewPassword;
    Button btnRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String newUser = etNewUsername.getText().toString().trim();
            String newPass = etNewPassword.getText().toString().trim();

            if (newUser.isEmpty() || newPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", newUser);
            editor.putString("password", newPass);
            editor.apply();

            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

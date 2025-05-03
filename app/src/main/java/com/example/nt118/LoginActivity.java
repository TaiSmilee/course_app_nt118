package com.example.nt118;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra đã đăng nhập chưa
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false);
        if (isLoggedIn) {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // không cho quay lại LoginActivity nữa
            return;
        }

        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Kiểm tra dữ liệu nhập vào
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi hàm xử lý đăng nhập
            performLogin(email, password);
        });
    }

    private void performLogin(String email, String password) {
        // Login giả lập - thay bằng gọi API thật nếu có
        if (email.equals("test@example.com") && password.equals("123456")) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            // Lưu trạng thái đăng nhập

            SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // xoá hết, hoặc editor.remove("IS_LOGGED_IN");
            editor.apply();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // đóng LoginActivity
        } else {
            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại, kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
        }
    }
}

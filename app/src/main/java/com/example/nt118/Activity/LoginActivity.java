package com.example.nt118.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118.Model.LoginRequest;
import com.example.nt118.Model.LoginResponse;
import com.example.nt118.R;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        etEmail = findViewById(R.id.etStudentId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String studentId = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate input
            if (studentId.isEmpty()) {
                etEmail.setError("Vui lòng nhập mã số sinh viên");
                etEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                etPassword.setError("Vui lòng nhập mật khẩu");
                etPassword.requestFocus();
                return;
            }

            // Thực hiện đăng nhập
            RetrofitClient.getInstance()
                    .getAuthApi()
                    .login(new LoginRequest(studentId, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse.isStatus()) {
                                    // Lưu token và thông tin người dùng
                                    SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("TOKEN", loginResponse.getToken());
                                    editor.putString("STUDENT_ID", loginResponse.getStudentId());
                                    editor.putString("NAME", loginResponse.getName());
                                    editor.putString("EMAIL", loginResponse.getEmail());
                                    editor.putString("ROLE", loginResponse.getRole());
                                    editor.apply();

                                    // Hiển thị thông báo thành công
                                    Toast.makeText(LoginActivity.this, 
                                            loginResponse.getMessage(), 
                                            Toast.LENGTH_SHORT).show();

                                    // Chuyển đến màn hình chính
                                    Intent intent = new Intent(LoginActivity.this, HomeCourseActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Hiển thị thông báo lỗi từ server
                                    Toast.makeText(LoginActivity.this, 
                                            loginResponse.getMessage(), 
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Hiển thị thông báo lỗi chung
                                Toast.makeText(LoginActivity.this, 
                                        "Đăng nhập thất bại. Vui lòng thử lại!", 
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // Hiển thị thông báo lỗi kết nối
                            Toast.makeText(LoginActivity.this,
                                    "Lỗi kết nối: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
} 
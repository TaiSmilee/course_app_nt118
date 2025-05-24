package com.example.nt118.UI.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118.API.RetrofitClient;
import com.example.nt118.Model.LoginRequest;
import com.example.nt118.Model.LoginResponse;
import com.example.nt118.UI.MainActivity;
import com.example.nt118.R;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etStudentId, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        etStudentId = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String studentId = etStudentId.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            RetrofitClient.getInstance()
                    .getAuthApi()
                    .login(new LoginRequest(studentId, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse loginResponse = response.body();
                                Log.d("LoginDebug", "Response received - Status: " + loginResponse.isSuccess());
                                Log.d("LoginDebug", "Message: " + loginResponse.getMessage());
                                Log.d("LoginDebug", "Token: " + loginResponse.getToken());
                                Log.d("LoginDebug", "StudentId: " + loginResponse.getStudentId());
                                
                                if (loginResponse.isSuccess()) {
                                    // Lưu thông tin vào SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("token", loginResponse.getToken());
                                    editor.putString("studentId", loginResponse.getStudentId());
                                    editor.putString("name", loginResponse.getName());
                                    editor.putString("email", loginResponse.getEmail());
                                    editor.putString("role", loginResponse.getRole());
                                    editor.apply();

                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Log.d("LoginDebug", "Đang chuyển sang HomeCourseActivity");
                                    startActivity(new Intent(LoginActivity.this, HomeCourseActivity.class));
                                    Log.d("LoginDebug", "startActivity đã gọi");
                                    finish();
                                } else {
                                    Log.d("LoginDebug", "đăng nhập thất bại");
                                    // Đăng nhập thất bại
                                    Toast.makeText(LoginActivity.this,
                                            loginResponse.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("LoginDebug", "Response not successful or body is null");
                                Toast.makeText(LoginActivity.this,
                                        "Lỗi đăng nhập: " + (response.body() != null ? response.body().getMessage() : "Unknown error"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // Xử lý lỗi kết nối
                            Toast.makeText(LoginActivity.this,
                                    "Lỗi kết nối: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}

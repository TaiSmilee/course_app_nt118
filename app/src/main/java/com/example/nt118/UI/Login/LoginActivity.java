package com.example.nt118.UI.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //tvForgotPassword = findViewById(R.id.tvForgotPassword);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String studentId = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            RetrofitClient.getInstance()
                    .getAuthApi()
                    .login(new LoginRequest(studentId, password))
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                LoginResponse loginResponse = response.body();
                                if (loginResponse.isSuccess()) {
                                    // Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this,
                                            "Đăng nhập thành công",
                                            Toast.LENGTH_SHORT).show();
                                    // TODO: Chuyển đến màn hình chính
                                    startActivity(new Intent(LoginActivity.this, HomeCourseActivity.class));
                                    finish();
                                } else {
                                    // Đăng nhập thất bại
                                    Toast.makeText(LoginActivity.this,
                                            loginResponse.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
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

//            // Kiểm tra dữ liệu nhập vào
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Kiểm tra định dạng email
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
//                return;
//            }

            // Gọi hàm xử lý đăng nhập
            //performLogin(email, password);
        });
    }

//    private void performLogin(String email, String password) {
//        // Login giả lập - thay bằng gọi API thật nếu có
//        if (email.equals("test@example.com") && password.equals("123456")) {
//            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//
//            // Lưu trạng thái đăng nhập
//
//            SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.clear(); // xoá hết, hoặc editor.remove("IS_LOGGED_IN");
//            editor.apply();
//            startActivity(new Intent(LoginActivity.this, HomeCourseActivity.class));
//            finish(); // đóng LoginActivity
//        } else {
//            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại, kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
//        }
//    }
}

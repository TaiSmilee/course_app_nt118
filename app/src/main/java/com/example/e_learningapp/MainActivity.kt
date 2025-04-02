package com.example.e_learningapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ánh xạ các thành phần
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Xử lý sự kiện nhấn nút đăng nhập
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Kiểm tra dữ liệu nhập
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Nếu cần, kiểm tra định dạng email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gọi hàm xử lý đăng nhập (có thể là gọi API hoặc kiểm tra cục bộ)
            performLogin(email, password)
        }

        // Xử lý sự kiện “Quên mật khẩu”
        tvForgotPassword.setOnClickListener {
            // Chuyển sang màn hình quên mật khẩu hoặc hiển thị thông báo
            Toast.makeText(this, "Chức năng quên mật khẩu chưa được triển khai", Toast.LENGTH_SHORT).show()
        }
    }

    // Hàm xử lý đăng nhập
    private fun performLogin(email: String, password: String) {
        // Giả lập xử lý đăng nhập, có thể thay bằng API thực tế
        if (email == "test@example.com" && password == "123456") {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
            // Chuyển sang Activity chính của ứng dụng
        } else {
            Toast.makeText(this, "Đăng nhập thất bại, kiểm tra lại thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}

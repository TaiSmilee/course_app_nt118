package com.example.nt118.UI.Attendance;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118.R;

public class AttendanceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Không cần dùng FragmentTransaction nữa nếu sử dụng NavHostFragment
    }
}
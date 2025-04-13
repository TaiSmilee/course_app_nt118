package com.example.nt118;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        // Gán đúng item đang được chọn
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);

        // Setup listener nếu bạn có BottomNavHelper xử lý
        BottomNavHelper.setupBottomNav(this, bottomNav);

        // Click vào layoutCourses để mở danh sách môn
        LinearLayout layoutClasses = findViewById(R.id.layoutCourses);
        layoutClasses.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CourseListActivity.class);
            startActivity(intent);
        });
    }
}

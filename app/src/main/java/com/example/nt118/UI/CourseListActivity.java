package com.example.nt118.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;

    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        recyclerView = findViewById(R.id.recyclerViewCourses);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi bấm ảnh
                Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dữ liệu mẫu
        courseList = new ArrayList<>();

        // Thêm một số dữ liệu mẫu
        courseList.add(new Course("NT108","Lập trình ứng dụng di động", "Thứ 2", "08:00", "10:00", "Thứ 5", "14:00", "16:00"));
        courseList.add(new Course("IT001", "Nhập môn lập trình","Thứ 3", "09:00", "11:00", "", "", ""));
        courseList.add(new Course("IT003", "Cấu trúc dữ liệu và giải thuật", "", "", "", "Thứ 7", "13:0", "15:00"));
        courseList.add(new Course("IT002", "Lập trình hướng đối tượng", "", "", "", "", "", ""));

        // Cập nhật số lượng
        //tvCourseCount.setText("Tổng số khóa học: " + courseList.size());

        courseAdapter = new CourseAdapter(this, courseList);
        recyclerView.setAdapter(courseAdapter);

        setupBottomBarNavigation();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(CourseListActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(CourseListActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(CourseListActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(CourseListActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}

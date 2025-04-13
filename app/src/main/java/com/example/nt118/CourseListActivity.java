package com.example.nt118;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.CourseAdapter;
import com.example.nt118.Course;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        // Gán tiêu đề trên top bar nếu có
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        if (toolbarTitle != null) {
            toolbarTitle.setText("Course List");
        }

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        BottomNavHelper.setupBottomNav(this, bottomNav);

        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dữ liệu mẫu
        courseList = new ArrayList<>();
        courseList.add(new Course("Lớp Java A", "Thứ 2", "08:00", "10:00", "Thứ 5", "14:00", "16:00"));
        courseList.add(new Course("Lớp Android", "Thứ 3", "09:00", "11:00", "", "", ""));
        courseList.add(new Course("Lớp Web", "", "", "", "Thứ 7", "13:00", "15:00"));
        courseList.add(new Course("Lớp Python", "", "", "", "", "", ""));

        courseAdapter = new CourseAdapter(this, courseList);
        recyclerView.setAdapter(courseAdapter);
    }
}

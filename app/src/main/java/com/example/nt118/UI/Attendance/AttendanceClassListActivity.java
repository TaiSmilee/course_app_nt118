package com.example.nt118.UI.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;

import java.util.ArrayList;
import java.util.List;

public class AttendanceClassListActivity extends AppCompatActivity {

    List<Subject> classList;
    SubjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_attendance_class_list);

        RecyclerView recyclerView = findViewById(R.id.subjectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        classList = new ArrayList<>();
        // Dummy data
        classList.add(new Subject("NT118", "Lập trình ứng dụng di động", 2));
        classList.add(new Subject("IT002", "Lập trình hướng đối tượng", 0));

        adapter = new SubjectAdapter(classList, this);

        recyclerView.setAdapter(adapter);

        setupBottomBarNavigation();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(AttendanceClassListActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(AttendanceClassListActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(AttendanceClassListActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(AttendanceClassListActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
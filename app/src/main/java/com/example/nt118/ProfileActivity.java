package com.example.nt118;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118.Deadline.DeadlineActivity;
import com.example.nt118.Tuition.TuitionActivity;
import com.example.nt118.grades.GradeActivity;
import com.example.nt118.homecourse.HomeCourseActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);




        BottomBarNavigation();
        StudentComponent();
    }

    private void StudentComponent(){
        LinearLayout layoutClasses = findViewById(R.id.layoutCourses);
        layoutClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CourseListActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutTuition = findViewById(R.id.layoutTuition);
        layoutTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TuitionActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutResult = findViewById(R.id.layoutResult);
        layoutResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, GradeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutAttendance = findViewById(R.id.layoutAttendance);
        layoutAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, com.example.nt118.Attendance.AttendanceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void BottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {

            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}

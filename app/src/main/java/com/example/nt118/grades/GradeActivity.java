package com.example.nt118.grades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.Deadline.DeadlineActivity;
import com.example.nt118.ProfileActivity;
import com.example.nt118.R;

import com.example.nt118.homecourse.HomeCourseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class GradeActivity extends AppCompatActivity {

    private TextView tvGPA;
    private TextView tvCredits;
    private MaterialButton btnSemester;
    private MaterialButton btnBack;
    private RecyclerView rvCourseGrades;
    private CourseGradeAdapter gradeAdapter;
    private List<CourseGradeModel> courseGrades = new ArrayList<>();

    // Semester data
    private String[] semesters = {"HK1 2024-2025", "HK2 2023-2024", "HK1 2023-2024", "HK2 2022-2023"};
    private int currentSemesterIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup click listeners
        setupClickListeners();

        // Load initial data
        loadSemesterData(currentSemesterIndex);

        // Setup bottom bar navigation
        setupBottomBarNavigation();
    }

    private void initializeViews() {
        tvGPA = findViewById(R.id.tvGPA);
        tvCredits = findViewById(R.id.tvCredits);
        btnSemester = findViewById(R.id.btnSemester);
        rvCourseGrades = findViewById(R.id.rvCourseGrades);
    }

    private void setupRecyclerView() {
        rvCourseGrades.setLayoutManager(new LinearLayoutManager(this));
        gradeAdapter = new CourseGradeAdapter(courseGrades, new CourseGradeAdapter.OnCourseClickListener() {
            @Override
            public void onCourseClick(CourseGradeModel course) {
                showCourseDetail(course);
            }
        });
        rvCourseGrades.setAdapter(gradeAdapter);
    }

    private void setupClickListeners() {

        // Semester filter button
        btnSemester.setOnClickListener(v -> showSemesterDialog());
    }

    private void showSemesterDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Semester")
                .setSingleChoiceItems(semesters, currentSemesterIndex, (dialog, which) -> {
                    currentSemesterIndex = which;
                    btnSemester.setText(semesters[which]);
                    loadSemesterData(which);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadSemesterData(int semesterIndex) {
        courseGrades.clear();

        // Load data based on semester
        switch (semesterIndex) {
            case 0: // HK1 2024-2025 (Current)
                updateSummary("3.75", "45");
                courseGrades.add(new CourseGradeModel("NT118.P21.CLC", "Lập trình ứng dụng di động", 3, 4.0, "A"));
                courseGrades.add(new CourseGradeModel("NT120.M21", "Lập trình web", 4, 3.5, "B+"));
                courseGrades.add(new CourseGradeModel("IT003.M22", "Cấu trúc dữ liệu và giải thuật", 4, 3.7, "B+"));
                courseGrades.add(new CourseGradeModel("IT005.M21", "Nhập môn mạng máy tính", 4, 3.3, "B"));
                break;

            case 1: // HK2 2023-2024
                updateSummary("3.60", "41");
                courseGrades.add(new CourseGradeModel("NT106.M21", "Mạng máy tính", 4, 3.8, "A-"));
                courseGrades.add(new CourseGradeModel("CS105.M21", "Đồ họa máy tính", 4, 3.2, "B"));
                courseGrades.add(new CourseGradeModel("IT001.M21", "Nhập môn lập trình", 4, 4.0, "A"));
                break;

            case 2: // HK1 2023-2024
                updateSummary("3.85", "37");
                courseGrades.add(new CourseGradeModel("IT002.M22", "Lập trình hướng đối tượng", 4, 3.7, "B+"));
                courseGrades.add(new CourseGradeModel("NT101.M21", "An toàn mạng", 3, 4.0, "A"));
                courseGrades.add(new CourseGradeModel("IT004.M21", "Cơ sở dữ liệu", 4, 3.9, "A-"));
                break;

            case 3: // HK2 2022-2023
                updateSummary("3.50", "33");
                courseGrades.add(new CourseGradeModel("MATH001", "Toán cao cấp", 4, 3.3, "B"));
                courseGrades.add(new CourseGradeModel("PHYS001", "Vật lý đại cương", 3, 3.7, "B+"));
                courseGrades.add(new CourseGradeModel("IT000", "Tin học cơ sở", 4, 3.5, "B+"));
                break;
        }

        gradeAdapter.notifyDataSetChanged();
    }

    private void updateSummary(String gpa, String credits) {
        tvGPA.setText(gpa);
        tvCredits.setText(credits);
    }

    private void showCourseDetail(CourseGradeModel course) {
        String details = "Course Code: " + course.getCourseCode() + "\n" +
                "Course Name: " + course.getCourseName() + "\n" +
                "Credits: " + course.getCredits() + "\n" +
                "Score: " + course.getNumericGrade() + "\n" +
                "Letter Grade: " + course.getLetterGrade();

        new MaterialAlertDialogBuilder(this)
                .setTitle("Course Details")
                .setMessage(details)
                .setPositiveButton("OK", null)
                .show();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                Intent intent = new Intent(GradeActivity.this, DeadlineActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {

            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(GradeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(GradeActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }


}
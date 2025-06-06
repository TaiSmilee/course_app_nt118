package com.example.nt118.UI.grades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.Login.LoginActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.R;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.ApiService;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.grades.GradeResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeActivity extends AppCompatActivity {

    private static final String TAG = "GradeActivity";
    private static final String PREF_NAME = "APP_PREF";
    private TextView tvGPA;
    private TextView tvCredits;
    private MaterialButton btnSemester;
    private RecyclerView rvCourseGrades;
    private CourseGradeAdapter gradeAdapter;
    private List<CourseGradeModel> courseGrades = new ArrayList<>();
    private ApiService apiService;
    private String currentToken;
    private String studentId;
    private TextView tvGradeA;
    private TextView tvGradeB;
    private TextView tvGradeC;
    private TextView tvGradeD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        // Get user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        
        // Log all SharedPreferences data for debugging
        Log.d(TAG, "All SharedPreferences data:");
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            Log.d(TAG, entry.getKey() + ": " + entry.getValue());
        }
        
        studentId = sharedPreferences.getString("STUDENT_ID", "");
        currentToken = "Bearer " + sharedPreferences.getString("TOKEN", "");
        
        Log.d(TAG, "StudentId from SharedPreferences: " + studentId);
        Log.d(TAG, "Token from SharedPreferences: " + currentToken);

        // Check if we have the required data
        if (studentId.isEmpty()) {
            Log.d(TAG, "StudentId is empty, redirecting to login");
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize Retrofit service using the correct method
        apiService = RetrofitClient.getInstance().createService(ApiService.class);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup click listeners
        setupClickListeners();

        // Load initial data with actual studentId
        loadGradeData(studentId, "HK1_2024");

        // Setup bottom bar navigation
        setupBottomBarNavigation();
    }

    private void initializeViews() {
        tvGPA = findViewById(R.id.tvGPA);
        tvCredits = findViewById(R.id.tvCredits);
        btnSemester = findViewById(R.id.btnSemester);
        rvCourseGrades = findViewById(R.id.rvCourseGrades);
        tvGradeA = findViewById(R.id.tvGradeA);
        tvGradeB = findViewById(R.id.tvGradeB);
        tvGradeC = findViewById(R.id.tvGradeC);
        tvGradeD = findViewById(R.id.tvGradeD);
    }

    private void setupRecyclerView() {
        rvCourseGrades.setLayoutManager(new LinearLayoutManager(this));
        gradeAdapter = new CourseGradeAdapter(courseGrades, course -> showCourseDetail(course));
        rvCourseGrades.setAdapter(gradeAdapter);
    }

    private void setupClickListeners() {
        btnSemester.setOnClickListener(v -> showSemesterDialog());
    }

    private void loadGradeData(String studentId, String semesterId) {
        apiService.getStudentGrades(currentToken, studentId, semesterId)
                .enqueue(new Callback<GradeResponse>() {
                    @Override
                    public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            updateUI(response.body().getData());
                        } else {
                            Toast.makeText(GradeActivity.this, "Failed to load grades", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GradeResponse> call, Throwable t) {
                        Toast.makeText(GradeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(GradeResponse.GradeData data) {
        // Update summary
        tvGPA.setText(String.format("%.2f", data.getSemester().getSummary().getGpa()));
        tvCredits.setText(String.valueOf(data.getSemester().getSummary().getTotalCredits()));

        // Update grade distribution
        GradeResponse.GradeDistribution distribution = data.getGradeDistribution();
        if (distribution != null) {
            tvGradeA.setText(String.valueOf(distribution.getA()));
            tvGradeB.setText(String.valueOf(distribution.getB()));
            tvGradeC.setText(String.valueOf(distribution.getC()));
            tvGradeD.setText(String.valueOf(distribution.getD()));
        }

        // Update courses list
        courseGrades.clear();
        for (GradeResponse.Course course : data.getCourses()) {
            String teacherName = course.getTeacher() != null ? course.getTeacher().getName() : "N/A";
            courseGrades.add(new CourseGradeModel(
                course.getCourseId(),
                course.getCourseName(),
                course.getCredits(),
                course.getGrades().getAverage(),
                course.getGrades().getLetterGrade(),
                teacherName
            ));
        }
        gradeAdapter.notifyDataSetChanged();
    }

    private void showSemesterDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Semester")
                .setSingleChoiceItems(new String[]{"HK1 2024-2025", "HK2 2023-2024"}, 0, (dialog, which) -> {
                    String semesterId = which == 0 ? "HK1_2024" : "HK2_2023";
                    loadGradeData(studentId, semesterId);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showCourseDetail(CourseGradeModel course) {
        String details = String.format(
            "Course Code: %s\nCourse Name: %s\nCredits: %d\nScore: %.2f\nLetter Grade: %s\nLecturer: %s",
            course.getCourseCode(),
            course.getCourseName(),
            course.getCredits(),
            course.getNumericGrade(),
            course.getLetterGrade(),
            course.getLecturer()
        );

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
                startActivity(new Intent(GradeActivity.this, DeadlineActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                // Already on grade screen
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                startActivity(new Intent(GradeActivity.this, ProfileActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                startActivity(new Intent(GradeActivity.this, HomeCourseActivity.class));
                finish();
            });
        }
    }
}
package com.example.nt118.UI.Attendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118.R;
import com.example.nt118.UI.Login.LoginActivity;
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.Model.Attendance.AttendanceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvTotalSessions, tvAttendanceRate;
    private RecyclerView rvCourses;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Get student ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        studentId = prefs.getString("STUDENT_ID", "");
        if (studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
        loadAttendanceData();
        setupBottomBarNavigation();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        tvTotalSessions = findViewById(R.id.tvTotalSessions);
        tvAttendanceRate = findViewById(R.id.tvAttendanceRate);
        rvCourses = findViewById(R.id.rvCourses);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadAttendanceData() {
        RetrofitClient.getInstance()
                .getAttendanceApi()
                .getStudentAttendance(studentId)
                .enqueue(new Callback<AttendanceResponse>() {
                    @Override
                    public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AttendanceResponse attendanceResponse = response.body();
                            if ("success".equals(attendanceResponse.getStatus())) {
                                AttendanceResponse.AttendanceData data = attendanceResponse.getData();
                                
                                // Update summary
                                tvTotalSessions.setText(String.valueOf(data.getTotalAttendance()));
                                tvAttendanceRate.setText(String.format("%.1f%%", data.getAttendanceRate()));
                                
                                // Update course list
                                CourseAttendanceAdapter adapter = new CourseAttendanceAdapter(
                                    AttendanceActivity.this,
                                    data.getCourses(),
                                    studentId
                                );
                                rvCourses.setAdapter(adapter);
                            } else {
                                Toast.makeText(AttendanceActivity.this, 
                                    "Lỗi: " + attendanceResponse.getStatus(), 
                                    Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AttendanceActivity.this, 
                                "Lỗi kết nối", 
                                Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                        Log.e("AttendanceActivity", "API call failed: " + t.getMessage(), t);
                        Toast.makeText(AttendanceActivity.this, 
                            "Lỗi: " + t.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceActivity.this, DeadlineActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceActivity.this, GradeActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceActivity.this, ProfileActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceActivity.this, HomeCourseActivity.class));
                finish();
            });
        }
    }
}
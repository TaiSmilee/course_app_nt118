package com.example.nt118.UI.Attendance;

import android.content.Intent;
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
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.Model.Attendance.AttendanceDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDetailActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvCourseName;
    private TextView tvTotalSessions;
    private TextView tvAttendedSessions;
    private TextView tvAttendanceRate;
    private RecyclerView rvSessions;
    private String studentId;
    private String courseId;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_detail);

        // Get data from intent
        studentId = getIntent().getStringExtra("studentId");
        courseId = getIntent().getStringExtra("courseId");
        courseName = getIntent().getStringExtra("courseName");

        if (studentId == null || courseId == null || courseName == null) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
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
        tvCourseName = findViewById(R.id.tvCourseName);
        tvTotalSessions = findViewById(R.id.tvTotalSessions);
        tvAttendedSessions = findViewById(R.id.tvAttendedSessions);
        tvAttendanceRate = findViewById(R.id.tvAttendanceRate);
        rvSessions = findViewById(R.id.rvSessions);

        tvCourseName.setText(courseName);
        rvSessions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadAttendanceData() {
        RetrofitClient.getInstance()
                .getAttendanceApi()
                .getCourseAttendance(courseId, studentId)
                .enqueue(new Callback<AttendanceDetailResponse>() {
                    @Override
                    public void onResponse(Call<AttendanceDetailResponse> call, Response<AttendanceDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AttendanceDetailResponse attendanceResponse = response.body();
                            if ("success".equals(attendanceResponse.getStatus())) {
                                AttendanceDetailResponse.AttendanceDetailData data = attendanceResponse.getData();
                                
                                // Update course info
                                tvCourseName.setText(data.getCourseName());
                                
                                // Update summary
                                tvTotalSessions.setText(String.valueOf(data.getTotalSessions()));
                                tvAttendedSessions.setText(String.valueOf(data.getAttendedSessions()));
                                tvAttendanceRate.setText(String.format("%.1f%%", data.getAttendanceRate()));
                                
                                // Update sessions list
                                SessionAdapter adapter = new SessionAdapter(data.getSessions());
                                rvSessions.setAdapter(adapter);
                            } else {
                                Toast.makeText(AttendanceDetailActivity.this, 
                                    "Lỗi: " + attendanceResponse.getMessage(), 
                                    Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AttendanceDetailActivity.this,
                                "Không thể tải dữ liệu điểm danh",
                                Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AttendanceDetailResponse> call, Throwable t) {
                        Toast.makeText(AttendanceDetailActivity.this,
                            "Lỗi kết nối: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceDetailActivity.this, DeadlineActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceDetailActivity.this, GradeActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceDetailActivity.this, ProfileActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                startActivity(new Intent(AttendanceDetailActivity.this, HomeCourseActivity.class));
                finish();
            });
        }
    }
} 
package com.example.nt118.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.Model.StudentProfileResponse;
import com.example.nt118.R;
import com.example.nt118.UI.Tuition.TuitionActivity;
import com.example.nt118.UI.Attendance.AttendanceActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivAvatar;
    private TextView tvName, tvStudentId, tvEmail, tvClass, tvDob;
    private TextView tvTotalCourses, tvTotalAttendance, tvTotalGrades, tvTuitionBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        initializeViews();
        loadStudentProfile();
        BottomBarNavigation();
        StudentComponent();
    }

    private void initializeViews() {
        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        tvStudentId = findViewById(R.id.tvStudentId);
        tvEmail = findViewById(R.id.tvEmail);
        tvClass = findViewById(R.id.tvClass);
        tvDob = findViewById(R.id.tvDob);
        tvTotalCourses = findViewById(R.id.tvTotalCourses);
        tvTotalAttendance = findViewById(R.id.tvTotalAttendance);
        tvTotalGrades = findViewById(R.id.tvTotalGrades);
        tvTuitionBalance = findViewById(R.id.tvTuitionBalance);
    }

    private void loadStudentProfile() {
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");
        String studentId = prefs.getString("STUDENT_ID", "");

        if (token.isEmpty() || studentId.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getInstance()
                .getAuthApi()
                .getStudentProfile("Bearer " + token, studentId)
                .enqueue(new Callback<StudentProfileResponse>() {
                    @Override
                    public void onResponse(Call<StudentProfileResponse> call, Response<StudentProfileResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            StudentProfileResponse profile = response.body();
                            if (profile.isStatus()) {
                                updateUI(profile);
                            } else {
                                Toast.makeText(ProfileActivity.this, 
                                        profile.getMessage(), 
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, 
                                    "Không thể tải thông tin. Vui lòng thử lại!", 
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentProfileResponse> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this,
                                "Lỗi kết nối: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(StudentProfileResponse profile) {
        // Load avatar with better error handling
        if (profile.getAvatarUrl() != null && !profile.getAvatarUrl().isEmpty()) {
            try {
                Glide.with(this)
                    .load(profile.getAvatarUrl())
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .circleCrop() // Make the image circular
                    .into(ivAvatar);
            } catch (Exception e) {
                // If there's any error loading the image, set the default avatar
                ivAvatar.setImageResource(R.drawable.default_avatar);
            }
        } else {
            // If no avatar URL is provided, set the default avatar
            ivAvatar.setImageResource(R.drawable.default_avatar);
        }

        // Update text views with null checks
        tvName.setText(profile.getName() != null ? profile.getName() : "N/A");
        tvStudentId.setText(profile.getStudentId() != null ? profile.getStudentId() : "N/A");
        tvEmail.setText(profile.getEmail() != null ? profile.getEmail() : "N/A");
        tvClass.setText(profile.getClassName() != null ? profile.getClassName() : "N/A");
        tvDob.setText(profile.getDateOfBirth() != null ? profile.getDateOfBirth() : "N/A");

        // Update statistics with null checks
        if (profile.getStatistics() != null) {
            tvTotalCourses.setText(String.valueOf(profile.getStatistics().getTotalCourses()));
            tvTotalAttendance.setText(String.valueOf(profile.getStatistics().getTotalAttendance()));
            tvTotalGrades.setText(String.valueOf(profile.getStatistics().getTotalGrades()));
            tvTuitionBalance.setText(String.format("%,.0f VNĐ", profile.getStatistics().getTuitionBalance()));
        } else {
            // Set default values if statistics is null
            tvTotalCourses.setText("0");
            tvTotalAttendance.setText("0");
            tvTotalGrades.setText("0");
            tvTuitionBalance.setText("0 VNĐ");
        }
    }

    private void StudentComponent() {
        LinearLayout layoutClasses = findViewById(R.id.layoutCourses);
        layoutClasses.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CourseListActivity.class);
            startActivity(intent);
        });

        LinearLayout layoutTuition = findViewById(R.id.layoutTuition);
        layoutTuition.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, TuitionActivity.class);
            startActivity(intent);
        });

        LinearLayout layoutResult = findViewById(R.id.layoutResult);
        layoutResult.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, GradeActivity.class);
            startActivity(intent);
        });

        LinearLayout layoutAttendance = findViewById(R.id.layoutAttendance);
        layoutAttendance.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AttendanceActivity.class);
            startActivity(intent);
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
                // Already on profile screen
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}

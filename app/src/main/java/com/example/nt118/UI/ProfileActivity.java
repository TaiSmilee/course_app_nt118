package com.example.nt118.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.Model.StudentProfileResponse;
import com.example.nt118.Model.StudentStatistics;
import com.example.nt118.R;
import com.example.nt118.UI.Tuition.TuitionActivity;
import com.example.nt118.UI.Attendance.AttendanceActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivAvatar;
    private TextView tvName, tvStudentId, tvEmail, tvClass, tvDob;
    private TextView tvTotalCourses, tvTotalAttendance, tvTotalGrades, tvTuitionBalance;
    private LinearLayout contentStatistics;
    private ImageView ivExpandStatistics;
    private boolean isStatisticsExpanded = false;

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

        // Statistics
        contentStatistics = findViewById(R.id.contentStatistics);
        ivExpandStatistics = findViewById(R.id.ivExpandStatistics);

        // Set up expand/collapse animation for statistics
        View headerStatistics = findViewById(R.id.headerStatistics);
        headerStatistics.setOnClickListener(v -> toggleStatisticsExpansion());
    }

    private void toggleStatisticsExpansion() {
        isStatisticsExpanded = !isStatisticsExpanded;
        
        // Rotate arrow icon
        float startRotation = isStatisticsExpanded ? 0 : 180;
        float endRotation = isStatisticsExpanded ? 180 : 0;
        RotateAnimation rotateAnimation = new RotateAnimation(
            startRotation, endRotation,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        ivExpandStatistics.startAnimation(rotateAnimation);

        // Show/hide content with animation
        contentStatistics.setVisibility(isStatisticsExpanded ? View.VISIBLE : View.GONE);
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
                            if ("success".equals(profile.getStatus())) {
                                updateUI(profile.getData());
                            } else {
                                Toast.makeText(ProfileActivity.this, 
                                        "Không thể tải thông tin", 
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

    private String formatTuitionBalance(double amount) {
        if (amount >= 1000000000) { // Tỷ
            return String.format("%.1f tỷ VNĐ", amount / 1000000000);
        } else if (amount >= 1000000) { // Triệu
            return String.format("%.1f triệu VNĐ", amount / 1000000);
        } else if (amount >= 1000) { // Nghìn
            return String.format("%.1f nghìn VNĐ", amount / 1000);
        } else {
            return String.format("%.0f VNĐ", amount);
        }
    }

    private void updateUI(StudentProfileResponse.StudentData data) {
        // Load avatar with better error handling
        if (data.getAvatarUrl() != null && !data.getAvatarUrl().isEmpty()) {
            try {
                Glide.with(this)
                    .load(data.getAvatarUrl())
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
        tvName.setText(data.getName() != null ? data.getName() : "N/A");
        tvStudentId.setText(data.getStudentId() != null ? data.getStudentId() : "N/A");
        tvEmail.setText(data.getEmail() != null ? data.getEmail() : "N/A");
        tvClass.setText(data.getClassName() != null ? data.getClassName() : "N/A");
        tvDob.setText(data.getDateOfBirth() != null ? data.getDateOfBirth() : "N/A");

        // Update statistics with null checks
        if (data.getStatistics() != null) {
            StudentStatistics stats = data.getStatistics();
            tvTotalCourses.setText(String.valueOf(stats.getTotalCourses()));
            tvTotalAttendance.setText(String.valueOf(stats.getTotalAttendance()));
            tvTotalGrades.setText(String.valueOf(stats.getTotalGrades()));
            tvTuitionBalance.setText(formatTuitionBalance(stats.getTuitionBalance()));
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

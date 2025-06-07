package com.example.nt118.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.Model.Deadline.DeadlineItem;
import com.example.nt118.Model.Deadline.DeadlineResponse;
import com.example.nt118.UI.CourseDetail.CourseDeadlineAdapter;
import com.example.nt118.UI.CourseDetail.NotificationAdapter;
import com.example.nt118.UI.Login.LoginActivity;
import com.example.nt118.Model.CourseDetail.Notification;
import com.example.nt118.api.models.notification.NotificationResponse;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    private static final String TAG = "CourseDetailActivity";
    private ImageView btnBack;
    private ImageView btnStudentList;
    private TabLayout tabLayout;
    private ListView notificationList;
    private ListView deadlineList;
    private String studentId;
    private String courseId;
    private CourseDeadlineAdapter deadlineAdapter;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;
    private TextView tvNoNotifications;
    
    // Flags to track API completion
    private boolean notificationLoaded = false;
    private boolean deadlineLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
        setupTabs();

        // Get courseId from intent
        courseId = getIntent().getStringExtra("courseId");
        if (courseId != null) {
            loadAllData();
        }

        setupBottomBarNavigation();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnStudentList = findViewById(R.id.btnStudentList);
        tabLayout = findViewById(R.id.tabLayout);
        notificationList = findViewById(R.id.notificationList);
        deadlineList = findViewById(R.id.deadlineList);
        tvNoNotifications = findViewById(R.id.tvNoNotifications);
        
        // Initialize notifications
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notifications);
        notificationList.setAdapter(notificationAdapter);

        // Initialize deadlines
        deadlineAdapter = new CourseDeadlineAdapter(this, new ArrayList<>());
        deadlineList.setAdapter(deadlineAdapter);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnStudentList.setOnClickListener(v -> {
            Intent intent = new Intent(CourseDetailActivity.this, StudentListActivity.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        });
    }

    private void setupTabs() {
        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Thông báo"));
        tabLayout.addTab(tabLayout.newTab().setText("Deadline"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    // Show notifications
                    notificationList.setVisibility(View.VISIBLE);
                    deadlineList.setVisibility(View.GONE);
                    if (notifications.isEmpty()) {
                        tvNoNotifications.setVisibility(View.VISIBLE);
                    } else {
                        tvNoNotifications.setVisibility(View.GONE);
                    }
                } else {
                    // Show deadlines
                    notificationList.setVisibility(View.GONE);
                    deadlineList.setVisibility(View.VISIBLE);
                    tvNoNotifications.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Show notifications by default
        notificationList.setVisibility(View.VISIBLE);
        deadlineList.setVisibility(View.GONE);
    }

    private void loadAllData() {
        // Show loading dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get token and validate
        SharedPreferences prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE);
        String token = prefs.getString("TOKEN", "");
        if (token.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reset completion flags
        notificationLoaded = false;
        deadlineLoaded = false;

        // Load notifications
        RetrofitClient.getInstance()
            .getNotificationApi()
            .getCourseNotifications("Bearer " + token, courseId)
            .enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        NotificationResponse data = response.body();
                        if (data.isSuccess()) {
                            notifications.clear();
                            notifications.addAll(data.getNotifications());
                            notificationAdapter.notifyDataSetChanged();
                            
                            // Log để debug
                            Log.d(TAG, "Loaded " + notifications.size() + " notifications");
                            for (Notification notification : notifications) {
                                Log.d(TAG, "Notification: " + notification.getTitle());
                            }
                            
                            if (notifications.isEmpty()) {
                                tvNoNotifications.setVisibility(View.VISIBLE);
                                notificationList.setVisibility(View.GONE);
                            } else {
                                tvNoNotifications.setVisibility(View.GONE);
                                notificationList.setVisibility(View.VISIBLE);
                            }
                        } else {
                            handleError(data.getMessage());
                        }
                    } else {
                        handleError("Không thể tải thông báo");
                    }
                    notificationLoaded = true;
                    checkAllDataLoaded(progressDialog);
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    Log.e(TAG, "Failed to load notifications", t);
                    handleError("Lỗi kết nối: " + t.getMessage());
                    notificationLoaded = true;
                    checkAllDataLoaded(progressDialog);
                }
            });

        // Load deadlines
        RetrofitClient.getInstance()
            .getDeadlineApi()
            .getDeadlines(studentId, courseId)
            .enqueue(new Callback<DeadlineResponse>() {
                @Override
                public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DeadlineResponse data = response.body();
                        if ("success".equals(data.getStatus())) {
                            deadlineAdapter.clear();
                            deadlineAdapter.addAll(data.getData());
                            deadlineAdapter.notifyDataSetChanged();
                        } else {
                            handleError("Không thể tải deadline: " + data.getStatus());
                        }
                    } else {
                        handleError("Không thể tải deadline");
                    }
                    deadlineLoaded = true;
                    checkAllDataLoaded(progressDialog);
                }

                @Override
                public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                    handleError("Lỗi kết nối: " + t.getMessage());
                    deadlineLoaded = true;
                    checkAllDataLoaded(progressDialog);
                }
            });
    }

    private void checkAllDataLoaded(ProgressDialog progressDialog) {
        if (notificationLoaded && deadlineLoaded) {
            progressDialog.dismiss();
            updateUI();
        }
    }

    private void updateUI() {
        if (tabLayout.getSelectedTabPosition() == 0) {
            // Show notifications
            notificationList.setVisibility(View.VISIBLE);
            deadlineList.setVisibility(View.GONE);
            if (notifications.isEmpty()) {
                tvNoNotifications.setVisibility(View.VISIBLE);
            } else {
                tvNoNotifications.setVisibility(View.GONE);
            }
        } else {
            // Show deadlines
            notificationList.setVisibility(View.GONE);
            deadlineList.setVisibility(View.VISIBLE);
            tvNoNotifications.setVisibility(View.GONE);
        }
    }

    private void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                startActivity(new Intent(CourseDetailActivity.this, DeadlineActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                startActivity(new Intent(CourseDetailActivity.this, GradeActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                startActivity(new Intent(CourseDetailActivity.this, ProfileActivity.class));
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                startActivity(new Intent(CourseDetailActivity.this, HomeCourseActivity.class));
                finish();
            });
        }
    }
}
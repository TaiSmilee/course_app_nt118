package com.example.nt118.UI;

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
import android.widget.Toast;

import com.example.nt118.UI.Deadline.DeadlineActivity;
import com.example.nt118.R;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.Model.Deadline.DeadlineItem;
import com.example.nt118.Model.Deadline.DeadlineResponse;
import com.example.nt118.UI.CourseDetail.CourseDeadlineAdapter;
import com.example.nt118.UI.Login.LoginActivity;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    private ImageView btnBack;
    private ImageView btnStudentList;
    private TabLayout tabLayout;
    private ListView notificationList;
    private ListView deadlineList;
    private String studentId;
    private String courseId;

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
            loadDeadlines();
        }

        setupBottomBarNavigation();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        btnStudentList = findViewById(R.id.btnStudentList);
        tabLayout = findViewById(R.id.tabLayout);
        notificationList = findViewById(R.id.notificationList);
        deadlineList = findViewById(R.id.deadlineList);
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
                    notificationList.setVisibility(View.VISIBLE);
                    deadlineList.setVisibility(View.GONE);
                } else {
                    notificationList.setVisibility(View.GONE);
                    deadlineList.setVisibility(View.VISIBLE);
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

    private void loadDeadlines() {
        Log.d("CourseDetailActivity", "Loading deadlines for studentId: " + studentId + ", courseId: " + courseId);
        
        RetrofitClient.getInstance()
                .getDeadlineApi()
                .getDeadlines(studentId, courseId)
                .enqueue(new Callback<DeadlineResponse>() {
                    @Override
                    public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
                        Log.d("CourseDetailActivity", "Response code: " + response.code());
                        
                        if (response.isSuccessful() && response.body() != null) {
                            DeadlineResponse deadlineResponse = response.body();
                            Log.d("CourseDetailActivity", "Response status: " + deadlineResponse.getStatus());
                            
                            if ("success".equals(deadlineResponse.getStatus())) {
                                List<DeadlineItem> deadlines = deadlineResponse.getData();
                                Log.d("CourseDetailActivity", "Number of deadlines received: " + (deadlines != null ? deadlines.size() : 0));
                                
                                if (deadlines != null && !deadlines.isEmpty()) {
                                    for (DeadlineItem deadline : deadlines) {
                                        Log.d("CourseDetailActivity", "Deadline: " +
                                            "ID=" + deadline.getId() + 
                                            ", Title=" + deadline.getTitle() + 
                                            ", Status=" + deadline.getStatus() +
                                            ", DueDate=" + deadline.getDueDate());
                                    }
                                    
                                    CourseDeadlineAdapter adapter = new CourseDeadlineAdapter(CourseDetailActivity.this, deadlines);
                                    deadlineList.setAdapter(adapter);
                                    Log.d("CourseDetailActivity", "Adapter set with " + deadlines.size() + " items");
                                } else {
                                    Log.d("CourseDetailActivity", "No deadlines found in response");
                                    Toast.makeText(CourseDetailActivity.this, "Không có deadline nào", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("CourseDetailActivity", "API returned error status: " + deadlineResponse.getStatus());
                                Toast.makeText(CourseDetailActivity.this, "Lỗi: " + deadlineResponse.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String errorBody = "";
                            try {
                                if (response.errorBody() != null) {
                                    errorBody = response.errorBody().string();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e("CourseDetailActivity", "Error response: " + errorBody);
                            Toast.makeText(CourseDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                        Log.e("CourseDetailActivity", "API call failed: " + t.getMessage(), t);
                        Toast.makeText(CourseDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
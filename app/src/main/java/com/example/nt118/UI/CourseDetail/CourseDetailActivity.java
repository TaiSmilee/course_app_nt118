package com.example.nt118.UI.CourseDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nt118.R;
import com.example.nt118.Model.Deadline.DeadlineItem;
import com.example.nt118.Model.Deadline.DeadlineResponse;
import com.example.nt118.UI.submission.SubmissionActivity;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.course.CourseDetailResponse;
import com.example.nt118.api.models.notification.NotificationResponse;
import com.google.android.material.tabs.TabLayout;
import com.example.nt118.Model.CourseDetail.Notification;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {
    private static final String TAG = "CourseDetailActivity";
    private CourseDeadlineAdapter deadlineAdapter;
    private String courseId;
    private String semesterId;
    private ListView notificationList;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notifications;
    private TabLayout tabLayout;
    private TextView tvNoNotifications;
    private ListView deadlineList;
    
    // Flags to track API completion
    private boolean courseLoaded = false;
    private boolean notificationLoaded = false;
    private boolean deadlineLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Log.d(TAG, "onCreate: Activity created");
        
        // Validate input and initialize
        if (!validateInput()) {
            return;
        }

        initializeViews();
        setupTabLayout();
        loadAllData();
    }

    private boolean validateInput() {
        courseId = getIntent().getStringExtra("courseId");
        semesterId = getIntent().getStringExtra("semesterId");

        if (courseId == null || semesterId == null) {
            Toast.makeText(this, "Không tìm thấy thông tin khóa học", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        return true;
    }

    private void initializeViews() {
        notificationList = findViewById(R.id.notificationList);
        deadlineList = findViewById(R.id.deadlineList);
        tabLayout = findViewById(R.id.tabLayout);
        tvNoNotifications = findViewById(R.id.tvNoNotifications);
        
        // Initialize notifications
        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notifications);
        notificationList.setAdapter(notificationAdapter);

        // Initialize deadlines
        deadlineAdapter = new CourseDeadlineAdapter(this, new ArrayList<>());
        deadlineList.setAdapter(deadlineAdapter);
        
        // Set click listener for deadline items
        deadlineList.setOnItemClickListener((parent, view, position, id) -> {
            DeadlineItem deadline = deadlineAdapter.getItem(position);
            if (deadline != null && "NOT_SUBMITTED".equals(deadline.getStatus())) {
                Log.d(TAG, "setupDeadlineList: Opening SubmissionActivity for deadline: " + deadline.getId());
                Intent intent = new Intent(this, SubmissionActivity.class);
                intent.putExtra("courseId", deadline.getCourseId());
                intent.putExtra("notificationId", deadline.getId());
                intent.putExtra("deadline", deadline.getDueDate().getTime());
                startActivity(intent);
            }
        });

        // Initially show notifications tab
        notificationList.setVisibility(View.VISIBLE);
        deadlineList.setVisibility(View.GONE);
    }

    private void setupTabLayout() {
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
        courseLoaded = false;
        notificationLoaded = false;
        deadlineLoaded = false;

        Log.d(TAG, "loadAllData: Starting to load all data");
        
        // Create API calls
        Call<CourseDetailResponse> courseCall = RetrofitClient.getApiService()
            .getCourseDetail("Bearer " + token, semesterId, courseId);
            
        Call<NotificationResponse> notificationCall = RetrofitClient.getInstance()
            .getNotificationApi()
            .getCourseNotifications("Bearer " + token, courseId);
            
        // Comment out deadline API call temporarily
        /*
        Call<DeadlineResponse> deadlineCall = RetrofitClient.getInstance()
            .getDeadlineApi()
            .getDeadlines(prefs.getString("STUDENT_ID", ""), courseId);
        */

        // Execute API calls in parallel
        courseCall.enqueue(new Callback<CourseDetailResponse>() {
            @Override
            public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {
                Log.d(TAG, "loadCourseDetail: Response code=" + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "loadCourseDetail: Successfully loaded course detail");
                    updateCourseInfo(response.body());
                } else {
                    Log.e(TAG, "loadCourseDetail: Failed to load course detail - " + response.message());
                    handleError("Không thể tải thông tin khóa học");
                }
                courseLoaded = true;
                checkAllDataLoaded(progressDialog);
            }

            @Override
            public void onFailure(Call<CourseDetailResponse> call, Throwable t) {
                Log.e(TAG, "loadCourseDetail: Error loading course detail", t);
                handleError("Lỗi kết nối: " + t.getMessage());
                courseLoaded = true;
                checkAllDataLoaded(progressDialog);
            }
        });

        notificationCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                Log.d(TAG, "loadNotifications: Response code=" + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    NotificationResponse data = response.body();
                    if (data.isSuccess()) {
                        Log.d(TAG, "loadNotifications: Successfully loaded " + data.getNotifications().size() + " notifications");
                        notifications.clear();
                        notifications.addAll(data.getNotifications());
                        notificationAdapter.notifyDataSetChanged();
                        
                        if (notifications.isEmpty()) {
                            tvNoNotifications.setVisibility(View.VISIBLE);
                            notificationList.setVisibility(View.GONE);
                        } else {
                            tvNoNotifications.setVisibility(View.GONE);
                            notificationList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.e(TAG, "loadNotifications: Failed to load notifications - " + data.getMessage());
                        handleError(data.getMessage());
                    }
                } else {
                    Log.e(TAG, "loadNotifications: Failed to load notifications - " + response.message());
                    handleError("Không thể tải thông báo");
                }
                notificationLoaded = true;
                checkAllDataLoaded(progressDialog);
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e(TAG, "loadNotifications: Error loading notifications", t);
                handleError("Lỗi kết nối: " + t.getMessage());
                notificationLoaded = true;
                checkAllDataLoaded(progressDialog);
            }
        });

        // Comment out deadline API call temporarily
        /*
        deadlineCall.enqueue(new Callback<DeadlineResponse>() {
            @Override
            public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
                Log.d(TAG, "loadDeadlines: Response code=" + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<DeadlineItem> deadlines = response.body().getData();
                    Log.d(TAG, "loadDeadlines: Successfully loaded " + deadlines.size() + " deadlines");
                    deadlineAdapter.clear();
                    deadlineAdapter.addAll(deadlines);
                    deadlineAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "loadDeadlines: Failed to load deadlines - " + response.message());
                    handleError("Không thể tải danh sách deadline");
                }
                deadlineLoaded = true;
                checkAllDataLoaded(progressDialog);
            }

            @Override
            public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                Log.e(TAG, "loadDeadlines: Error loading deadlines", t);
                handleError("Lỗi kết nối: " + t.getMessage());
                deadlineLoaded = true;
                checkAllDataLoaded(progressDialog);
            }
        });
        */
    }

    private void checkAllDataLoaded(ProgressDialog progressDialog) {
        Log.d(TAG, "checkAllDataLoaded: courseLoaded=" + courseLoaded + 
            ", notificationLoaded=" + notificationLoaded + 
            ", deadlineLoaded=" + deadlineLoaded);
            
        if (courseLoaded && notificationLoaded && deadlineLoaded) {
            Log.d(TAG, "checkAllDataLoaded: All data loaded, updating UI");
            progressDialog.dismiss();
            updateUI();
        }
    }

    private void updateUI() {
        // Update any UI elements that depend on all data being loaded
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

    private void updateCourseInfo(CourseDetailResponse data) {
        TextView tvCourseName = findViewById(R.id.tvCourseName);
        TextView tvCourseId = findViewById(R.id.tvCourseId);
        TextView tvTeacher = findViewById(R.id.tvTeacher);
        TextView tvCredits = findViewById(R.id.tvCredits);

        CourseDetailResponse.CourseInfo courseInfo = data.getData().getCourseInfo();
        tvCourseName.setText(courseInfo.getCourseName());
        tvCourseId.setText(courseInfo.getCourseCode());
        tvTeacher.setText(courseInfo.getLecturer());
        tvCredits.setText(String.valueOf(courseInfo.getCredits()));
    }
} 
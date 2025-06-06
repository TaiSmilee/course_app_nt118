package com.example.nt118.UI.Deadline;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.nt118.R;
import com.example.nt118.UI.MainActivity;
import com.example.nt118.UI.ProfileActivity;
import com.example.nt118.UI.grades.GradeActivity;
import com.example.nt118.UI.homecourse.HomeCourseActivity;
import com.example.nt118.api.ApiService;
import com.example.nt118.api.RetrofitClient;
import com.example.nt118.api.models.deadline.DeadlineItem;
import com.example.nt118.api.models.deadline.DeadlineResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeadlineActivity extends AppCompatActivity {
    private static final String TAG = "DeadlineActivity";

    private RecyclerView recyclerView;
    private DeadlineAdapter adapter;
    private List<DeadlineItem> deadlineList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline);

        // Initialize API service
        apiService = RetrofitClient.getInstance().createService(ApiService.class);
        sharedPreferences = getSharedPreferences("APP_PREF", MODE_PRIVATE);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_deadlines);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        tvSummary = findViewById(R.id.tv_deadline_summary);

        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView not found!");
            return;
        }
        
        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        deadlineList = new ArrayList<>();
        adapter = new DeadlineAdapter(this, deadlineList);
        recyclerView.setAdapter(adapter);

        // Setup refresh listener
        swipeRefreshLayout.setOnRefreshListener(this::fetchDeadlines);

        // Initial data load
        fetchDeadlines();

        setupBottomBarNavigation();
    }

    private void fetchDeadlines() {
        String token = sharedPreferences.getString("TOKEN", "");
        String studentId = sharedPreferences.getString("STUDENT_ID", "");
        
        Log.d(TAG, "Fetching deadlines for student: " + studentId);
        
        if (studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found. Please login again.", Toast.LENGTH_LONG).show();
            return;
        }
        
        apiService.getDeadlines("Bearer " + token, studentId).enqueue(new Callback<DeadlineResponse>() {
            @Override
            public void onResponse(Call<DeadlineResponse> call, Response<DeadlineResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    DeadlineResponse deadlineResponse = response.body();
                    Log.d(TAG, "Response successful. Total deadlines: " + deadlineResponse.getTotalDeadlines());
                    
                    if (deadlineResponse.isSuccess()) {
                        List<DeadlineItem> deadlines = deadlineResponse.getDeadlines();
                        Log.d(TAG, "Updating adapter with " + deadlines.size() + " deadlines");
                        
                        // Update the adapter with new data
                        adapter.updateDeadlines(deadlines);
                        
                        // Check for upcoming deadlines and show notifications
                        checkAndNotifyUpcomingDeadlines(deadlines);
                        
                        // Update summary
                        updateSummary(deadlineResponse);
                    } else {
                        Log.e(TAG, "API returned error: " + deadlineResponse.getMessage());
                        Toast.makeText(DeadlineActivity.this, 
                            deadlineResponse.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Response not successful. Code: " + response.code());
                    Toast.makeText(DeadlineActivity.this, 
                        "Failed to load deadlines", 
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeadlineResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "API call failed", t);
                Toast.makeText(DeadlineActivity.this, 
                    "Error: " + t.getMessage(), 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndNotifyUpcomingDeadlines(List<DeadlineItem> deadlines) {
        for (DeadlineItem deadline : deadlines) {
            long daysRemaining = getDaysRemaining(deadline.getDeadlineDate());
            if (daysRemaining <= 2 && daysRemaining >= 0 && 
                "PENDING".equalsIgnoreCase(deadline.getStatus())) {
                showDeadlineNotification(deadline);
            }
        }
    }

    private long getDaysRemaining(Date deadline) {
        long diff = deadline.getTime() - System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    private void showDeadlineNotification(DeadlineItem deadline) {
        createNotificationChannel();

        Intent intent = new Intent(this, DeadlineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "deadline_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Deadline Approaching!")
                .setContentText(String.format("%s - %s", 
                    deadline.getCourse().getCourseId(), 
                    deadline.getTitle()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        try {
            notificationManager.notify(deadline.getId(), builder.build());
        } catch (SecurityException e) {
            Log.e(TAG, "Notification permission not granted", e);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Deadline Notifications";
            String description = "Notifications for upcoming deadlines";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("deadline_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateSummary(DeadlineResponse response) {
        String summaryText = String.format("Total: %d | Pending: %d | Completed: %d | Overdue: %d",
            response.getTotalDeadlines(),
            response.getPendingDeadlines(),
            response.getCompletedDeadlines(),
            response.getOverdueDeadlines());
        Log.d(TAG, "Updating summary: " + summaryText);
        tvSummary.setText(summaryText);
    }

    private void setupBottomBarNavigation() {
        View bottomBar = findViewById(R.id.bottom_bar);
        if (bottomBar != null) {
            bottomBar.findViewById(R.id.btnDeadline).setOnClickListener(v -> {
                // Already on Deadline screen
            });

            bottomBar.findViewById(R.id.btnGrade).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            });

            bottomBar.findViewById(R.id.btnHome).setOnClickListener(v -> {
                Intent intent = new Intent(DeadlineActivity.this, HomeCourseActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
}
